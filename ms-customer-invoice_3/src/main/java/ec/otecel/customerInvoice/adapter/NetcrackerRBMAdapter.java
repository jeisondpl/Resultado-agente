package ec.otecel.customerInvoice.adapter;

import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import ec.otecel.customerInvoice.constants.MsConstants;
import ec.otecel.customerInvoice.dto.exposition.GetInvoiceRequestDTO;
import ec.otecel.customerInvoice.dto.exposition.GetInvoiceResponseDTO;
import ec.otecel.customerInvoice.util.BasicOperationAdapter;
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
public class NetcrackerRBMAdapter {

    private LoggerService loggerService;

    @Value("${otecel.api.rbm.host:default_value}")
    private String host;

    @Value("${otecel.api.rbm.port:default_value}")
    private String port;

    @Value("${otecel.api.rbm.path.getInvoice:default_value}")
    private String pathGetInvoice;

    @Value("${otecel.api.rbm.timeout:default_value}")
    private String timeout;

    @Value("${otecel.api.token:default_value}")
    private String token;

    @Autowired
    public NetcrackerRBMAdapter(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    public GetInvoiceResponseDTO getInvoiceByAccountNumber(HeaderInType h, GetInvoiceRequestDTO req) throws ComponentException {
        String message = "consumo del legado Netcracker RBM getInvoiceByAccountNumber";
        String step = "PASO 1 - 1 LEGADO: ";
        String stepError = "PASO 1 - 1 LEGADO_ERROR: ";

        try {
            return new BasicOperationAdapter<GetInvoiceResponseDTO, GetInvoiceRequestDTO>(h, new GetInvoiceResponseDTO(), req) {
                public GetInvoiceResponseDTO process(HeaderInType headerIn, GetInvoiceRequestDTO request) throws ComponentException {
                    Map<String, String> headerInMap = RestUtil.objectToMap(headerIn);
                    if (Boolean.parseBoolean("true")) {
                        headerInMap.put(MsConstants.AUTHORIZATION, token);
                    }

                    BaseRequester requester = new BaseRequester(host, port, pathGetInvoice, HttpMethod.POST.name(), request, headerInMap);
                    requester.setTimeout(Integer.parseInt(timeout));
                    requester.setHttps(Boolean.parseBoolean("true"));

                    RestResponse<GetInvoiceResponseDTO, MessageFaultDTO> response = null;
                    try {
                        loggerService.logApp(UUID.randomUUID().toString(), Strings.EMPTY, UUID.randomUUID().toString(), LoggerAppType.DSI_MCI_BODY_AUDIT_REQUEST.toString(), step + "Se inicia " + message, ErrorUtil.createErrorLocation(this.getClass().getSimpleName(), LayerType.INTEGRATION));

                        response = requester.run(GetInvoiceResponseDTO.class, MessageFaultDTO.class, 5000);

                        loggerService.logApp(UUID.randomUUID().toString(), Strings.EMPTY, UUID.randomUUID().toString(), LoggerAppType.DSI_MCI_BODY_AUDIT_REQUEST.toString(), step + "Se realiza " + message + ", de manera correcta", ErrorUtil.createErrorLocation(this.getClass().getSimpleName(), LayerType.INTEGRATION));

                        if (response.getCode() == 404) {
                            throw new ComponentException(ErrorCodeType.ERROR_SIN_RESULTADOS, response.getErrorResponse().getAppDetail().getExceptionAppCause(), true, new String[]{request.toString()}, ErrorUtil.createErrorLocation(NetcrackerRBMAdapter.class.getSimpleName(), LayerType.INTEGRATION));
                        } else if (response.getCode() != 200) {
                            throw new ComponentException(ErrorCodeType.ERROR_INTERNO_SL, response.getErrorResponse().getExceptionMessage(), true, new String[]{request.toString()}, ErrorUtil.createErrorLocation(NetcrackerRBMAdapter.class.getSimpleName(), LayerType.INTEGRATION));
                        }
                    } catch (JsonProcessingException e) {
                        throw new ComponentException(ErrorCodeType.ERROR_INTERNO_SL, true, new String[]{MsConstants.RESULT_CODE_ERROR + host + ":" + port + "/" + pathGetInvoice}, this.getClass().getSimpleName());
                    }

                    return response.getBodyResponse();
                }
            }.initializer(loggerService)
             .setCommonInfo(MsConstants.MS_SERVICE_RBM, MsConstants.METHOD_NAME_GET_INVOICE, NetcrackerRBMAdapter.class.getSimpleName())
             .run();

        } catch (Exception e) {
            loggerService.logApp(UUID.randomUUID().toString(), Strings.EMPTY, UUID.randomUUID().toString(), LoggerAppType.DSI_MCI_BODY_AUDIT_RESPONSE.toString(), stepError + "Se genera error en el " + message + MsConstants.RESPONSE + e.toString(), ErrorUtil.createErrorLocation(this.getClass().getSimpleName(), LayerType.INTEGRATION));
            throw e;
        }
    }
}
