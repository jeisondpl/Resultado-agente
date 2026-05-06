package ec.otecel.customerretrievebalancebuckets.dto.exposition;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class GetretrievebalancebucketsRequestDTO {

    /**
     * Tipo de identificador
     * Validación: solo letras, 1-3 caracteres
     */
    @NotNull(message = "El atributo identifierType es obligatorio")
    @Pattern(regexp = "^[a-zA-Z]{1,3}$", message = "El atributo identifierType debe contener solo letras y tener longitud entre 1 y 3 caracteres")
    private String identifierType;

    /**
     * Número de identificador
     * Validación: solo letras y números, longitud entre 1 y 30 caracteres
     */
    @NotNull(message = "El atributo identifierNumber es obligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,30}$", message = "El atributo identifierNumber debe contener solo letras y números, longitud entre 1 y 30 caracteres")
    private String identifierNumber;

    /**
     * Tiempo de vida del cache
     * Validación: solo dígitos, longitud entre 1 y 5
     */
    @Pattern(regexp = "^[0-9]{1,5}$", message = "El atributo timeToLive debe contener solo números, longitud entre 1 y 5")
    private String timeToLive;
}