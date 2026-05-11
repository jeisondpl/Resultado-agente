package ec.otecel.customerInvoice.dto.exposition;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class CheckInvoiceDTO {

    /**
     * Secuencia de la factura
     */
    @NotNull(message = "El atributo billSequence es obligatorio")
    private String billSequence;

    /**
     * Número de factura
     */
    @NotNull(message = "El atributo invoiceNumber es obligatorio")
    private String invoiceNumber;

    /**
     * Estado del documento
     */
    @NotNull(message = "El atributo documentStatus es obligatorio")
    private String documentStatus;
}