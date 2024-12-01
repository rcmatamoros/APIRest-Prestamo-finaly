package hn.unah.proyecto.dtos;

import java.util.List;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para representar un cliente completo con préstamos.")
public class ClienteDTO {

    @Schema(description = "dni del cliente", example = "0704")
    private String dni;

    @Schema(description = "Nombre del cliente", example = "Pedro")
    private String nombre;

    @Schema(description = "Apellido del cliente", example = "Picapiedra")
    private String apellido;

    @Schema(description = "Teléfono del cliente", example = "22222222")
    private String telefono;

    @Schema(description = "Correo electrónico del cliente", example = "pedro.picapiedra@example.com")
    private String correo;

    @Schema(description = "Sueldo del cliente en dólares", example = "5000.50")
    private double sueldo;

    @ArraySchema(schema = @Schema(description = "Lista de direcciones asociadas al cliente"))
    private List<DireccionDTO> listaDireccion;

    @ArraySchema(schema = @Schema(description = "Lista de préstamos asociados al cliente"))
    private List<Prestamos2DTO> listaPrestamos;
}
