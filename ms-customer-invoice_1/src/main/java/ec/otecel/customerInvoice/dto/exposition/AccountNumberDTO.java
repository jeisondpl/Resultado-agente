package ec.otecel.customerInvoice.dto.exposition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Builder
public class AccountNumberDTO {
    private String ban;
    private String msisdn;
    private String dateStart;
    private String dateEnd;
    private String cai;
    private String bai;
    private String bpiLine;
    private String typeSubscription;
    private String planId;
    private String planCC;
    private String planName;
    private String status;
}