package hn.unah.proyecto.controladores;

import hn.unah.proyecto.dtos.Prestamos2DTO;
import hn.unah.proyecto.dtos.PrestamosDTO;
import hn.unah.proyecto.excepciones.ClienteNoEncontradoException;
import hn.unah.proyecto.excepciones.PrestamoNoEncontradoException;
import hn.unah.proyecto.servicios.PrestamoServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoServicio prestamoServicio;

    @Operation(summary= "Crearun nuevo prestamo", description="Crea un prestamo con los datos introducidos por el usuario.")
    @PostMapping("/crear/nuevo")
    public String crearPrestamo(@Parameter(description="Recibe un JSON con las propiedades")@RequestBody PrestamosDTO prestamo) {
        return prestamoServicio.crearPrestamo(prestamo);
    }
    @Operation(summary=" Buscar prestamos por dni",description="Retorna una lista de prestamos asociados a un cliente por su dni.")
    @GetMapping("/dni/{dni}")
    public List<Prestamos2DTO> buscarPrestamosPorDni(@Parameter (description="dni del cliente.") @PathVariable String dni) throws ClienteNoEncontradoException {
        return prestamoServicio.buscarPrestamosPorDni(dni);
    }

    @Operation(summary = "Buscar préstamo por id", description = "Obtiene los detalles de un préstamo por su id.")
    @GetMapping("/{id}")
    public Optional<PrestamosDTO> buscarPrestamoPorId(@Parameter(description = "id del préstamo") @PathVariable int id) throws PrestamoNoEncontradoException {
        return prestamoServicio.buscarPrestamoPorId(id);
    }

    @Operation(summary = "Asociar préstamo a cliente", description = "Asocia un préstamo a un cliente.")
    @PutMapping("/{idPrestamo}/{dniCliente}")
    public void asociarPrestamoACliente(@Parameter( description = "id del préstamo" ) @PathVariable int idPrestamo, 
                                       @Parameter( description = "dni del cliente" ) @PathVariable String dniCliente) {
        prestamoServicio.asociarPrestamoACliente(idPrestamo, dniCliente);
    }

    @Operation(summary = "Obtener saldo pendiente", description = "Consulta y retorna el saldo pendiente de un préstamo.")
    @GetMapping("/saldo/{idPrestamo}")
    public double obtenerSaldoPendiente(@Parameter(description = "id del préstamo") @PathVariable int idPrestamo) {
        return prestamoServicio.obtenerSaldoPendiente(idPrestamo);
    }

    @Operation(summary = "Pagar cuota", description = "Realiza el pago de una cuota de un préstamo.")
    @PutMapping("/pagar/{idPrestamo}")
    public void pagarCuota(@Parameter(description = "id del préstamo") @PathVariable int idPrestamo) {
        prestamoServicio.pagarCuota(idPrestamo);
    }
}