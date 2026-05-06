package ec.otecel.customerInvoice.dto.exposition;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class CheckInvoiceDTO {

    /**
     * Secuencia de la factura
     * Validación: solo letras y números, longitud entre 1 y 30
     */
    @NotNull(message = "El atributo billSequence es obligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,30}$", message = "El atributo billSequence debe contener solo letras y números, longitud entre 1 y 30 caracteres")
    private String billSequence;

    /**
     * Número de factura
     * Validación: solo letras y números, longitud entre 1 y 30
     */
    @NotNull(message = "El atributo invoiceNumber es obligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,30}$", message = "El atributo invoiceNumber debe contener solo letras y números, longitud entre 1 y 30 caracteres")
    private String invoiceNumber;

    /**
     * Estado del documento
     * Validación: solo letras y números, longitud entre 1 y 30
     */
    @NotNull(message = "El atributo documentStatus es obligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,30}$", message = "El atributo documentStatus debe contener solo letras y números, longitud entre 1 y 30 caracteres")
    private String documentStatus;
}