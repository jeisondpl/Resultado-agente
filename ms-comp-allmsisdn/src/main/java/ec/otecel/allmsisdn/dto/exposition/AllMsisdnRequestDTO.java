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
     * Validación: solo letras, 1-15 caracteres
     */
    @NotNull(message = "El atributo identifierType es obligatorio")
    @Pattern(regexp = "^[a-zA-Z]{1,15}$",
             message = "El atributo identifierType debe contener solo letras (mayúsculas o minúsculas) y tener una longitud entre 1 y 15 caracteres")
    private String identifierType;

    /**
     * Número identificador
     * Validación: solo alfanumérico, 1-50 caracteres
     */
    @NotNull(message = "El atributo identifierNumber es obligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,50}$",
             message = "El atributo identifierNumber debe contener solo letras y números, longitud entre 1 y 50 caracteres")
    private String identifierNumber;

    /**
     * Nivel
     * Validación: solo letras, 1-15 caracteres
     */
    @NotNull(message = "El atributo level es obligatorio")
    @Pattern(regexp = "^[a-zA-Z]{1,15}$",
             message = "El atributo level debe contener solo letras (mayúsculas o minúsculas) y tener una longitud entre 1 y 15 caracteres")
    private String level;

    /**
     * ID del operador
     * Validación: solo números, 1-50 caracteres
     */
    @NotNull(message = "El atributo operatorId es obligatorio")
    @Pattern(regexp = "^[0-9]{1,50}$",
             message = "El atributo operatorId debe contener solo números y tener una longitud entre 1 y 50 caracteres")
    private String operatorId;

    /**
     * Lista de CC Plan
     * Validación: opcional, 0-200 caracteres
     */
    private String listCCPlan;

    /**
     * Filtro del plan
     * Validación: solo mayúsculas, 1-10 caracteres
     */
    @NotNull(message = "El atributo filterPlan es obligatorio")
    @Pattern(regexp = "^[A-Z]{1,10}$",
             message = "El atributo filterPlan debe contener solo letras mayúsculas y tener una longitud entre 1 y 10 caracteres")
    private String filterPlan;

    /**
     * Tipo de suscripción
     * Validación: opcional, lista de textos
     */
    private List<String> subscriptionType;

    /**
     * Tiempo de vida
     * Validación: opcional, solo números, 1-5 caracteres
     */
    private String timeToLive;
}