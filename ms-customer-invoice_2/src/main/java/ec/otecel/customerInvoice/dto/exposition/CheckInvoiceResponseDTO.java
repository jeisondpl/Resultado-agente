package ec.otecel.customerInvoice.dto.exposition;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class CheckInvoiceResponseDTO {
    private String accountNumber;
    private List<CheckInvoiceDTO> bills;
    private String retry;
}