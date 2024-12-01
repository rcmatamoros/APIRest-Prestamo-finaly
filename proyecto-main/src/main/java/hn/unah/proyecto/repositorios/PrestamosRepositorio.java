package hn.unah.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import hn.unah.proyecto.modelos.Prestamos;

public interface PrestamosRepositorio extends JpaRepository<Prestamos,Integer>{
    
}
