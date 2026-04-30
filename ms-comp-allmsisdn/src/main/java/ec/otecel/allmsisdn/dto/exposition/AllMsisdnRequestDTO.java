package ec.otecel.allmsisdn.dto.exposition;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(max = 15, message = "El atributo identifierType debe tener una longitud máxima de 15 caracteres")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "El atributo identifierType debe contener solo letras")
    private String identifierType;

    /**
     * Número identificador
     * Validación: alfanumérico, 1-50 caracteres
     */
    @NotNull(message = "El atributo identifierNumber es obligatorio")
    @Size(max = 50, message = "El atributo identifierNumber debe tener una longitud máxima de 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "El atributo identifierNumber debe contener solo letras y números")
    private String identifierNumber;

    /**
     * Nivel
     * Validación: solo letras, 1-15 caracteres
     */
    @NotNull(message = "El atributo level es obligatorio")
    @Size(max = 15, message = "El atributo level debe tener una longitud máxima de 15 caracteres")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "El atributo level debe contener solo letras")
    private String level;

    /**
     * ID del operador
     * Validación: solo números, 1-50 caracteres
     */
    @NotNull(message = "El atributo operatorId es obligatorio")
    @Size(max = 50, message = "El atributo operatorId debe tener una longitud máxima de 50 caracteres")
    @Pattern(regexp = "^[0-9]+$", message = "El atributo operatorId debe contener solo números")
    private String operatorId;

    /**
     * Plan CC
     * Validación: opcional, 1-200 caracteres
     */
    @Size(max = 200, message = "El atributo listCCPlan debe tener una longitud máxima de 200 caracteres")
    private String listCCPlan;

    /**
     * Filtro de plan
     * Validación: solo mayúsculas, 1-10 caracteres
     */
    @NotNull(message = "El atributo filterPlan es obligatorio")
    @Size(max = 10, message = "El atributo filterPlan debe tener una longitud máxima de 10 caracteres")
    @Pattern(regexp = "^[A-Z]+$", message = "El atributo filterPlan debe contener solo letras mayúsculas")
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
    @Size(max = 5, message = "El atributo timeToLive debe tener una longitud máxima de 5 caracteres")
    @Pattern(regexp = "^[0-9]+$", message = "El atributo timeToLive debe contener solo números")
    private String timeToLive;
}