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
import ec.otecel.digercic.dto.exposition.DigercicDacRequestDTO;
import ec.otecel.digercic.dto.exposition.DigercicDacResponseDTO;
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
public class DigercicDacAdapter {

    private LoggerService loggerService;

    @Value("${otecel.api.digercic.host:app74-pre.registrocivil.gob.ec}")
    private String apiHost;

    @Value("${otecel.api.digercic.port:443}")
    private String apiPort;

    @Value("${otecel.api.digercic.dac.path:WS-RCivil-Rest/api/ConsultaDAC}")
    private String apiPath;

    @Value("${otecel.api.digercic.dac.timeout:30000}")
    private String apiTimeOut;

    @Value("${otecel.api.internal.https:true}")
    private String apiHttps;

    @Value("${otecel.api.token:default_value}")
    private String basicToken;

    @Autowired
    public DigercicDacAdapter(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    /**
     * Limpia espacios y tabs invisibles que un editor del .properties pudo dejar
     * en las propiedades String inyectadas (host, path, token). Sin esto, un
     * espacio en `Authorization: Bearer abc ` rompe la auth y un `" /api/foo"`
     * produce 404 en el legado. Ver L-29.
     */
    @PostConstruct
    public void init() {
        apiHost = apiHost.trim();
        apiPort = apiPort.trim();
        apiPath = apiPath.trim();
        basicToken = basicToken.trim();
    }

    public DigercicDacResponseDTO queryByDac(HeaderInType headerIn, DigercicDacRequestDTO request,
            String initialStep, String finalStep) throws ComponentException {

        String message = "consumo del legado DAC";
        String step = "PASO " + initialStep + " - " + finalStep + " LEGADO: ";
        String stepError = "PASO " + initialStep + " - " + finalStep + " LEGADO_ERROR: ";

        try {
            return new BasicOperationAdapter<DigercicDacResponseDTO, DigercicDacRequestDTO>(
                    headerIn, new DigercicDacResponseDTO(), request) {

                public DigercicDacResponseDTO process(HeaderInType headerIn, DigercicDacRequestDTO request)
                        throws ComponentException {

                    Map<String, String> headerInMap = RestUtil.objectToMap(headerIn);
                    if (Boolean.parseBoolean(apiHttps)) {
                        headerInMap.put(MsConstants.AUTHORIZATION, basicToken);
                    }

                    BaseRequester requester = new BaseRequester(apiHost, apiPort, apiPath,
                            HttpMethod.POST.name(), request, headerInMap);
                    // Parsear los @Value String al tipo que espera BaseRequester (ver L-39).
                    requester.setTimeout(Integer.parseInt(apiTimeOut));
                    requester.setHttps(Boolean.parseBoolean(apiHttps));

                    RestResponse<DigercicDacResponseDTO, MessageFaultDTO> response = null;

                    try {
                        loggerService.logApp(UUID.randomUUID().toString(), Strings.EMPTY, UUID.randomUUID().toString(),
                                LoggerAppType.DSI_MCI_BODY_AUDIT_REQUEST.toString(),
                                step + "Se inicia " + message,
                                ErrorUtil.createErrorLocation(this.getClass().getSimpleName(), LayerType.INTEGRATION));

                        response = requester.run(DigercicDacResponseDTO.class, MessageFaultDTO.class, 5000);

                        loggerService.logApp(UUID.randomUUID().toString(), Strings.EMPTY, UUID.randomUUID().toString(),
                                LoggerAppType.DSI_MCI_BODY_AUDIT_REQUEST.toString(),
                                step + "Se realiza " + message + ", de manera correcta",
                                ErrorUtil.createErrorLocation(this.getClass().getSimpleName(), LayerType.INTEGRATION));

                        if (response.getCode() == 404) {
                            throw new ComponentException(ErrorCodeType.ERROR_SIN_RESULTADOS,
                                    response.getErrorResponse().getAppDetail().getExceptionAppCause(),
                                    true, new String[]{request.toString()},
                                    ErrorUtil.createErrorLocation(DigercicDacAdapter.class.getSimpleName(), LayerType.INTEGRATION));
                        } else if (response.getCode() != 200) {
                            throw new ComponentException(ErrorCodeType.ERROR_INTERNO_SL,
                                    response.getErrorResponse().getExceptionMessage(),
                                    true, new String[]{request.toString()},
                                    ErrorUtil.createErrorLocation(DigercicDacAdapter.class.getSimpleName(), LayerType.INTEGRATION));
                        }

                    } catch (JsonProcessingException e) {
                        throw new ComponentException(ErrorCodeType.ERROR_INTERNO_SL, true,
                                new String[]{MsConstants.RESULT_CODE_ERROR + apiHost + ":" + apiPort + "/" + apiPath},
                                this.getClass().getSimpleName());
                    }

                    return response.getBodyResponse();
                }
            }.initializer(loggerService)
             .setCommonInfo(MsConstants.MS_SERVICE_DAC, MsConstants.METHOD_NAME_QUERY_BY_DAC,
                     DigercicDacAdapter.class.getSimpleName())
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
