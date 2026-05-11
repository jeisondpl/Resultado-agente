package ec.otecel.customerretrievebalancebuckets.service;

import ec.otecel.customerretrievebalancebuckets.adapter.PostgressDBAdapter;
import ec.otecel.customerretrievebalancebuckets.constants.MsConstants;
import ec.otecel.customerretrievebalancebuckets.dto.exposition.GetretrievebalancebucketsRequestDTO;
import ec.otecel.customerretrievebalancebuckets.dto.exposition.GetretrievebalancebucketsResponseDTO;
import ec.otecel.customerretrievebalancebuckets.util.BasicOperationService;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.logs.config.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.otecel.customerretrievebalancebuckets.dto.integration.ParameterRequestDTO;
import ec.otecel.customerretrievebalancebuckets.mapper.GetParameterDBRequestDTOMapper;
import ec.otecel.customerretrievebalancebuckets.dto.integration.ParameterResponseDTO;
import ec.otecel.customerretrievebalancebuckets.mapper.GetParametersResponseDTOMapper;

@Service
public class customerRetrieveBalanceBucketsService implements IcustomerRetrieveBalanceBucketsService {

    @Autowired
    private LoggerService loggerService;

    @Autowired
    private PostgressDBAdapter postgressDBAdapter;

    @Override
    public GetretrievebalancebucketsResponseDTO getretrievebalancebuckets(HeaderInType headerIn, GetretrievebalancebucketsRequestDTO request) throws ComponentException {
        return new BasicOperationService<GetretrievebalancebucketsResponseDTO, GetretrievebalancebucketsRequestDTO>(headerIn, new GetretrievebalancebucketsResponseDTO(), request) {
            @Override
            public GetretrievebalancebucketsResponseDTO process(HeaderInType headerIn, GetretrievebalancebucketsRequestDTO request) throws ComponentException {
                // 1. Mapeo del request a DTO de base de datos
                ParameterRequestDTO requestDB = new GetParameterDBRequestDTOMapper().getObject(request);
                // 2. Llamada al adaptador para obtener los parámetros
                ParameterResponseDTO responseDB = postgressDBAdapter.getParameters(headerIn, requestDB);
                // 3. Mapeo de la respuesta a DTO de salida
                return new GetParametersResponseDTOMapper().getObject(responseDB);
            }
        }.initializer(loggerService)
         .setCommonInfo(MsConstants.SERVICE, MsConstants.METHOD_NAME_GETRETRIEVEBALANCEBUCKETS, getClass().getSimpleName())
         .run();
    }

    private String createKey(String originator, GetretrievebalancebucketsRequestDTO req) {
        // lógica para crear clave
        return originator + "_" + req.getIdentifierType() + "_" + req.getIdentifierNumber();
    }
}