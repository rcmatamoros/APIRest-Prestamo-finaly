package hn.unah.proyecto.modelos;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="cliente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    private String dni;

    private String nombre;
    
    private String apellido;
    
    private String telefono;
    
    private String correo;

    private double sueldo;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List <Direccion> listaDireccion;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="cliente_prestamos",
            joinColumns = @JoinColumn(name = "dni",referencedColumnName = "dni"),
            inverseJoinColumns = @JoinColumn(name="idprestamo", referencedColumnName = "idPrestamo")
    )
    private List<Prestamos> listaPrestamos;
    
}
