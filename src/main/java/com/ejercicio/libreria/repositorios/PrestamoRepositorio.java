package com.ejercicio.libreria.repositorios;

import com.ejercicio.libreria.entidades.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Franco
 */

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, String>{
    
}
