package ec.otecel.customerInvoice.dto.exposition;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class CheckInvoiceRequestDTO {

    /**
     * Número de teléfono
     * Validación: solo dígitos, longitud 1-16
     */
    @NotNull(message = "El atributo phoneNumber es obligatorio")
    @Pattern(regexp = "^[0-9]{1,16}$", message = "El atributo phoneNumber debe contener solo números y tener una longitud entre 1 y 16 caracteres")
    private String phoneNumber;

    /**
     * Sistema fuente
     * Validación: solo letras y números, longitud 4-27
     */
    @NotNull(message = "El atributo sourceSystem es obligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,27}$", message = "El atributo sourceSystem debe contener solo letras y números y tener una longitud entre 4 y 27 caracteres")
    private String sourceSystem;

    /**
     * Fecha de transacción
     * Validación: formato YYYYMMDD
     */
    @NotNull(message = "El atributo transactionDate es obligatorio")
    @Pattern(regexp = "^(?:19|20)\\d{2}(?:(?:0[1-9]|1[0-2])(?:0[1-9]|[12][0-9]|3[01]))$", message = "El atributo transactionDate debe estar en el formato YYYYMMDD")
    private String transactionDate;

    /**
     * Delta en horas
     * Validación: solo dígitos, longitud 1-3
     */
    @NotNull(message = "El atributo delta es obligatorio")
    @Pattern(regexp = "^\\d{1,3}$", message = "El atributo delta debe contener solo números y tener una longitud entre 1 y 3 caracteres")
    private String delta;
}