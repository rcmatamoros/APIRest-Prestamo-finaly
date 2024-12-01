package hn.unah.proyecto.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para representar  direcciónes asociadas a un cliente.")
public class DireccionDTO {

    @Schema(description = "id único de la dirección", example = "1")
    private int idDireccion;

    @Schema(description = "País", example = "Honduras")
    private String pais;

    @Schema(description = "Departamento", example = "Francisco Morazán")
    private String departamento;

    @Schema(description = "Ciudad", example = "Tegucigalpa")
    private String ciudad;

    @Schema(description = "Colonia", example = "Colonia Kennedy")
    private String colonia;

    @Schema(description = "Referencia adicional", example = "Cerca del parque")
    private String referencia;

    @JsonIgnore
    @Schema(description = "Cliente asociado a esta dirección (usando JSONIgnore)")
    private ClienteDTO listaCliente;
}