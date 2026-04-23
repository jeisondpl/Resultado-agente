package ec.otecel.allmsisdn.dto.exposition;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class AllMsisdnRequestDTO {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]{1,3}$")
    private String identifierType;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{1,30}$")
    private String identifierNumber;

    @Pattern(regexp = "^[a-zA-Z]{1,15}$")
    private String level;

    @Pattern(regexp = "^[0-9]{1,5}$")
    private String operatorId;

    private String listCCPlan;
    private String filterPlan;
    private List<String> subscriptionType;

    @Pattern(regexp = "^[0-9]+$")
    private Integer timeToLive;
}
