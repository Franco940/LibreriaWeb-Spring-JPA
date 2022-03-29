package com.ejercicio.libreria.repositorios;

import com.ejercicio.libreria.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Franco
 */

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String>{
    
    @Query("SELECT c FROM Cliente c WHERE c.documento = :documento")
    public Cliente buscarPorDocumento(@Param("documento") Long documento);
    
    @Modifying
    @Query("UPDATE Cliente c SET c.alta = :alta WHERE c.id = :id")
    public void actualizarAlta(@Param("id") String id, @Param("alta") boolean alta);
}
