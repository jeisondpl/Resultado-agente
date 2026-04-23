package ec.otecel.allmsisdn.adapter;

import ec.otecel.allmsisdn.constants.MsConstants;
import ec.otecel.allmsisdn.dto.exposition.AllMsisdnRequestDTO;
import ec.otecel.allmsisdn.dto.exposition.AllMsisdnResponseDTO;
import ec.otecel.allmsisdn.util.BasicOperationAdapter;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.logs.config.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NetCrackerRDBAdapter {

    @Autowired
    private LoggerService loggerService;

    @Value("${otecel.api.rdb.host:localhost}")
    private String apiHost;

    @Value("${otecel.api.rdb.port:32101}")
    private int apiPort;

    @Value("${otecel.api.rdb.path.all.msisdn:/netcrackerrdb/allMsisdn}")
    private String apiPath;

    public AllMsisdnResponseDTO allMsisdn(HeaderInType headerIn, AllMsisdnRequestDTO request)
            throws ComponentException {
        // Lógica de implementación para llamar al servicio de NetCracker
        return new AllMsisdnResponseDTO();
    }
}
