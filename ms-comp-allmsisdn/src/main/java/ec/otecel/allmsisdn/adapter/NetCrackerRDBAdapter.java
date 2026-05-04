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
import ec.otecel.allmsisdn.dto.exposition.AllMsisdnRequestDTO;
import ec.otecel.allmsisdn.dto.exposition.AllMsisdnResponseDTO;
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
public class NetCrackerRDBAdapter {

    private LoggerService loggerService;

    @Value("${otecel.api.rdb.host:backendpre.movistar.com.ec}")
    private String apiHost;

    @Value("${otecel.api.rdb.port:32101}")
    private String apiPort;

    @Value("${otecel.api.rdb.path.all.msisdn:/netcrackerrdb/allMsisdn}")
    private String apiPath;

    @Value("${otecel.api.rdb.timeout:40000}")
    private Integer apiTimeOut;

    @Value("${otecel.api.internal.https:true}")
    private boolean apiHttps;

    @Value("${otecel.api.token:}")
    private String basicToken;

    @Autowired
    public NetCrackerRDBAdapter(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    public AllMsisdnResponseDTO allMsisdn(HeaderInType headerIn, AllMsisdnRequestDTO request,
            String initialStep, String finalStep) throws ComponentException {

        String message = "consumo del legado NetCracker RDB allMsisdn";
        String step = "PASO " + initialStep + " - " + finalStep + " LEGADO: ";
        String stepError = "PASO " + initialStep + " - " + finalStep + " LEGADO_ERROR: ";

        try {
            return new BasicOperationAdapter<AllMsisdnResponseDTO, AllMsisdnRequestDTO>(
                    headerIn, new AllMsisdnResponseDTO(), request) {

                public AllMsisdnResponseDTO process(HeaderInType headerIn, AllMsisdnRequestDTO request)
                        throws ComponentException {

                    Map<String, String> headerInMap = RestUtil.objectToMap(headerIn);
                    if (apiHttps) {
                        headerInMap.put(MsConstants.AUTHORIZATION, basicToken);
                    }

                    BaseRequester requester = new BaseRequester(apiHost, apiPort, apiPath,
                            HttpMethod.POST.name(), request, headerInMap);
                    requester.setTimeout(apiTimeOut);
                    requester.setHttps(apiHttps);

                    RestResponse<AllMsisdnResponseDTO, MessageFaultDTO> response = null;

                    try {
                        loggerService.logApp(uUidTransaction, Strings.EMPTY, uUidTransaction,
                                LoggerAppType.DSI_MCI_BODY_AUDIT_REQUEST.toString(),
                                step + "Se inicia " + message,
                                ErrorUtil.createErrorLocation(this.getClass().getSimpleName(),
                                        LayerType.INTEGRATION));

                        response = requester.run(AllMsisdnResponseDTO.class, MessageFaultDTO.class, 5000);

                        loggerService.logApp(uUidTransaction, Strings.EMPTY, uUidTransaction,
                                LoggerAppType.DSI_MCI_BODY_AUDIT_REQUEST.toString(),
                                step + "Se realiza " + message + ", de manera correcta",
                                ErrorUtil.createErrorLocation(this.getClass().getSimpleName(),
                                        LayerType.INTEGRATION));

                        if (response.getCode() == 404) {
                            throw new ComponentException(ErrorCodeType.ERROR_SIN_RESULTADOS,
                                    response.getErrorResponse().getAppDetail().getExceptionAppCause(),
                                    true, new String[]{request.toString()},
                                    ErrorUtil.createErrorLocation(NetCrackerRDBAdapter.class.getSimpleName(),
                                            LayerType.INTEGRATION));
                        } else if (response.getCode() != 200) {
                            throw new ComponentException(ErrorCodeType.ERROR_INTERNO_SL,
                                    response.getErrorResponse().getExceptionMessage(),
                                    true, new String[]{request.toString()},
                                    ErrorUtil.createErrorLocation(NetCrackerRDBAdapter.class.getSimpleName(),
                                            LayerType.INTEGRATION));
                        }

                    } catch (JsonProcessingException e) {
                        throw new ComponentException(ErrorCodeType.ERROR_INTERNO_SL, true,
                                new String[]{MsConstants.RESULT_CODE_ERROR
                                        + apiHost + ":" + apiPort + "/" + apiPath},
                                this.getClass().getSimpleName());
                    }

                    return response.getBodyResponse();
                }
            }.initializer(loggerService)
             .setCommonInfo(MsConstants.MS_SERVICE_NETCRACKER, MsConstants.METHOD_NAME_ALL_MSISDN,
                     NetCrackerRDBAdapter.class.getSimpleName())
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
