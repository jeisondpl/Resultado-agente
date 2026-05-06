package ec.otecel.customerInvoice.dto.exposition;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Builder
public class GetInvoiceRequestDTO {

    @NotNull(message = "El atributo accountNumber es obligatorio")
    private String accountNumber;
}
