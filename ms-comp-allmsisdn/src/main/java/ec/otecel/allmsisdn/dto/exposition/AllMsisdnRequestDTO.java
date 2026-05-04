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
     * Obligatorio. Máx 15 caracteres. Solo letras.
     */
    @NotNull(message = "El atributo identifierType es obligatorio")
    @Size(max = 15, message = "El atributo identifierType debe tener una longitud máxima de 15 caracteres")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "El atributo identifierType debe contener solo letras")
    private String identifierType;

    /**
     * Número identificador
     * Obligatorio. Máx 50 caracteres. Solo alfanumérico.
     */
    @NotNull(message = "El atributo identifierNumber es obligatorio")
    @Size(max = 50, message = "El atributo identifierNumber debe tener una longitud máxima de 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "El atributo identifierNumber debe contener solo letras y números")
    private String identifierNumber;

    /**
     * Nivel
     * Obligatorio. Máx 15 caracteres. Solo letras.
     */
    @NotNull(message = "El atributo level es obligatorio")
    @Size(max = 15, message = "El atributo level debe tener una longitud máxima de 15 caracteres")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "El atributo level debe contener solo letras")
    private String level;

    /**
     * ID del operador
     * Obligatorio. Máx 50 caracteres. Solo números.
     */
    @NotNull(message = "El atributo operatorId es obligatorio")
    @Size(max = 50, message = "El atributo operatorId debe tener una longitud máxima de 50 caracteres")
    @Pattern(regexp = "^[0-9]+$", message = "El atributo operatorId debe contener solo números")
    private String operatorId;

    /**
     * Plan CC
     * Opcional. Máx 200 caracteres. Letras, números y comas.
     */
    @Size(max = 200, message = "El atributo listCCPlan debe tener una longitud máxima de 200 caracteres")
    private String listCCPlan;

    /**
     * Filtro del plan
     * Obligatorio. Máx 10 caracteres. Solo mayúsculas.
     */
    @NotNull(message = "El atributo filterPlan es obligatorio")
    @Size(max = 10, message = "El atributo filterPlan debe tener una longitud máxima de 10 caracteres")
    @Pattern(regexp = "^[A-Z]+$", message = "El atributo filterPlan debe contener solo mayúsculas")
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
    @Size(max = 5, message = "El atributo timeToLive debe tener una longitud máxima de 5 caracteres")
    @Pattern(regexp = "^[0-9]*$", message = "El atributo timeToLive debe contener solo números")
    private String timeToLive;
}