package ec.otecel.customerretrievebalancebuckets.dto.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;
import ec.otecel.customerretrievebalancebuckets.dto.integration.ParameterDBDTO;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class ParameterResponseDTO {
    private List<ParameterDBDTO> parameters;
}