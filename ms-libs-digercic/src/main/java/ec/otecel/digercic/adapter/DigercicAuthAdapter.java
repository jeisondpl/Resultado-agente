package ec.otecel.digercic.adapter;

import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import ec.otecel.digercic.constants.MsConstants;
import ec.otecel.digercic.dto.exposition.DigercicAuthRequestDTO;
import ec.otecel.digercic.dto.exposition.DigercicAuthResponseDTO;
import ec.otecel.digercic.util.BasicOperationAdapter;
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

import jakarta.annotation.PostConstruct;

@Component
public class DigercicAuthAdapter {

    private LoggerService loggerService;

    @Value("${otecel.api.digercic.host:app74-pre.registrocivil.gob.ec}")
    private String apiHost;

    @Value("${otecel.api.digercic.port:443}")
    private String apiPort;

    @Value("${otecel.api.digercic.auth.path:WS-RCivil-Rest/api/auth}")
    private String authPath;

    @Value("${otecel.api.digercic.auth.timeout:15000}")
    private String apiTimeOut;

    @Value("${otecel.api.internal.https:true}")
    private String apiHttps;

    @Value("${otecel.api.digercic.auth.username:<DIGERCIC_USERNAME>}")
    private String username;

    @Value("${otecel.api.digercic.auth.password:<DIGERCIC_PASSWORD>}")
    private String password;

    @Autowired
    public DigercicAuthAdapter(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @PostConstruct
    public void init() {
        apiHost = apiHost.trim();
        apiPort = apiPort.trim();
        authPath = authPath.trim();
        username = username.trim();
        password = password.trim();
    }

    public DigercicAuthResponseDTO getToken(HeaderInType headerIn, DigercicAuthRequestDTO request,
            String initialStep, String finalStep) throws ComponentException {

        String message = "consumo del legado DIGERCIC para obtener token";
        String step = "PASO " + initialStep + " - " + finalStep + " LEGADO: ";
        String stepError = "PASO " + initialStep + " - " + finalStep + " LEGADO_ERROR: ";

        try {
            return new BasicOperationAdapter<DigercicAuthResponseDTO, DigercicAuthRequestDTO>(
                    headerIn, new DigercicAuthResponseDTO(), request) {

                public DigercicAuthResponseDTO process(HeaderInType headerIn, DigercicAuthRequestDTO request)
                        throws ComponentException {

                    Map<String, String> headerInMap = RestUtil.objectToMap(headerIn);
                    if (Boolean.parseBoolean(apiHttps)) {
                        headerInMap.put(MsConstants.AUTHORIZATION, "Basic " + username + ":" + password);
                    }

                    BaseRequester requester = new BaseRequester(apiHost, apiPort, authPath,
                            HttpMethod.POST.name(), request, headerInMap);
                    requester.setTimeout(Integer.parseInt(apiTimeOut));
                    requester.setHttps(Boolean.parseBoolean(apiHttps));

                    RestResponse<DigercicAuthResponseDTO, MessageFaultDTO> response = null;

                    try {
                        loggerService.logApp(UUID.randomUUID().toString(), Strings.EMPTY, UUID.randomUUID().toString(),
                                LoggerAppType.DSI_MCI_BODY_AUDIT_REQUEST.toString(),
                                step + "Se inicia " + message,
                                ErrorUtil.createErrorLocation(this.getClass().getSimpleName(), LayerType.INTEGRATION));

                        response = requester.run(DigercicAuthResponseDTO.class, MessageFaultDTO.class, 5000);

                        loggerService.logApp(UUID.randomUUID().toString(), Strings.EMPTY, UUID.randomUUID().toString(),
                                LoggerAppType.DSI_MCI_BODY_AUDIT_REQUEST.toString(),
                                step + "Se realiza " + message + ", de manera correcta",
                                ErrorUtil.createErrorLocation(this.getClass().getSimpleName(), LayerType.INTEGRATION));

                        if (response.getCode() == 404) {
                            throw new ComponentException(ErrorCodeType.ERROR_SIN_RESULTADOS,
                                    response.getErrorResponse().getAppDetail().getExceptionAppCause(),
                                    true, new String[]{request.toString()},
                                    ErrorUtil.createErrorLocation(DigercicAuthAdapter.class.getSimpleName(), LayerType.INTEGRATION));
                        } else if (response.getCode() != 200) {
                            throw new ComponentException(ErrorCodeType.ERROR_INTERNO_SL,
                                    response.getErrorResponse().getExceptionMessage(),
                                    true, new String[]{request.toString()},
                                    ErrorUtil.createErrorLocation(DigercicAuthAdapter.class.getSimpleName(), LayerType.INTEGRATION));
                        }
                    } catch (JsonProcessingException e) {
                        throw new ComponentException(ErrorCodeType.ERROR_INTERNO_SL, true,
                                new String[]{MsConstants.RESULT_CODE_ERROR + apiHost + ":" + apiPort + "/" + authPath},
                                this.getClass().getSimpleName());
                    }

                    return response.getBodyResponse();
                }
            }.initializer(loggerService)
             .setCommonInfo(MsConstants.MS_SERVICE_DAC, MsConstants.METHOD_NAME_GET_TOKEN,
                     DigercicAuthAdapter.class.getSimpleName())
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
