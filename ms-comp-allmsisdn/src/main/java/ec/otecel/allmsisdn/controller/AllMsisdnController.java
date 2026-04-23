package ec.otecel.allmsisdn.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import ec.otecel.allmsisdn.constants.MsConstants;
import ec.otecel.allmsisdn.dto.exposition.AllMsisdnRequestDTO;
import ec.otecel.allmsisdn.dto.exposition.AllMsisdnResponseDTO;
import ec.otecel.allmsisdn.service.IAllMsisdnService;
import ec.otecel.allmsisdn.util.BasicOperationController;
import ec.otecel.allmsisdn.util.ErrorMappingProperties;
import ec.otecel.allmsisdn.util.ErrorTOpenApiProperties;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.component.error.adapter.ServiceErrorAdapter;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.logs.config.LoggerService;

@RestController
@RequestMapping("${controller.properties.base-path}")
public class AllMsisdnController {

    private final IAllMsisdnService service;
    private final LoggerService loggerService;
    private final SmartValidator validator;
    private final HttpServletRequest httpServletRequest;
    private final ServiceErrorAdapter adapterError;
    private final ErrorTOpenApiProperties fmwErrors;
    private final ErrorMappingProperties mappingErrors;

    @Autowired
    public AllMsisdnController(IAllMsisdnService service,
                                LoggerService loggerService,
                                SmartValidator validator,
                                HttpServletRequest httpServletRequest,
                                ServiceErrorAdapter adapterError,
                                ErrorTOpenApiProperties fmwErrors,
                                ErrorMappingProperties mappingErrors) {
        this.service = service;
        this.loggerService = loggerService;
        this.validator = validator;
        this.httpServletRequest = httpServletRequest;
        this.adapterError = adapterError;
        this.fmwErrors = fmwErrors;
        this.mappingErrors = mappingErrors;
    }

    @PostMapping("/allmsisdn")
    public ResponseEntity<?> allMsisdn(@Valid @RequestBody AllMsisdnRequestDTO request,
                                         @RequestHeader Map<String, String> headers)
            throws ComponentException {
        return new BasicOperationController<AllMsisdnResponseDTO, AllMsisdnRequestDTO>(
                headers, new AllMsisdnResponseDTO(), request) {
            @Override
            public AllMsisdnResponseDTO process(HeaderInType headerIn, AllMsisdnRequestDTO req)
                    throws ComponentException {
                return service.allMsisdn(headerIn, req);
            }
        }.initializer(loggerService, validator, httpServletRequest)
         .errors(adapterError, mappingErrors, fmwErrors)
         .setCommonInfo(MsConstants.SERVICE, MsConstants.METHOD1, getClass().getSimpleName())
         .preRun();
    }
}
