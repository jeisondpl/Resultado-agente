package ec.otecel.customerretrievebalancebuckets.dto.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class ParameterDBDTO {
    private String key;
    private String value;
    private String description;
    private String isPublic;
    private String group;
}