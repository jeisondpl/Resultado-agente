package ec.otecel.customerInvoice.dto.exposition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Builder
public class GetInvoiceResponseDTO {
    private String code;
    private String message;
    private InvoiceDataDTO data;
}
