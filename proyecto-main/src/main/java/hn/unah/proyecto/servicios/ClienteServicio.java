package hn.unah.proyecto.servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import hn.unah.proyecto.modelos.Cliente;
import hn.unah.proyecto.modelos.Direccion;
import hn.unah.proyecto.modelos.Prestamos;
import hn.unah.proyecto.modelos.TablaAmortizacion;
import hn.unah.proyecto.ModelMapper.ModelMapperSingleton;
import hn.unah.proyecto.dtos.ClienteDTO;
import hn.unah.proyecto.dtos.DireccionDTO;
import hn.unah.proyecto.dtos.Prestamos2DTO;
import hn.unah.proyecto.enums.PrestamoEnum;
import hn.unah.proyecto.excepciones.ClienteNoEncontradoException;
import hn.unah.proyecto.repositorios.ClienteRepositorio;

@Service

public class ClienteServicio {
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Value("${prestamo.vehicular}")
    private double vehicular;

    @Value("${prestamo.personal}")
    private double personal;

    @Value("${prestamo.hipotecario}")
    private double hipotecario;

    private ModelMapper modelMapper = ModelMapperSingleton.getInstancia();

    public List<ClienteDTO> obtenerTodos() {
        List<Cliente> listaCliente = clienteRepositorio.findAll();
        List<ClienteDTO> listaClienteDTO = new ArrayList<>();
        ClienteDTO clienteDTO;
        for (Cliente cliente : listaCliente) {
            clienteDTO = modelMapper.map(cliente, ClienteDTO.class);
            listaClienteDTO.add(clienteDTO);
        }
        return listaClienteDTO;
    }

    public Optional<ClienteDTO> obtenerPorDni(String dni) throws ClienteNoEncontradoException {
        Optional<Cliente> cliente = clienteRepositorio.findById(dni);

        if (cliente.isEmpty()) {
            throw new ClienteNoEncontradoException("Cliente con el DNI " + dni + " no encontrado.");
        }

        ClienteDTO clienteDto = this.modelMapper.map(cliente.get(), ClienteDTO.class);

        return Optional.ofNullable(clienteDto);
    }

    public String crearCliente(ClienteDTO nvoCliente) {
        if (this.clienteRepositorio.existsById(nvoCliente.getDni())) {
            return "Ya existe el cliente";
        }

        Cliente nvoClienteBd = modelMapper.map(nvoCliente, Cliente.class);

        List<DireccionDTO> direccionDTOs = nvoCliente.getListaDireccion();
        List<Direccion> listaDirecciones = new ArrayList<>();

        if (direccionDTOs == null) {
            return "Digite al menos 1 direccion";
        }

        if (direccionDTOs.size() <= 2) {
            for (DireccionDTO d : direccionDTOs) {
                Direccion direccion = modelMapper.map(d, Direccion.class);
                direccion.setCliente(nvoClienteBd);
                listaDirecciones.add(direccion);
            }
        } else {
            return "Un cliente no puede tener mas de 2 direcciones";
        }

        List<Prestamos> listaPrestamos = new ArrayList<>();
        if (nvoCliente.getListaPrestamos() == null) {
            listaPrestamos.add(null);
        } else {

            List<Prestamos2DTO> prestamosDTOs = nvoCliente.getListaPrestamos();
            List<TablaAmortizacion> listaAmortizacion = new ArrayList<>();

            for (Prestamos2DTO p : prestamosDTOs) {
                Prestamos nvoPrestamo = modelMapper.map(p, Prestamos.class);
                char tipo = Character.toUpperCase(nvoPrestamo.getTipoPrestamo());
                nvoPrestamo = modelMapper.map(p, Prestamos.class);
                tipo = Character.toUpperCase(nvoPrestamo.getTipoPrestamo());
                if (tipo == PrestamoEnum.Hipotecario.getC() ||
                        tipo == PrestamoEnum.Personal.getC() ||
                        tipo == PrestamoEnum.Vehicular.getC()) {
                    if (nvoPrestamo.getPlazo() >= 1) {
                        nvoPrestamo.setEstado('P');
                        nvoPrestamo.setTipoPrestamo(tipo);

                        switch (tipo) {
                            case 'V':
                                nvoPrestamo.setTasaInteres(vehicular);
                                break;
                            case 'P':
                                nvoPrestamo.setTasaInteres(personal);
                                break;
                            case 'H':
                                nvoPrestamo.setTasaInteres(hipotecario);
                                break;
                        }

                        double cuota = this.calcularCuota(nvoPrestamo);
                        nvoPrestamo.setCuota(cuota);

                        listaAmortizacion = insertarCuotasEnTablaAmortizacion(nvoPrestamo);
                        nvoPrestamo.setListaAmortizacion(listaAmortizacion);

                        listaPrestamos.add(nvoPrestamo);
                    } else {
                        return "El plazo mínimo para un préstamo es de 1 año";
                    }
                } else {
                    return "El prestamo con codigo: " + tipo + " no es valido.";
                }
            }

            double tE = this.calcularTotalEgresos(listaPrestamos);
            double nivelEndeudamiento = tE / nvoClienteBd.getSueldo();

            if (nivelEndeudamiento >= 0.4) {
                return "El nivel de endeudamiento es mayor al establecido" + "\n" +
                        "Nivel endeudamiento: " + (int) (nivelEndeudamiento * 100) + "%" +
                        "\nLimite máximo: 40%";
            }

        }

        nvoClienteBd.setListaDireccion(listaDirecciones);
        nvoClienteBd.setListaPrestamos(listaPrestamos);

        clienteRepositorio.save(nvoClienteBd);

        return "Cliente creado exitosamente";
    }

    private double calcularCuota(Prestamos prestamo) {
        double r = prestamo.getTasaInteres() / 12;
        int n = prestamo.getPlazo() * 12;
        double P = prestamo.getMonto();

        return (P * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
    }

    private List<TablaAmortizacion> insertarCuotasEnTablaAmortizacion(Prestamos prestamo) {
        List<TablaAmortizacion> tablaAmortizaciones = new ArrayList<>();
        double saldoPendiente = prestamo.getMonto();
        double r = prestamo.getTasaInteres() / 12;
        int n = prestamo.getPlazo() * 12;

        for (int i = 1; i <= n; i++) {
            double interes = saldoPendiente * r;
            double capital = prestamo.getCuota() - interes;

            saldoPendiente -= capital;

            TablaAmortizacion cuota = new TablaAmortizacion();
            cuota.setNumeroCuota(i);
            cuota.setInteres(interes);
            cuota.setCapital(capital);
            cuota.setSaldo(saldoPendiente);
            cuota.setFechaVencimiento(LocalDate.now().plusMonths(i));
            cuota.setEstado('P');

            cuota.setPrestamo(prestamo);
            tablaAmortizaciones.add(cuota);
        }
        return tablaAmortizaciones;
    }

    public String eliminarClientePorId(String id) {
        if (!this.clienteRepositorio.existsById(id)) {
            return "No existe el cliente";
        }
        this.clienteRepositorio.deleteById(id);
        return "Cliente eliminado satisfactoriamente";
    }

    private double calcularTotalEgresos(List<Prestamos> listaPrestamos) {
        double totalEgresos = 0;

        for (Prestamos prestamo : listaPrestamos) {
            totalEgresos += prestamo.getCuota();
        }

        return totalEgresos;
    }

}
