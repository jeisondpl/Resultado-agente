package ec.otecel.customerretrievebalancebuckets.dto.exposition;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class ParameterDTO {
    /**
     * Clave del parámetro
     */
    @NotNull(message = "El atributo key es obligatorio")
    private String key;

    /**
     * Valor del parámetro
     */
    @NotNull(message = "El atributo value es obligatorio")
    private String value;

    /**
     * Descripción del parámetro
     */
    private String description;

    /**
     * Indica si el parámetro es público
     */
    private String isPublic;

    /**
     * Grupo al que pertenece el parámetro
     */
    private String group;
}