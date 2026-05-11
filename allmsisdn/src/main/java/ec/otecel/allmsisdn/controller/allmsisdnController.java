package ec.otecel.allmsisdn.controller;

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
import ec.otecel.allmsisdn.constants.MsConstants;
import ec.otecel.allmsisdn.util.BasicOperationController;
import ec.otecel.allmsisdn.util.ErrorMappingProperties;
import ec.otecel.allmsisdn.util.ErrorTOpenApiProperties;
import ec.otecel.allmsisdn.service.IAllMsisdnService;
import ec.otecel.allmsisdn.dto.exposition.AllMsisdnRequestDTO;
import ec.otecel.allmsisdn.dto.exposition.AllMsisdnResponseDTO;

@RestController
@RequestMapping(value = "${controller.properties.base-path}")
public class AllMsisdnController {

    private IAllMsisdnService service;
    private LoggerService loggerService;
    private SmartValidator validator;
    private HttpServletRequest httpServletRequest;
    private ServiceErrorAdapter adapterError;
    private ErrorTOpenApiProperties fmwErrors;
    private ErrorMappingProperties mappingErrors;

    @Autowired
    public AllMsisdnController(IAllMsisdnService service, LoggerService loggerService,
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
    @PostMapping(path = "allMsisdn", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity allMsisdn(@RequestHeader Map<String,String> headerReq,
            @RequestBody AllMsisdnRequestDTO request) throws ComponentException {
        return new BasicOperationController<AllMsisdnResponseDTO, AllMsisdnRequestDTO>(headerReq,
                new AllMsisdnResponseDTO(), request) {
            @Override
            public AllMsisdnResponseDTO process(HeaderInType headers, AllMsisdnRequestDTO request)
                    throws ComponentException {
                return service.allMsisdn(headers, request);
            }
        }.errors(adapterError, mappingErrors, fmwErrors)
         .initializer(loggerService, validator, httpServletRequest)
         .setCommonInfo(MsConstants.SERVICE, MsConstants.METHOD_NAME_ALLMSISDN, this.getClass().getSimpleName())
         .run();
    }
}