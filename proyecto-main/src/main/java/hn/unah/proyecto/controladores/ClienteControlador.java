package hn.unah.proyecto.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hn.unah.proyecto.dtos.ClienteDTO;
import hn.unah.proyecto.excepciones.ClienteNoEncontradoException;
import hn.unah.proyecto.servicios.ClienteServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;



@RestController
@RequestMapping("api/clientes")
public class ClienteControlador {

      @Autowired
    private ClienteServicio clienteServicio;


    @Operation(summary = "Crear un nuevo cliente", description = "Permite agregar un nuevo cliente a la base de datos.")
    @PostMapping("/crear/nuevo")
    public String crearNuevoCliente(@RequestBody ClienteDTO nvoCliente) {
        return this.clienteServicio.crearCliente(nvoCliente);
    }
    
    @Operation(summary = "Obtener cliente por dni", description = "Obtiene un cliente basado en su dni.")
    @GetMapping("/obtener/dni")
    public Optional<ClienteDTO> obtenerPorId(@Parameter(description = "dni del cliente") 
    @RequestParam(name="dni") String dni) throws ClienteNoEncontradoException {
        return this.clienteServicio.obtenerPorDni(dni);
    }

    @Operation(summary = "Obtener todos los clientes", description = "Devuelve una lista con todos los clientes registrados.")
    @GetMapping("/obtener")
    public List<ClienteDTO> obtenerTodos() {
        return this.clienteServicio.obtenerTodos();
    }

   

    @Operation(summary = "Eliminar un cliente", description = "Elimina un cliente de la DB basado en su dni.")
    @DeleteMapping("/eliminar/{dni}")
    public String eliminarCliente(@Parameter(description = "DNI del cliente a eliminar") 
    @PathVariable(name="dni") String dni){
        return this.clienteServicio.eliminarClientePorId(dni);
    }
    
}
