package ec.otecel.allmsisdn.dto.integration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class SaveRedisResponseDTO {
    /** Código del resultado */
    private String code;
    /** Mensaje del resultado */
    private String message;
}