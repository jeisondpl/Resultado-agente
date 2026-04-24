package ec.otecel.allmsisdn.adapter;

import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import ec.otecel.allmsisdn.constants.MsConstants;
import ec.otecel.allmsisdn.dto.integration.RecoverRedisRequestDTO;
import ec.otecel.allmsisdn.dto.integration.RecoverRedisResponseDTO;
import ec.otecel.allmsisdn.dto.integration.SaveRedisRequestDTO;
import ec.otecel.allmsisdn.dto.integration.SaveRedisResponseDTO;
import ec.otecel.allmsisdn.util.BasicOperationAdapter;
import ec.otecel.common.model.commontypes.ErrorCodeType;
import ec.otecel.common.model.commontypes.LayerType;
import ec.otecel.common.model.commontypes.LoggerAppType;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.common.model.rest.fault.MessageFaultDTO;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.error.util.ErrorUtil;
import ec.otecel.component.logs.config.LoggerService;
import ec.otecel.component.requester.base.BaseRequester;
import ec.otecel.component.requester.model.RestResponse;
import ec.otecel.component.requester.util.RestUtil;

@Component
public class RedisAdapter {

    private LoggerService loggerService;

    @Value("${otecel.api.redis.host:localhost}")
    private String redisHost;

    @Value("${otecel.api.redis.port:8080}")
    private String redisPort;

    @Value("${otecel.api.redis.path.save:/comp/redis/v2/saveRedisTimeToLive}")
    private String savePath;

    @Value("${otecel.api.redis.path.recover:/comp/redis/v2/getDataRedis}")
    private String recoverPath;

    @Value("${otecel.api.redis.timeout:20000}")
    private Integer redisTimeOut;

    @Value("${otecel.api.internal.https:false}")
    private boolean apiHttps;

    @Value("${otecel.api.token:null}")
    private String basicToken;

