package hn.unah.proyecto.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para representar un préstamo básico.")
public class Prestamos2DTO {

    @Schema(description = "Id único del préstamo", example = "1")
    private int idPrestamo;

    @Schema(description = "Monto del préstamo", example = "10000.00")
    private double monto;

    @Schema(description = "Plazo del préstamo en meses", example = "12")
    private int plazo;

    @Schema(description = "Tasa de interés anual (%)", example = "5.5")
    private double tasaInteres;

    @Schema(description = "Cuota mensual del préstamo", example = "860.50")
    private double cuota;

    @Schema(description = "Estado del préstamo ('A' para aprobado, 'P' para pendiente)", example = "A")
    private char estado;

    @Schema(description = "Tipo de préstamo ('P' personal, 'H' hipotecario,'V' vehicular .)", example = "P")
    private char tipoPrestamo;
}