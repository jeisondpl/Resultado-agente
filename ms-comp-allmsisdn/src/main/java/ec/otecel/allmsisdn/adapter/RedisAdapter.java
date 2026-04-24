package ec.otecel.allmsisdn.adapter;

import ec.otecel.allmsisdn.constants.MsConstants;
import ec.otecel.allmsisdn.dto.integration.RecoverRedisRequestDTO;
import ec.otecel.allmsisdn.dto.integration.RecoverRedisResponseDTO;
import ec.otecel.allmsisdn.dto.integration.SaveRedisRequestDTO;
import ec.otecel.allmsisdn.dto.integration.SaveRedisResponseDTO;
import ec.otecel.allmsisdn.util.BasicOperationAdapter;
import ec.otecel.common.model.commontypes.ErrorCodeType;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.logs.config.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RedisAdapter {

    @Autowired
    private LoggerService loggerService;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${telefonica.api.redis.host:backendpre.movistar.com.ec}")
    private String redisHost;

    @Value("${telefonica.api.redis.port:32101}")
    private int redisPort;

    @Value("${telefonica.api.redis.path.save:/comp/redis/v2/saveRedisTimeToLive}")
    private String savePath;

    @Value("${telefonica.api.redis.path.recover:/comp/redis/v2/getDataRedis}")
    private String recoverPath;

    @Value("${telefonica.api.redis.https:true}")
    private boolean useHttps;

    @Value("${otecel.api.token:Basic YWRtaW46c1kzNU1Yei56dVg=}")
    private String authToken;

    public SaveRedisResponseDTO saveRedis(HeaderInType headerIn, SaveRedisRequestDTO request)
            throws ComponentException {
        return new BasicOperationAdapter<SaveRedisResponseDTO, SaveRedisRequestDTO>(headerIn,
                new SaveRedisResponseDTO(), request) {
            @Override
            public SaveRedisResponseDTO process(HeaderInType h, SaveRedisRequestDTO r)
                    throws ComponentException {
                try {
                    String url = (useHttps ? "https" : "http") + "://" + redisHost + ":" + redisPort + savePath;
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.set("Authorization", authToken);
                    HttpEntity<SaveRedisRequestDTO> entity = new HttpEntity<>(r, headers);
                    ResponseEntity<SaveRedisResponseDTO> resp = restTemplate.exchange(
                            url, HttpMethod.POST, entity, SaveRedisResponseDTO.class);
                    if (resp.getBody() == null) {
                        throw new ComponentException(ErrorCodeType.ERROR_INESPERADO,
                                "Empty response from Redis save", true,
                                new String[]{}, getClass().getSimpleName());
                    }
                    return resp.getBody();
                } catch (ComponentException ce) {
                    throw ce;
                } catch (Exception e) {
                    throw new ComponentException(e.getMessage(), e,
                            ErrorCodeType.ERROR_INESPERADO, true,
                            new String[]{}, getClass().getSimpleName());
                }
            }
        }.initializer(loggerService)
         .setCommonInfo(MsConstants.SERVICE, MsConstants.METHOD1, getClass().getSimpleName())
         .run();
    }

    public RecoverRedisResponseDTO getDataRedis(HeaderInType headerIn, RecoverRedisRequestDTO request)
            throws ComponentException {
        return new BasicOperationAdapter<RecoverRedisResponseDTO, RecoverRedisRequestDTO>(headerIn,
                new RecoverRedisResponseDTO(), request) {
            @Override
            public RecoverRedisResponseDTO process(HeaderInType h, RecoverRedisRequestDTO r)
                    throws ComponentException {
                try {
                    String url = (useHttps ? "https" : "http") + "://" + redisHost + ":" + redisPort + recoverPath;
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.set("Authorization", authToken);
                    HttpEntity<RecoverRedisRequestDTO> entity = new HttpEntity<>(r, headers);
                    ResponseEntity<RecoverRedisResponseDTO> resp = restTemplate.exchange(
                            url, HttpMethod.POST, entity, RecoverRedisResponseDTO.class);
                    return resp.getBody() != null ? resp.getBody() : new RecoverRedisResponseDTO();
                } catch (Exception e) {
                    // No romper el flujo si el cache no responde — devolver vacío para que
                    // el service haga fallback a NetCracker.
                    return new RecoverRedisResponseDTO();
                }
            }
        }.initializer(loggerService)
         .setCommonInfo(MsConstants.SERVICE, MsConstants.METHOD1, getClass().getSimpleName())
         .run();
    }
}
