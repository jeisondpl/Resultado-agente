package ec.otecel.digercic.dto.exposition;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class DigercicAuthRequestDTO {

    /**
     * Número Único de Identificación
     * Validación: solo letras y números, longitud entre 1 y 30
     */
    @NotNull(message = "El atributo nui es obligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,30}$", message = "El atributo nui debe contener solo letras y números, longitud entre 1 y 30 caracteres")
    private String nui;

    /**
     * Código dactilar
     * Validación: solo letras y números, longitud 1-6
     */
    @NotNull(message = "El atributo dactilarCode es obligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,6}$", message = "El atributo dactilarCode debe contener solo letras y números, longitud entre 1 y 6 caracteres")
    private String dactilarCode;
}