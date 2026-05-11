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
import ec.otecel.customerInvoice.dto.integration.GetInvoiceRequestDTO;
import ec.otecel.customerInvoice.dto.integration.GetInvoiceResponseDTO;
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

import jakarta.annotation.PostConstruct;

@Component
public class NetcrackerRBMAdapter {

    private LoggerService loggerService;

    @Value("${otecel.api.backend.host:default_value}")
    private String host;

    @Value("${otecel.api.backend.port:default_value}")
    private String port;

    @Value("${otecel.api.backend.path.getInvoiceByAccountNumber:default_value}")
    private String path;

    @Value("${otecel.api.backend.timeout:default_value}")
    private Integer timeout;

    @Value("${otecel.api.backend.https:default_value}")
    private boolean https;

    @Value("${otecel.api.token:}")
    private String token;

    @Autowired
    public NetcrackerRBMAdapter(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    public GetInvoiceResponseDTO getInvoiceByAccountNumber(HeaderInType h, GetInvoiceRequestDTO req) throws ComponentException {
        return new BasicOperationAdapter<GetInvoiceResponseDTO, GetInvoiceRequestDTO>(h, new GetInvoiceResponseDTO(), req) {
            public GetInvoiceResponseDTO process(HeaderInType headerIn, GetInvoiceRequestDTO request) throws ComponentException {
                Map<String, String> headerInMap = RestUtil.objectToMap(headerIn);
                if (https) {
                    headerInMap.put(MsConstants.AUTHORIZATION, token);
                }

                BaseRequester requester = new BaseRequester(host, port, path, HttpMethod.POST.name(), request, headerInMap);
                requester.setTimeout(timeout);
                requester.setHttps(https);

                RestResponse<GetInvoiceResponseDTO, MessageFaultDTO> response = null;
                try {
                    loggerService.logApp(UUID.randomUUID().toString(), Strings.EMPTY, UUID.randomUUID().toString(), LoggerAppType.DSI_MCI_BODY_AUDIT_REQUEST.toString(), "Iniciando consumo del legado para obtener la factura por número de cuenta.", MsConstants.SERVICE);
                    response = requester.run(GetInvoiceResponseDTO.class, MessageFaultDTO.class, 5000);

                    if (response.getCode() == 404) {
                        throw new ComponentException(ErrorCodeType.ERROR_SIN_RESULTADOS, response.getErrorResponse().getAppDetail().getExceptionAppCause(), true, new String[]{request.toString()}, ErrorUtil.createErrorLocation(NetcrackerRBMAdapter.class.getSimpleName(), LayerType.INTEGRATION));
                    } else if (response.getCode() != 200) {
                        throw new ComponentException(ErrorCodeType.ERROR_INTERNO_SL, response.getErrorResponse().getExceptionMessage(), true, new String[]{request.toString()}, ErrorUtil.createErrorLocation(NetcrackerRBMAdapter.class.getSimpleName(), LayerType.INTEGRATION));
                    }
                } catch (JsonProcessingException e) {
                    throw new ComponentException(ErrorCodeType.ERROR_INTERNO_SL, true, new String[]{MsConstants.RESULT_CODE_ERROR + host + ":" + port + "/" + path}, this.getClass().getSimpleName());
                }
                return response.getBodyResponse();
            }
        }.initializer(loggerService)
         .setCommonInfo(MsConstants.SERVICE, MsConstants.METHOD_NAME_GET_INVOICE_BY_ACCOUNT_NUMBER, NetcrackerRBMAdapter.class.getSimpleName())
         .run();
    }
}
