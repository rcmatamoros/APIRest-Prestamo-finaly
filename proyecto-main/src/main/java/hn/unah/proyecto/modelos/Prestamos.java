package hn.unah.proyecto.modelos;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="prestamos")
public class Prestamos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idprestamo")
    private int idPrestamo;

    private double monto;
    
    private int plazo;

    @Column(name="tasa_interes")
    private double tasaInteres;

    private double cuota;

    private char estado;

    @Column(name="tipoprestamo")
    private char tipoPrestamo;

    @ManyToMany(mappedBy = "listaPrestamos")
    private List<Cliente> listaCliente;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "prestamo")
    private List<TablaAmortizacion> listaAmortizacion;
}
