
package ec.otecel.customerretrievebalancebuckets.controller;

import java.util.LinkedHashMap;
import java.util.Locale;
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
import ec.otecel.customerretrievebalancebuckets.constants.MsConstants;
import ec.otecel.customerretrievebalancebuckets.util.BasicOperationController;
import ec.otecel.customerretrievebalancebuckets.util.ErrorMappingProperties;
import ec.otecel.customerretrievebalancebuckets.util.ErrorTOpenApiProperties;
import ec.otecel.customerretrievebalancebuckets.service.IcustomerRetrieveBalanceBucketsService;
import ec.otecel.customerretrievebalancebuckets.dto.exposition.GetretrievebalancebucketsRequestDTO;
import ec.otecel.customerretrievebalancebuckets.dto.exposition.GetretrievebalancebucketsResponseDTO;



/**
 * Class controladora que expone los metodos correspondientes al servicio xxxx
 * 
 * @author nombre apellido correo@indracompany.com
 */
@RestController
@RequestMapping(value = "${controller.properties.base-path}")
public class customerRetrieveBalanceBucketsController{

	/**
	 * Componente para realizar la redireccion hacia las operaciones del back.
	 */
	private IcustomerRetrieveBalanceBucketsService service;

	private LoggerService loggerService;

	private SmartValidator validator;

	private HttpServletRequest httpServletRequest;

	private ServiceErrorAdapter adapterError;

	/**
	 * Errores fmw
	 */

	private ErrorTOpenApiProperties fmwErrors;

	/**
	 * Mapeo de errores legado contra fmw
	 */

	private ErrorMappingProperties mappingErrors;

	/**
	 * Constructor controlador inyector de dependecias
	 * 
	 * @param service
	 */
	@Autowired 
	public customerRetrieveBalanceBucketsController(IcustomerRetrieveBalanceBucketsService service, LoggerService loggerService,
	SmartValidator validator, HttpServletRequest httpServletRequest,
	ServiceErrorAdapter adapterError, ErrorTOpenApiProperties fmwErrors,
	ErrorMappingProperties mappingErrors)
	{
		this.service = service;
		this.loggerService = loggerService;
		this.validator = validator;
		this.httpServletRequest = httpServletRequest;
		this.adapterError = adapterError;
		this.fmwErrors = fmwErrors;
		this.mappingErrors = mappingErrors;

	}

	// TODO: AGREGAR DESCRIPCION DEL METODO
	/**@Metodo que permite XXXXX
	 * @param headerReq
	 * @param customerRetrieveBalanceBucketsAddRequestDTO request
	 * @return customerRetrieveBalanceBucketsAddResponseDTO
	 * @throws ComponentException
	 * @author "cambiar autor" 
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping(path = "getretrievebalancebuckets", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getretrievebalancebuckets(@RequestHeader Map<String, String> headerReq,
			@RequestBody GetretrievebalancebucketsRequestDTO request)throws ComponentException
	{
            headerReq = lowerCaseHeaderKeys(headerReq);
			return new BasicOperationController<GetretrievebalancebucketsResponseDTO, GetretrievebalancebucketsRequestDTO>(headerReq,
					new GetretrievebalancebucketsResponseDTO(), request) {
			  @Override
		 	  public GetretrievebalancebucketsResponseDTO process(HeaderInType headers, GetretrievebalancebucketsRequestDTO request)throws ComponentException
		    	{
					return service.getretrievebalancebuckets(headers, request);
			    }

			}.errors(adapterError,mappingErrors,fmwErrors).initializer(loggerService,validator,httpServletRequest).setCommonInfo(MsConstants.SERVICE,MsConstants.METHOD1,this.getClass().getSimpleName()).run();

	}

    private static Map<String, String> lowerCaseHeaderKeys(Map<String, String> headers) {
        if (headers == null) {
            return null;
        }
        Map<String, String> normalized = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            if (key == null) {
                continue;
            }
            normalized.put(key.toLowerCase(Locale.ROOT), entry.getValue());
        }
        return normalized;
    }
}
