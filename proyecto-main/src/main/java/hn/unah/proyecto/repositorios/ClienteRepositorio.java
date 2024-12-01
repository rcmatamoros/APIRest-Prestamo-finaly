package hn.unah.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import hn.unah.proyecto.modelos.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, String>{
    
}
