package ec.otecel.digercic.service;

import ec.otecel.digercic.adapter.DigercicAuthAdapter;
import ec.otecel.digercic.adapter.DigercicDacAdapter;
import ec.otecel.digercic.constants.MsConstants;
import ec.otecel.digercic.dto.exposition.DigercicAuthRequestDTO;
import ec.otecel.digercic.dto.exposition.DigercicAuthResponseDTO;
import ec.otecel.digercic.dto.exposition.DigercicDacRequestDTO;
import ec.otecel.digercic.dto.exposition.DigercicDacResponseDTO;
import ec.otecel.digercic.util.BasicOperationService;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.logs.config.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DigercicService implements IDigercicService {

    @Autowired
    private DigercicAuthAdapter authAdapter;

    @Autowired
    private DigercicDacAdapter dacAdapter;

    @Autowired
    private LoggerService loggerService;

    @Override
    public DigercicAuthResponseDTO getToken(HeaderInType headerIn, DigercicAuthRequestDTO request) throws ComponentException {
        return new BasicOperationService<DigercicAuthResponseDTO, DigercicAuthRequestDTO>(headerIn, new DigercicAuthResponseDTO(), request) {
            @Override
            public DigercicAuthResponseDTO process(HeaderInType headerIn, DigercicAuthRequestDTO request) throws ComponentException {
                // finalStep = TOTAL de integraciones de este flujo (1: authAdapter.getToken)
                final String finalStep = "1";
                return authAdapter.getToken(headerIn, request, "1", finalStep);
            }
        }.initializer(loggerService)
         .setCommonInfo(MsConstants.SERVICE, MsConstants.METHOD_NAME_GET_TOKEN, getClass().getSimpleName())
         .run();
    }

    @Override
    public DigercicDacResponseDTO queryByDac(HeaderInType headerIn, DigercicDacRequestDTO request) throws ComponentException {
        return new BasicOperationService<DigercicDacResponseDTO, DigercicDacRequestDTO>(headerIn, new DigercicDacResponseDTO(), request) {
            @Override
            public DigercicDacResponseDTO process(HeaderInType headerIn, DigercicDacRequestDTO request) throws ComponentException {
                // finalStep = TOTAL de integraciones de este flujo (1: dacAdapter.queryByDac)
                final String finalStep = "1";
                return dacAdapter.queryByDac(headerIn, request, "1", finalStep);
            }
        }.initializer(loggerService)
         .setCommonInfo(MsConstants.SERVICE, MsConstants.METHOD_NAME_QUERY_BY_DAC, getClass().getSimpleName())
         .run();
    }
}