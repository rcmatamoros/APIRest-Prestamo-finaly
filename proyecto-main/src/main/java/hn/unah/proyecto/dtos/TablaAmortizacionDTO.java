package hn.unah.proyecto.dtos;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para representar los detalles de una tabla de amortización.")
public class TablaAmortizacionDTO {

    @Schema(description = "Id único de la tabla de amortización", example = "1")
    private int id;

    @Schema(description = "Número de la cuota en la tabla de amortización", example = "1")
    private int numeroCuota;

    @Schema(description = "Interés correspondiente a la cuota", example = "50.25")
    private double interes;

    @Schema(description = "Capital pagado en la cuota", example = "200.75")
    private double capital;

    @Schema(description = "Saldo restante después del pago de la cuota", example = "800.00")
    private double saldo;

    @Schema(description = "Estado de la cuota ('P' para pendiente, 'A' para apropado)", example = "P")
    private char estado;

    @Schema(description = "Fecha de vencimiento de la cuota", example = "2024-12-25")
    private LocalDate fechaVencimiento;

    @Schema(description = "Préstamo asociado a esta tabla de amortización")
    private PrestamosDTO prestamo;
}
