package ec.otecel.customerInvoice.dto.exposition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class GetAccountNumberResponseDTO {
    private String code;
    private String message;
    private AccountNumberDTO data;
}