    @Autowired
    public RedisAdapter(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    public SaveRedisResponseDTO saveRedis(HeaderInType headerIn, SaveRedisRequestDTO request,
            String initialStep, String finalStep) throws ComponentException {

        String message = "consumo del legado Redis saveRedis";
        String step = "PASO " + initialStep + " - " + finalStep + " LEGADO: ";
        String stepError = "PASO " + initialStep + " - " + finalStep + " LEGADO_ERROR: ";

        try {
            return new BasicOperationAdapter<SaveRedisResponseDTO, SaveRedisRequestDTO>(
                    headerIn, new SaveRedisResponseDTO(), request) {

                public SaveRedisResponseDTO process(HeaderInType headerIn, SaveRedisRequestDTO request)
                        throws ComponentException {

                    Map<String, String> headerInMap = RestUtil.objectToMap(headerIn);
                    if (apiHttps) {
                        headerInMap.put(MsConstants.AUTHORIZATION, basicToken);
                    }

                    BaseRequester requester = new BaseRequester(redisHost, redisPort, savePath,
                            HttpMethod.POST.name(), request, headerInMap);
                    requester.setTimeout(redisTimeOut);
                    requester.setHttps(apiHttps);

                    RestResponse<SaveRedisResponseDTO, MessageFaultDTO> response = null;

                    try {
                        loggerService.logApp(uUidTransaction, Strings.EMPTY, uUidTransaction,
                                LoggerAppType.DSI_MCI_BODY_AUDIT_REQUEST.toString(),
                                step + "Se inicia " + message,
                                ErrorUtil.createErrorLocation(this.getClass().getSimpleName(),
                                        LayerType.INTEGRATION));

                        response = requester.run(SaveRedisResponseDTO.class, MessageFaultDTO.class, 5000);

                        loggerService.logApp(uUidTransaction, Strings.EMPTY, uUidTransaction,
                                LoggerAppType.DSI_MCI_BODY_AUDIT_REQUEST.toString(),
                                step + "Se realiza " + message + ", de manera correcta",
                                ErrorUtil.createErrorLocation(this.getClass().getSimpleName(),
                                        LayerType.INTEGRATION));

                        if (response.getCode() == 404) {
                            throw new ComponentException(ErrorCodeType.ERROR_SIN_RESULTADOS,
                                    response.getErrorResponse().getAppDetail().getExceptionAppCause(),
                                    true, new String[]{request.toString()},
                                    ErrorUtil.createErrorLocation(RedisAdapter.class.getSimpleName(),
                                            LayerType.INTEGRATION));
                        } else if (response.getCode() != 200) {
                            throw new ComponentException(ErrorCodeType.ERROR_INTERNO_SL,
                                    response.getErrorResponse().getExceptionMessage(),
                                    true, new String[]{request.toString()},
                                    ErrorUtil.createErrorLocation(RedisAdapter.class.getSimpleName(),
                                            LayerType.INTEGRATION));
                        }
                    } catch (JsonProcessingException e) {
                        throw new ComponentException(ErrorCodeType.ERROR_INTERNO_SL, true,
                                new String[]{MsConstants.RESULT_CODE_ERROR
                                        + redisHost + ":" + redisPort + "/" + savePath},
                                this.getClass().getSimpleName());
                    }

                    return response.getBodyResponse();
                }
            }.initializer(loggerService)
             .setCommonInfo(MsConstants.MS_SERVICE_REDIS, MsConstants.METHOD_NAME_SAVE_REDIS,
                     RedisAdapter.class.getSimpleName())
             .run();

        } catch (Exception e) {
            loggerService.logApp(UUID.randomUUID().toString(), Strings.EMPTY,
                    UUID.randomUUID().toString(),
                    LoggerAppType.DSI_MCI_BODY_AUDIT_RESPONSE.toString(),
                    stepError + "Se genera error en el " + message + MsConstants.RESPONSE + e.toString(),
                    ErrorUtil.createErrorLocation(this.getClass().getSimpleName(), LayerType.INTEGRATION));
            throw e;
        }
    }

    public RecoverRedisResponseDTO getDataRedis(HeaderInType headerIn, RecoverRedisRequestDTO request,
            String initialStep, String finalStep) throws ComponentException {

        String message = "consumo del legado Redis getDataRedis";
        String step = "PASO " + initialStep + " - " + finalStep + " LEGADO: ";
        String stepError = "PASO " + initialStep + " - " + finalStep + " LEGADO_ERROR: ";

        try {
            return new BasicOperationAdapter<RecoverRedisResponseDTO, RecoverRedisRequestDTO>(
                    headerIn, new RecoverRedisResponseDTO(), request) {

                public RecoverRedisResponseDTO process(HeaderInType headerIn, RecoverRedisRequestDTO request)
                        throws ComponentException {

                    Map<String, String> headerInMap = RestUtil.objectToMap(headerIn);
                    if (apiHttps) {
                        headerInMap.put(MsConstants.AUTHORIZATION, basicToken);
                    }

                    BaseRequester requester = new BaseRequester(redisHost, redisPort, recoverPath,
                            HttpMethod.POST.name(), request, headerInMap);
                    requester.setTimeout(redisTimeOut);
                    requester.setHttps(apiHttps);

                    RestResponse<RecoverRedisResponseDTO, MessageFaultDTO> response = null;

                    try {
                        loggerService.logApp(uUidTransaction, Strings.EMPTY, uUidTransaction,
                                LoggerAppType.DSI_MCI_BODY_AUDIT_REQUEST.toString(),
                                step + "Se inicia " + message,
                                ErrorUtil.createErrorLocation(this.getClass().getSimpleName(),
                                        LayerType.INTEGRATION));

                        response = requester.run(RecoverRedisResponseDTO.class, MessageFaultDTO.class, 5000);

                        loggerService.logApp(uUidTransaction, Strings.EMPTY, uUidTransaction,
                                LoggerAppType.DSI_MCI_BODY_AUDIT_REQUEST.toString(),
                                step + "Se realiza " + message + ", de manera correcta",
                                ErrorUtil.createErrorLocation(this.getClass().getSimpleName(),
                                        LayerType.INTEGRATION));

                        // En getDataRedis, un 404 = cache miss → devolver vacío para que el
                        // service haga fallback a NetCracker. NO lanzar excepción.
                        if (response.getCode() == 404) {
                            return new RecoverRedisResponseDTO();
                        } else if (response.getCode() != 200) {
                            throw new ComponentException(ErrorCodeType.ERROR_INTERNO_SL,
                                    response.getErrorResponse().getExceptionMessage(),
                                    true, new String[]{request.toString()},
                                    ErrorUtil.createErrorLocation(RedisAdapter.class.getSimpleName(),
                                            LayerType.INTEGRATION));
                        }
                    } catch (JsonProcessingException e) {
                        throw new ComponentException(ErrorCodeType.ERROR_INTERNO_SL, true,
                                new String[]{MsConstants.RESULT_CODE_ERROR
                                        + redisHost + ":" + redisPort + "/" + recoverPath},
                                this.getClass().getSimpleName());
                    }

                    return response.getBodyResponse();
                }
            }.initializer(loggerService)
             .setCommonInfo(MsConstants.MS_SERVICE_REDIS, MsConstants.METHOD_NAME_RECOVER_REDIS,
                     RedisAdapter.class.getSimpleName())
             .run();

        } catch (Exception e) {
            loggerService.logApp(UUID.randomUUID().toString(), Strings.EMPTY,
                    UUID.randomUUID().toString(),
                    LoggerAppType.DSI_MCI_BODY_AUDIT_RESPONSE.toString(),
                    stepError + "Se genera error en el " + message + MsConstants.RESPONSE + e.toString(),
                    ErrorUtil.createErrorLocation(this.getClass().getSimpleName(), LayerType.INTEGRATION));
            throw e;
        }
    }
}
