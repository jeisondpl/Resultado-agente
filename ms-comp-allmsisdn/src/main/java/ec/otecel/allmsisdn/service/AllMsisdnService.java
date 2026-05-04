package ec.otecel.allmsisdn.service;

import ec.otecel.allmsisdn.adapter.NetCrackerRDBAdapter;
import ec.otecel.allmsisdn.adapter.RedisAdapter;
import ec.otecel.allmsisdn.constants.MsConstants;
import ec.otecel.allmsisdn.dto.exposition.AllMsisdnRequestDTO;
import ec.otecel.allmsisdn.dto.exposition.AllMsisdnResponseDTO;
import ec.otecel.allmsisdn.dto.integration.RecoverRedisRequestDTO;
import ec.otecel.allmsisdn.dto.integration.RecoverRedisResponseDTO;
import ec.otecel.allmsisdn.dto.integration.SaveRedisRequestDTO;
import ec.otecel.allmsisdn.dto.integration.SaveRedisResponseDTO;
import ec.otecel.allmsisdn.util.BasicOperationService;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.logs.config.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import ec.otecel.common.model.commontypes.ErrorCodeType;
import ec.otecel.common.model.commontypes.LoggerAppType;

@Service
public class AllMsisdnService implements IAllMsisdnService {

    @Autowired
    private RedisAdapter redisAdapter;

    @Autowired
    private NetCrackerRDBAdapter netCrackerRDBAdapter;

    @Autowired
    private LoggerService loggerService;

    @Override
    public AllMsisdnResponseDTO allMsisdn(HeaderInType h, AllMsisdnRequestDTO req) throws ComponentException {
        return new BasicOperationService<AllMsisdnResponseDTO, AllMsisdnRequestDTO>(h, new AllMsisdnResponseDTO(), req) {
            @Override
            public AllMsisdnResponseDTO process(HeaderInType headerIn, AllMsisdnRequestDTO request) throws ComponentException {
                final String finalStep = "3";
                String key = createKey(headerIn.getOriginator(), request);
                RecoverRedisResponseDTO cached = redisAdapter.getDataRedis(headerIn, new RecoverRedisRequestDTO(key), "1", finalStep);
                if (cached != null && cached.getJson() != null && !cached.getJson().isEmpty()) {
                    return jsonToAllMsisdnResponse(cached.getJson());
                }
                AllMsisdnResponseDTO response = netCrackerRDBAdapter.allMsisdn(headerIn, request, "2", finalStep);
                if (response == null) {
                    throw new ComponentException(ErrorCodeType.ERROR_INESPERADO, "No response from NetCracker", true, new String[]{}, getClass().getSimpleName());
                }
                try {
                    redisAdapter.saveRedis(headerIn, new SaveRedisRequestDTO(key, allMsisdnResponseToJson(response), Integer.valueOf(request.getTimeToLive())), "3", finalStep);
                } catch (Exception e) {
                    loggerService.logApp(UUID.randomUUID().toString(), Strings.EMPTY, UUID.randomUUID().toString(), LoggerAppType.DSI_AUDIT_BODY_ERROR_HANDLER.toString(), "Cache save failed: " + e.getMessage(), getClass().getSimpleName());
                }
                return response;
            }
        }.initializer(loggerService)
         .setCommonInfo(MsConstants.SERVICE, MsConstants.METHOD_NAME_ALL_MSISDN, getClass().getSimpleName())
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