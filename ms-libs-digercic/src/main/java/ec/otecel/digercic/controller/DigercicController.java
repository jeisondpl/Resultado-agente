package ec.otecel.digercic.controller;

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
import ec.otecel.digercic.constants.MsConstants;
import ec.otecel.digercic.util.BasicOperationController;
import ec.otecel.digercic.util.ErrorMappingProperties;
import ec.otecel.digercic.util.ErrorTOpenApiProperties;
import ec.otecel.digercic.service.IDigercicService;
import ec.otecel.digercic.dto.exposition.DigercicAuthRequestDTO;
import ec.otecel.digercic.dto.exposition.DigercicAuthResponseDTO;

/**
 * Class controladora que expone los metodos correspondientes al servicio Digercic
 *
 * @author nombre apellido correo@indracompany.com
 */
@RestController
@RequestMapping(value = "${controller.properties.base-path}")
public class DigercicController {

    private IDigercicService service;
    private LoggerService loggerService;
    private SmartValidator validator;
    private HttpServletRequest httpServletRequest;
    private ServiceErrorAdapter adapterError;
    private ErrorTOpenApiProperties fmwErrors;
    private ErrorMappingProperties mappingErrors;

    @Autowired
    public DigercicController(IDigercicService service, LoggerService loggerService,
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
    @PostMapping(path = "getToken", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DigercicAuthResponseDTO> getToken(@RequestHeader Map<String, String> headerReq,
            @RequestBody DigercicAuthRequestDTO request) throws ComponentException {
        return new BasicOperationController<DigercicAuthResponseDTO, DigercicAuthRequestDTO>(headerReq,
                new DigercicAuthResponseDTO(), request) {
            @Override
            public DigercicAuthResponseDTO process(HeaderInType headers, DigercicAuthRequestDTO request)
                    throws ComponentException {
                return service.getToken(headers, request);
            }
        }.errors(adapterError, mappingErrors, fmwErrors)
         .initializer(loggerService, validator, httpServletRequest)
         .setCommonInfo(MsConstants.SERVICE, MsConstants.METHOD_NAME_GET_TOKEN, this.getClass().getSimpleName())
         .run();
    }
}