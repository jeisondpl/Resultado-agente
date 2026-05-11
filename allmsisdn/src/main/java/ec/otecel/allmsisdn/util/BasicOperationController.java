package ec.otecel.allmsisdn.util;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.SmartValidator;
import ec.otecel.common.model.commontypes.ErrorCodeType;
import ec.otecel.common.model.commontypes.LayerType;
import ec.otecel.common.model.commontypes.LoggerAppType;
import ec.otecel.common.model.commontypes.MessageType;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.common.model.log.ExecutionInfoRuntimeDto;
import ec.otecel.common.model.log.ExecutionInfoToLogging;
import ec.otecel.common.model.rest.fault.MessageFaultDTO;
import ec.otecel.component.error.adapter.ServiceErrorAdapter;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.error.util.ErrorUtil;
import ec.otecel.component.logs.config.LoggerService;
import ec.otecel.component.logs.util.LoggerUtil;
import ec.otecel.component.requester.util.RestUtil;
import ec.otecel.allmsisdn.constants.MsConstants;

public abstract class BasicOperationController<R, P> {

	/**
	 * Referencia al request.
	 */
	@SuppressWarnings("unused")
	private HttpServletRequest httpServletRequest;

	/**
	 * Errores fmw
	 */

	private ErrorTOpenApiProperties fmwErrors;

	/**
	 * Mapeo de errores legado contra fmw
	 */

	private ErrorMappingProperties mappingErrors;

	/**
	 * Componente para realizar el mapeo de los errores.
	 */

	private ServiceErrorAdapter adapterError;

	/**
	 * Componente para manejo de log
	 */
	private LoggerService loggerService;

	/**
	 * Componente para validaciones.
	 */

	private SmartValidator validator;

	private Map<String, String> headers;

	private R response;
	private P request;

	protected String uUidTransaction = null;

	private String serviceName;
	private String operationName;

	private String className;

	protected BasicOperationController(Map<String, String> headers, R response, P request) {
		this.headers = headers;
		this.request = request;
		this.response = response;
	}

	public abstract R process(HeaderInType headers, P request) throws ComponentException;

