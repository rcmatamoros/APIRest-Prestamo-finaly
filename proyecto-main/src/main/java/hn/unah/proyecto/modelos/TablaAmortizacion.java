package hn.unah.proyecto.modelos;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tabla_amortizacion")
public class TablaAmortizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="numerocuota")
    private int numeroCuota;

    private double interes;
    
    private double capital;
    
    private double saldo;
    
    private char estado;
    
    @Column(name="fechavencimiento")
    private LocalDate fechaVencimiento;

    @ManyToOne()
    @JoinColumn(name="idprestamo",referencedColumnName = "idPrestamo")
    private Prestamos prestamo;
}
