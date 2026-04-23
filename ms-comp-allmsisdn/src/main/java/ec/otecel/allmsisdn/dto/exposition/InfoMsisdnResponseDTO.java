package ec.otecel.allmsisdn.dto.exposition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class InfoMsisdnResponseDTO {
    private String bai;
    private String ban;
    private String msisdn;
    private String bpiLine;
    private String typeSubscription;
    private String planId;
    private String planCc;
    private String planName;
    private String status;
    private String fecStart;
    private String can;
    private String luser;
}
