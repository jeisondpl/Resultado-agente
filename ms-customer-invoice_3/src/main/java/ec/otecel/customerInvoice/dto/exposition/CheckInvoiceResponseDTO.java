package ec.otecel.customerInvoice.dto.exposition;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class CheckInvoiceResponseDTO {
    /**
     * Número de cuenta
     */
    private String accountNumber;

    /**
     * Lista de facturas
     */
    private List<CheckInvoiceDTO> bills;

    /**
     * Indica si se debe relanzar
     */
    private String retry;
}