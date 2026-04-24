package ec.otecel.allmsisdn.service;

import ec.otecel.allmsisdn.adapter.NetCrackerRDBAdapter;
import ec.otecel.allmsisdn.adapter.RedisAdapter;
import ec.otecel.allmsisdn.constants.MsConstants;
import ec.otecel.allmsisdn.dto.exposition.AllMsisdnRequestDTO;
import ec.otecel.allmsisdn.dto.exposition.AllMsisdnResponseDTO;
import ec.otecel.allmsisdn.util.BasicOperationService;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.logs.config.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.otecel.allmsisdn.dto.integration.RecoverRedisRequestDTO;
import ec.otecel.allmsisdn.dto.integration.RecoverRedisResponseDTO;
import ec.otecel.allmsisdn.dto.integration.SaveRedisRequestDTO;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import ec.otecel.common.model.commontypes.ErrorCodeType;
import ec.otecel.common.model.commontypes.LoggerAppType;

@Service
public class AllMsisdnService implements IAllMsisdnService {

    @Autowired
    private NetCrackerRDBAdapter netCrackerRDBAdapter;

    @Autowired
    private RedisAdapter redisAdapter;

    @Autowired
    private LoggerService loggerService;

    @Override
    public AllMsisdnResponseDTO allMsisdn(HeaderInType h, AllMsisdnRequestDTO req)
            throws ComponentException {
        return new BasicOperationService<AllMsisdnResponseDTO, AllMsisdnRequestDTO>(
                h, new AllMsisdnResponseDTO(), req) {
            @Override
            public AllMsisdnResponseDTO process(HeaderInType headerIn, AllMsisdnRequestDTO request)
                    throws ComponentException {
                // 1. Construir key con originator + atributos no nulos
                String key = createKey(headerIn.getOriginator(), request);

                // 2. Intentar recuperar de Redis
                RecoverRedisResponseDTO cached = redisAdapter.getDataRedis(
                        headerIn, new RecoverRedisRequestDTO(key));
                if (cached != null && cached.getValue() != null && !cached.getValue().isEmpty()) {
                    return jsonToAllMsisdnResponse(cached.getValue());
                }

                // 3. Fallback a NetCrackerRDB
                AllMsisdnResponseDTO response = netCrackerRDBAdapter.allMsisdn(headerIn, request);
                if (response == null) {
                    throw new ComponentException(ErrorCodeType.ERROR_INESPERADO,
                            "No response from NetCracker", true,
                            new String[]{}, getClass().getSimpleName());
                }

                // 4. Guardar en cache (no romper si falla)
                try {
                    redisAdapter.saveRedis(headerIn, new SaveRedisRequestDTO(
                            key, allMsisdnResponseToJson(response), request.getTimeToLive()));
                } catch (Exception e) {
                    loggerService.logApp(
                            UUID.randomUUID().toString(), Strings.EMPTY,
                            UUID.randomUUID().toString(),
                            LoggerAppType.DSI_AUDIT_BODY_ERROR_HANDLER.toString(),
                            "Cache save failed: " + e.getMessage(),
                            getClass().getSimpleName());
                }
                return response;
            }
        }.initializer(loggerService)
         .setCommonInfo(MsConstants.SERVICE, MsConstants.METHOD1, getClass().getSimpleName())
         .run();
    }

    private String createKey(String originator, AllMsisdnRequestDTO req) {
        StringBuilder sb = new StringBuilder();
        if (originator != null) sb.append(originator);
        if (req.getIdentifierType() != null) sb.append('_').append(req.getIdentifierType());
        if (req.getIdentifierNumber() != null) sb.append('_').append(req.getIdentifierNumber());
        if (req.getLevel() != null) sb.append('_').append(req.getLevel());
        if (req.getOperatorId() != null) sb.append('_').append(req.getOperatorId());
        return sb.toString();
    }

    private AllMsisdnResponseDTO jsonToAllMsisdnResponse(String json) {
        try { return new ObjectMapper().readValue(json, AllMsisdnResponseDTO.class); }
        catch (Exception e) { return null; }
    }

    private String allMsisdnResponseToJson(AllMsisdnResponseDTO dto) {
        try { return new ObjectMapper().writeValueAsString(dto); }
        catch (Exception e) { return ""; }
    }
}