package ec.otecel.allmsisdn.dto.exposition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class AllMsisdnResponseDTO {
    private String identifierType;
    private String identifierNumber;
    private String customerName;
    private String customerAccountId;
    private String blackList;
    private String customerType;
    private String fullNameRep;
    private String emailRep;
    private String msisdnRep;
    private String documentType;
    private String documentNumber;
    private String customerPlan;
    private List<InfoMsisdnResponseDTO> infoMsisdn;
    private String responseCode;
    private String responseMessage;
}
