package ec.otecel.allmsisdn.dto.exposition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class InfoMsisdnResponseDTO {
    private String bai; // Código relacionado a la línea
    private String ban; // Identificador BAN
    private String msisdn; // Número MSISDN
    private String bpiLine; // Código BPI
    private String typeSubscription; // Tipo de suscripción
    private String planId; // ID del plan
    private String planCc; // Código del plan
    private String planName; // Nombre del plan
    private String status; // Estado de la línea
    private String fecStart; // Fecha de inicio
    private String can; // Código CAN
    private String luser; // Usuario asociado
}