package com.ejercicio.libreria.repositorios;

import com.ejercicio.libreria.entidades.Editorial;
import java.util.List;
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
public interface EditorialRepositorio extends JpaRepository<Editorial, String>{
    
    @Modifying
    @Query("UPDATE Editorial AS e SET e.nombre = :nombre WHERE e.id = :id")
    public void actualizar(@Param("id") String id, @Param("nombre") String nombre);
    
    @Modifying
    @Query("UPDATE Editorial AS e SET e.alta = :alta WHERE e.id = :id")
    public void actualizarAlta(@Param("id") String id, @Param("alta") boolean alta);
    
    @Query("SELECT e FROM Editorial AS e WHERE e.alta IS TRUE")
    public List<Editorial> buscarPorAlta();
    
    @Query("SELECT e FROM Editorial AS e WHERE e.alta IS NOT TRUE")
    public List<Editorial> buscarPorBaja();
    
    @Query("SELECT e FROM Editorial e WHERE e.nombre = :nombre")
    public Editorial buscarPorNombre(@Param("nombre") String nombre);
}
