package ec.otecel.customerInvoice.dto.exposition;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class GetAccountNumberRequestDTO {

    /**
     * Número de línea
     */
    @NotNull(message = "El atributo msisdn es obligatorio")
    private String msisdn;

    /**
     * Fecha de transacción
     */
    @NotNull(message = "El atributo transactionDate es obligatorio")
    private String transactionDate;

    /**
     * Delta en horas
     */
    @NotNull(message = "El atributo delta es obligatorio")
    private Integer delta;
}