package hn.unah.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import hn.unah.proyecto.modelos.Direccion;

public interface DireccionRepositorio extends JpaRepository<Direccion,Integer> {
    
}
