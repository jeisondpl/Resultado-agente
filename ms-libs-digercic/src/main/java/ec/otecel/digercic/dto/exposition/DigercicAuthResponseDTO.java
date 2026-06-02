package ec.otecel.digercic.dto.exposition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class DigercicAuthResponseDTO {
    /**
     * Bearer Token
     */
    private String token;
}