	@SuppressWarnings("rawtypes")
	public ResponseEntity preRun() throws ComponentException {
		return run();
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity run() throws ComponentException {

		// Headers del contexto
		HttpHeaders headersOutMap = null;
		// DTO de respuesta homologada
		MessageFaultDTO error = null;

		processHeader();

		// Se almacena la informaci�n de ejecuci�n del servicio.
		ExecutionInfoRuntimeDto execRuntimeInfo = new ExecutionInfoRuntimeDto(new Date());
		// Variable para almacenar la respuesta encriptada.
		R responseData = null;
		// Se define la ubicacion en caso de error.
		String location = ErrorUtil.createErrorLocation(serviceName, operationName, LayerType.EXPOSITION);

		// Se obtiene la informacion de header.
		HeaderInType headerIn = null;

		try {

			RestUtil.validateHeaderInPatterns(headers, validator, serviceName, operationName);

			headerIn = CommonUtil.getHeaderIn(headers, serviceName, operationName);

			loggerService.logApp(uUidTransaction, headerIn.getExecId(), uUidTransaction,
					LoggerAppType.EXP_MCI_BODY_AUDIT_REQUEST.toString(), request.toString(), serviceName);

			loggerService.logApp(uUidTransaction, headerIn.getExecId(), uUidTransaction,
					LoggerAppType.EXP_MCI_HEADERS_AUDIT_REQUEST.toString(), headerIn.toString(), serviceName);

			// Se realiza la validaci�n del body
			RestUtil.validateBodyMessage(request, validator, location);

			// Se registra la fecha de inicio de invocaci�n al legado.
			execRuntimeInfo.setLegacyInitialDate(new Date());

			responseData = process(headerIn, request);

			// Se registra la fecha de fin de invocaci�n al legado
			execRuntimeInfo.setLegacyEndDate(new Date());

			// Se registra la respuesta a retornar.
			loggerService.logApp(uUidTransaction, headerIn.getExecId(), uUidTransaction,
					LoggerAppType.EXP_MCI_BODY_AUDIT_RESPONSE.toString(), response.toString(), serviceName);

			headersOutMap = RestUtil.getHeaderOut(headerIn, MessageType.RESPONSE.value());

			loggerService.logApp(uUidTransaction, headerIn.getExecId(), uUidTransaction,
					LoggerAppType.EXP_MCI_HEADERS_AUDIT_RESPONSE.toString(), headersOutMap.toString(), serviceName);

			return new ResponseEntity<R>(responseData, headersOutMap, HttpStatus.OK);

			// Se codifica y asigna la respuesta del servicio.
		} catch (ComponentException e) {

			/* Invocar Transformador Errores */
			error = adapterError.errorHandlerRest(e, fmwErrors.getErrors(), mappingErrors.getErrors());

			// Se registra en log de aplicacion error
			loggerService.logApp(uUidTransaction, Strings.EMPTY, uUidTransaction,
					LoggerAppType.EXP_AUDIT_BODY_ERROR_HANDLER.toString(), error.toString(), className);

			headersOutMap = RestUtil.getHeaderOut(headerIn, MessageType.ERROR.value());
			error = createMessageFaultDTO(error);
			return new ResponseEntity<MessageFaultDTO>(error, headersOutMap,
					HttpStatus.valueOf(Integer.parseInt(error.getExceptionProtocol().getCode())));
		} catch (Exception e) {

			if (headerIn == null) {
				headerIn = new HeaderInType();
			}
			// Se transforma a ComponentException
			ComponentException exc = new ComponentException(ErrorUtil.getExceptionMessage(e), e,
					ErrorCodeType.ERROR_INESPERADO, true,
					new String[] { new StringBuilder(request.toString()).toString() },
					ErrorUtil.createErrorLocation(className, LayerType.EXPOSITION));

			// Se incluye excepci�n en el MessageFault a retornar
			error = adapterError.errorHandlerRest(exc, fmwErrors.getErrors(), mappingErrors.getErrors());

			loggerService.logApp(uUidTransaction, Strings.EMPTY, uUidTransaction,
					LoggerAppType.EXP_AUDIT_BODY_ERROR_HANDLER.toString(), error.toString(), className);

			headersOutMap = RestUtil.getHeaderOut(headerIn, MessageType.ERROR.value());

			error = createMessageFaultDTO(error);
			return new ResponseEntity<MessageFaultDTO>(error,
					HttpStatus.valueOf(Integer.parseInt(error.getExceptionProtocol().getCode())));
		} finally {
			// Se registra la informacion de la ejecucion del servicio.

			LoggerUtil.createExecutionLog(request.toString(), execRuntimeInfo,
					headerIn != null ? headerIn : new HeaderInType(), error != null ? error.getExceptionCode() : null,
					error != null ? error.toString() : null, new ExecutionInfoToLogging(serviceName, operationName,
							LayerType.EXPOSITION.value(), uUidTransaction),
					loggerService);

		}

	}

	private void processHeader() {
		// Se define el id de transaccion unico.
		uUidTransaction = UUID.randomUUID().toString();
		// header.execId=550e8400-e29b-41d4-a716-446655440001
	}

	public BasicOperationController<R, P> setCommonInfo(String serviceName, String operationName, String className) {
		this.className = className;
		this.serviceName = serviceName;
		this.operationName = operationName;
		return this;
	}

	public BasicOperationController<R, P> initializer(LoggerService loggerService, SmartValidator validator,
			HttpServletRequest httpServletRequest) {
		this.loggerService = loggerService;
		this.validator = validator;
		this.httpServletRequest = httpServletRequest;

		return this;
	}

	public BasicOperationController<R, P> errors(ServiceErrorAdapter adapterError, ErrorMappingProperties mappingErrors,
			ErrorTOpenApiProperties fmwErrors) {
		this.adapterError = adapterError;
		this.mappingErrors = mappingErrors;
		this.fmwErrors = fmwErrors;
		return this;
	}

	private MessageFaultDTO createMessageFaultDTO(MessageFaultDTO request) {
		MessageFaultDTO messageFaultDTO = request;

		messageFaultDTO.getAppDetail().setExceptionAppLocation(MsConstants.SERVICE);

		if(messageFaultDTO.getExceptionProtocol().getCode().equals(MsConstants.HTTP_CODE_BAD_REQUEST)){
			messageFaultDTO.getAppDetail().setExceptionAppMessage(MsConstants.INVALID_PARAMETER_BODY);
			messageFaultDTO.getAppDetail().setExceptionAppCause(MsConstants.INVALID_PARAMETER_BODY);
			return messageFaultDTO;
		}
		if(messageFaultDTO.getExceptionProtocol().getCode().equals(MsConstants.HTTP_CODE_INTERNAL_SERVER_ERROR) && messageFaultDTO.getAppDetail().getExceptionAppCause().contains(MsConstants.HEADER_ERROR)){
			messageFaultDTO.setExceptionMessage(MsConstants.INVALID_PARAMETER_BODY);
			messageFaultDTO.getExceptionProtocol().setCode(MsConstants.HTTP_CODE_BAD_REQUEST);
			messageFaultDTO.getExceptionProtocol().setDescription(MsConstants.BAD_REQUEST);
			messageFaultDTO.getAppDetail().setExceptionAppCode(MsConstants.CODE_ERROR_DATA);
			messageFaultDTO.getAppDetail().setExceptionAppMessage(MsConstants.INVALID_PARAMETER_BODY);
			messageFaultDTO.getAppDetail().setExceptionAppCause(MsConstants.INVALID_PARAMETER_BODY);
			messageFaultDTO.setExceptionDetail(MsConstants.INVALID_PARAMETER_BODY);
			return messageFaultDTO;
		}
		if(messageFaultDTO.getExceptionProtocol().getCode().equals(MsConstants.HTTP_CODE_INTERNAL_SERVER_ERROR) && !messageFaultDTO.getAppDetail().getExceptionAppCause().contains(MsConstants.HEADER_ERROR)){
			messageFaultDTO.setExceptionMessage(MsConstants.INTERNAL_SERVER_ERROR);
			messageFaultDTO.getExceptionProtocol().setCode(MsConstants.HTTP_CODE_INTERNAL_SERVER_ERROR);
			messageFaultDTO.getExceptionProtocol().setDescription(MsConstants.INTERNAL_SERVER_ERROR);
			messageFaultDTO.getAppDetail().setExceptionAppCode(MsConstants.HTTP_CODE_INTERNAL_SERVER_ERROR);
			messageFaultDTO.getAppDetail().setExceptionAppMessage(MsConstants.INTERNAL_SERVER_ERROR);
			messageFaultDTO.getAppDetail().setExceptionAppCause(MsConstants.INTERNAL_SERVER_ERROR);
			messageFaultDTO.setExceptionDetail(MsConstants.INTERNAL_SERVER_ERROR);
			return messageFaultDTO;
		}
		return messageFaultDTO;
	}
}

