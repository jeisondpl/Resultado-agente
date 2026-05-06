package ec.otecel.allmsisdn.dto.integration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class RecoverRedisResponseDTO {
    /** Código de respuesta del cache: "0000" = HIT, "0001" = MISS, otros = ERROR. */
    private String code;
    /** Payload serializado en JSON, presente solo si code == "0000". */
    private String json;
}