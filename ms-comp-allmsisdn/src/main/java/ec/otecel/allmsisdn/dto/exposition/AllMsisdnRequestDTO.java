package ec.otecel.allmsisdn.dto.exposition;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class AllMsisdnRequestDTO {
    /**
     * Tipo de identificador
     * Obligatorio. Máx 15 caracteres. Solo letras.
     */
    @NotNull(message = "El atributo identifierType es obligatorio")
    @Pattern(regexp = "^[a-zA-Z]{1,15}$", message = "El atributo identifierType debe contener solo letras y tener longitud entre 1 y 15 caracteres")
    private String identifierType;

    /**
     * Número identificador
     * Obligatorio. Máx 50 caracteres. Solo alfanumérico.
     */
    @NotNull(message = "El atributo identifierNumber es obligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,50}$", message = "El atributo identifierNumber debe contener solo letras y números, longitud entre 1 y 50 caracteres")
    private String identifierNumber;

    /**
     * Nivel
     * Obligatorio. Máx 15 caracteres. Solo letras.
     */
    @NotNull(message = "El atributo level es obligatorio")
    @Pattern(regexp = "^[a-zA-Z]{1,15}$", message = "El atributo level debe contener solo letras y tener longitud entre 1 y 15 caracteres")
    private String level;

    /**
     * ID del operador
     * Obligatorio. Máx 50 caracteres. Solo números.
     */
    @NotNull(message = "El atributo operatorId es obligatorio")
    @Pattern(regexp = "^[0-9]{1,50}$", message = "El atributo operatorId debe contener solo números y tener longitud entre 1 y 50 caracteres")
    private String operatorId;

    /**
     * Plan CC
     * Opcional. Máx 200 caracteres. Letras, números y comas.
     */
    private String listCCPlan;

    /**
     * Filtro del plan
     * Obligatorio. Máx 10 caracteres. Solo mayúsculas.
     */
    @NotNull(message = "El atributo filterPlan es obligatorio")
    @Pattern(regexp = "^[A-Z]{1,10}$", message = "El atributo filterPlan debe contener solo mayúsculas y tener longitud entre 1 y 10 caracteres")
    private String filterPlan;

    /**
     * Tipo de suscripción
     * Opcional. Lista de textos.
     */
    private List<String> subscriptionType;

    /**
     * Tiempo de vida
     * Opcional. Máx 5 caracteres. Solo números.
     */
    private String timeToLive;
}