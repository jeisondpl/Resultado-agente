
package ec.otecel.allmsisdn.exception;

import ec.otecel.common.model.commontypes.ErrorCodeType;
import ec.otecel.component.error.adapter.ServiceErrorAdapter;
import ec.otecel.allmsisdn.util.ErrorMappingProperties;
import ec.otecel.allmsisdn.util.ErrorTOpenApiProperties;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import ec.otecel.allmsisdn.constants.MsConstants;
import ec.otecel.common.model.commontypes.LoggerAppType;
import ec.otecel.common.model.rest.fault.AppDetailDTO;
import ec.otecel.common.model.rest.fault.ExceptionProtocolDTO;
import ec.otecel.common.model.rest.fault.MessageFaultDTO;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.logs.config.LoggerService;

import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.stream.Collectors;

/**
 * Manejador global de excepciones optimizado.
 * Se centraliza la lógica de construcción de respuesta y logging para cumplir con DRY y Clean Code.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final LoggerService loggerService;
    private final ServiceErrorAdapter adapterError;
    private final ErrorTOpenApiProperties fmwErrors;
    private final ErrorMappingProperties mappingErrors;
    protected String uUidTransaction = null;

    public GlobalExceptionHandler(LoggerService loggerService,
                                  ServiceErrorAdapter adapterError,
                                  ErrorTOpenApiProperties fmwErrors,
                                  ErrorMappingProperties mappingErrors) {
        this.loggerService = loggerService;
        this.adapterError = adapterError;
        this.fmwErrors = fmwErrors;
        this.mappingErrors = mappingErrors;
    }
    
    // --- Handlers Específicos ---

    @ExceptionHandler(ComponentException.class)
    public ResponseEntity<MessageFaultDTO> handleComponentException(ComponentException ex, WebRequest request) {
        HttpStatus status = MsConstants.HTTP_CODE_BAD_REQUEST.equals(ex.getErrorCode()) 
                ? HttpStatus.BAD_REQUEST 
                : HttpStatus.INTERNAL_SERVER_ERROR;

        return buildResponse(ex, request, status, ex.getErrorCode().getCode(), ex.getMessage(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageFaultDTO> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + (error.getDefaultMessage() != null ? error.getDefaultMessage() : "Valor inválido"))
                .collect(Collectors.joining(" | "));

        return buildResponse(ex, request, HttpStatus.BAD_REQUEST, MsConstants.HTTP_CODE_BAD_REQUEST, MsConstants.BAD_REQUEST, detail);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MessageFaultDTO> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return buildResponse(ex, request, HttpStatus.BAD_REQUEST, MsConstants.HTTP_CODE_BAD_REQUEST, MsConstants.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<MessageFaultDTO> handleNullPointerException(NullPointerException ex, WebRequest request) {
        return buildResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, MsConstants.EXCEPTION_CODE_1001, MsConstants.INTERNAL_SERVER_ERROR, "Null reference detected");
    }

    /**
     * CASO 405: Método no soportado (GET vs POST).
     * 
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<MessageFaultDTO> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        return buildResponse(ex, request, HttpStatus.METHOD_NOT_ALLOWED,
                MsConstants.EXCEPTION_CODE_1001, MsConstants.METHOD_NOT_ALLOWED, null);
    }

    /**
     * CASO 404: El error de "No static resource" que mencionaste.
     *
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<MessageFaultDTO> handleNotFound(NoResourceFoundException ex, WebRequest request) {
        return buildResponse(ex, request, HttpStatus.NOT_FOUND,
                MsConstants.EXCEPTION_CODE_1001, "resource not found.", null);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<MessageFaultDTO> handleResponseStatus(ResponseStatusException ex, WebRequest request) {
        var status = HttpStatus.valueOf(ex.getStatusCode().value());
        return buildResponse(ex, request, status, 
                MsConstants.EXCEPTION_CODE_1001, ex.getReason(), null);
    }

    /**
     * Este método maneja las excepciones HttpMessageNotReadableException que pueden
     * ocurrir cuando hay un problema con el formato de los datos de la solicitud (JSON mal formado).
     *
     * @param ex La excepción HttpMessageNotReadableException que se ha lanzado.
     * @param request La solicitud web que causó la excepción.
     * @return ResponseEntity<MessageFaultDTO> Una respuesta HTTP con un objeto MessageFaultDTO.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MessageFaultDTO> handleMessageNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
        loggerService.normalLogError("Error handleMessageNotReadable", ex);

        // Crear ComponentException con el tipo de error de parámetros incorrectos
        ComponentException er = new ComponentException(
                ErrorCodeType.ERROR_PARAMETROS_INCORRECTOS,
                null,
                true, null,
                MsConstants.SERVICE);

        // Invocar Transformador Errores
        MessageFaultDTO error = adapterError.errorHandlerRest(er, fmwErrors.getErrors(), mappingErrors.getErrors());
        error.setExceptionDetail(null);
        error.getAppDetail().setExceptionAppMessage(MsConstants.INVALID_PARAMETER_BODY);
        error.getAppDetail().setExceptionAppCause(MsConstants.INVALID_PARAMETER_BODY);

        return new ResponseEntity<>(error,
                HttpStatus.valueOf(Integer.parseInt(error.getExceptionProtocol().getCode())));
    }

    private ResponseEntity<MessageFaultDTO> buildResponse(Exception ex, WebRequest request, HttpStatus status, String appCode, String message, String detail) {
        String path = request.getDescription(false).replace("uri=", "");
        
        AppDetailDTO appDetail = createAppDetail(appCode, message, MsConstants.SERVICE, detail);
        ExceptionProtocolDTO protocol = createProtocol(status);
        MessageFaultDTO fault = createMessageFault(appCode, message, detail, protocol, appDetail);

        logError(ex, path, message, detail);

        return ResponseEntity.status(status).body(fault);
    }

    private AppDetailDTO createAppDetail(String code, String message, String path, String cause) {
        AppDetailDTO dto = new AppDetailDTO();
        dto.setExceptionAppCode(MsConstants.CODE_ERROR_DATA);
        dto.setExceptionAppMessage(message);
        dto.setExceptionAppLocation(path);
        dto.setExceptionAppCause(cause != null ? cause : message);
        return dto;
    }

    private ExceptionProtocolDTO createProtocol(HttpStatus status) {
        ExceptionProtocolDTO dto = new ExceptionProtocolDTO();
        dto.setCode(String.valueOf(status.value()));
        dto.setDescription(status.getReasonPhrase());
        return dto;
    }

    private MessageFaultDTO createMessageFault(String code, String message, String detail, ExceptionProtocolDTO protocol, AppDetailDTO appDetail) {
        MessageFaultDTO fault = new MessageFaultDTO();
        fault.setExceptionCategory(MsConstants.SYNTAX);
        fault.setExceptionCode(code);
        fault.setExceptionMessage(message);
        fault.setExceptionDetail(detail);
        fault.setExceptionSeverity(MsConstants.CHAR_E_ERROR);
        fault.setExceptionType(MsConstants.T_OPEN_API);
        fault.setExceptionProtocol(protocol);
        fault.setAppDetail(appDetail);
        return fault;
    }

    private void logError(Exception ex, String path, String message, String detail) {
        String logMessage = String.format("%s | Path: %s | Message: %s | Detail: %s", 
                ex.getClass().getSimpleName(), path, message, (detail != null ? detail : "N/A"));

        loggerService.logApp(
                uUidTransaction, 
                Strings.EMPTY, 
                uUidTransaction, 
                LoggerAppType.DSI_MCI_BODY_AUDIT_REQUEST.toString(), 
                logMessage, 
                ex.getClass().getName()
        );
    }
}