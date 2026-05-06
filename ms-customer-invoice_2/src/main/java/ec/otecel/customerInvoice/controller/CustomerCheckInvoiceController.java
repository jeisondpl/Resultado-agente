package ec.otecel.customerInvoice.controller;

import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ec.otecel.component.error.adapter.ServiceErrorAdapter;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.logs.config.LoggerService;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.customerInvoice.constants.MsConstants;
import ec.otecel.customerInvoice.util.BasicOperationController;
import ec.otecel.customerInvoice.util.ErrorMappingProperties;
import ec.otecel.customerInvoice.util.ErrorTOpenApiProperties;
import ec.otecel.customerInvoice.service.ICustomerCheckInvoiceService;
import ec.otecel.customerInvoice.dto.exposition.CheckInvoiceRequestDTO;
import ec.otecel.customerInvoice.dto.exposition.CheckInvoiceResponseDTO;

@RestController
@RequestMapping(value = "/checkInvoice")
public class CustomerCheckInvoiceController {

    private ICustomerCheckInvoiceService service;
    private LoggerService loggerService;
    private SmartValidator validator;
    private HttpServletRequest httpServletRequest;
    private ServiceErrorAdapter adapterError;
    private ErrorTOpenApiProperties fmwErrors;
    private ErrorMappingProperties mappingErrors;

    @Autowired
    public CustomerCheckInvoiceController(ICustomerCheckInvoiceService service, LoggerService loggerService,
            SmartValidator validator, HttpServletRequest httpServletRequest,
            ServiceErrorAdapter adapterError, ErrorTOpenApiProperties fmwErrors,
            ErrorMappingProperties mappingErrors) {
        this.service = service;
        this.loggerService = loggerService;
        this.validator = validator;
        this.httpServletRequest = httpServletRequest;
        this.adapterError = adapterError;
        this.fmwErrors = fmwErrors;
        this.mappingErrors = mappingErrors;
    }

    @SuppressWarnings("rawtypes")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CheckInvoiceResponseDTO> checkInvoice(@RequestHeader Map<String, String> headerReq,
            @RequestBody CheckInvoiceRequestDTO request) throws ComponentException {
        return new BasicOperationController<CheckInvoiceResponseDTO, CheckInvoiceRequestDTO>(headerReq,
                new CheckInvoiceResponseDTO(), request) {
            @Override
            public CheckInvoiceResponseDTO process(HeaderInType headers, CheckInvoiceRequestDTO request)
                    throws ComponentException {
                return service.checkInvoice(headers, request);
            }
        }.errors(adapterError, mappingErrors, fmwErrors)
         .initializer(loggerService, validator, httpServletRequest)
         .setCommonInfo(MsConstants.SERVICE, MsConstants.METHOD_NAME_CHECK_INVOICE, this.getClass().getSimpleName())
         .run();
    }
}
