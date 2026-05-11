package ec.otecel.customerInvoice.dto.exposition;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class GetAccountNumberRequestDTO {

    /**
     * Número de teléfono
     * Validación: solo dígitos, longitud 1-16
     */
    @NotNull(message = "El atributo msisdn es obligatorio")
    @Pattern(regexp = "^\\d{1,16}$", message = "El atributo msisdn debe contener solo números y tener una longitud entre 1 y 16 caracteres")
    private String msisdn;

    /**
     * Fecha de transacción
     * Validación: formato YYYYMMDD
     */
    @NotNull(message = "El atributo transactionDate es obligatorio")
    @Pattern(regexp = "^(?:19|20)\\d{2}(?:(?:0[1-9]|1[0-2])(?:0[1-9]|[12][0-9]|3[01]))$", message = "El atributo transactionDate debe estar en el formato YYYYMMDD")
    private String transactionDate;

    /**
     * Delta en horas
     */
    private Integer delta;
}