package ec.otecel.allmsisdn.adapter;

import ec.otecel.allmsisdn.constants.MsConstants;
import ec.otecel.allmsisdn.dto.exposition.AllMsisdnRequestDTO;
import ec.otecel.allmsisdn.dto.exposition.AllMsisdnResponseDTO;
import ec.otecel.allmsisdn.util.BasicOperationAdapter;
import ec.otecel.common.model.commontypes.ErrorCodeType;
import ec.otecel.common.model.globalintegration.header.HeaderInType;
import ec.otecel.component.error.exception.ComponentException;
import ec.otecel.component.logs.config.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NetCrackerRDBAdapter {

    @Autowired
    private LoggerService loggerService;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${otecel.api.rdb.host:backendpre.movistar.com.ec}")
    private String apiHost;

    @Value("${otecel.api.rdb.port:32101}")
    private int apiPort;

    @Value("${otecel.api.rdb.path.all.msisdn:/netcrackerrdb/allMsisdn}")
    private String apiPath;

    @Value("${otecel.api.rdb.https:true}")
    private boolean useHttps;

    @Value("${otecel.api.token:Basic YWRtaW46c1kzNU1Yei56dVg=}")
    private String authToken;

    public AllMsisdnResponseDTO allMsisdn(HeaderInType headerIn, AllMsisdnRequestDTO request)
            throws ComponentException {
        return new BasicOperationAdapter<AllMsisdnResponseDTO, AllMsisdnRequestDTO>(headerIn,
                new AllMsisdnResponseDTO(), request) {
            @Override
            public AllMsisdnResponseDTO process(HeaderInType h, AllMsisdnRequestDTO r)
                    throws ComponentException {
                try {
                    String scheme = useHttps ? "https" : "http";
                    String url = scheme + "://" + apiHost + ":" + apiPort + apiPath;

                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.set("Authorization", authToken);
                    if (h != null && h.getExecId() != null) {
                        httpHeaders.set("execId", h.getExecId());
                    }

                    HttpEntity<AllMsisdnRequestDTO> entity = new HttpEntity<>(r, httpHeaders);
                    ResponseEntity<AllMsisdnResponseDTO> resp = restTemplate.exchange(
                            url, HttpMethod.POST, entity, AllMsisdnResponseDTO.class);

                    if (resp.getBody() == null) {
                        throw new ComponentException(ErrorCodeType.ERROR_INESPERADO,
                                "Empty response from NetCrackerRDB", true,
                                new String[]{}, getClass().getSimpleName());
                    }
                    return resp.getBody();
                } catch (ComponentException ce) {
                    throw ce;
                } catch (Exception e) {
                    throw new ComponentException(e.getMessage(), e,
                            ErrorCodeType.ERROR_INESPERADO, true,
                            new String[]{}, getClass().getSimpleName());
                }
            }
        }.initializer(loggerService)
         .setCommonInfo(MsConstants.SERVICE, MsConstants.METHOD1, getClass().getSimpleName())
         .run();
    }
}
