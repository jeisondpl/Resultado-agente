package ec.otecel.digercic.dto.exposition;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class DigercicDacRequestDTO {

    /**
     * Número Único de Identificación
     */
    @NotNull(message = "El atributo nui es obligatorio")
    @Pattern(regexp = "^[0-9]{1,20}$", message = "El atributo nui debe contener solo números y tener una longitud entre 1 y 20 caracteres")
    private String nui;

    /**
     * Código dactilar
     */
    @NotNull(message = "El atributo dactilarCode es obligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9]{6}$", message = "El atributo dactilarCode debe contener exactamente 6 caracteres alfanuméricos")
    private String dactilarCode;
}