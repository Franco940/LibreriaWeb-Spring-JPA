package com.ejercicio.libreria.repositorios;

import com.ejercicio.libreria.entidades.Autor;
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
public interface AutorRepositorio extends JpaRepository<Autor, String>{
    
    @Modifying
    @Query("UPDATE Autor AS a SET a.nombre = :nombre WHERE a.id = :id")
    public void actualizar(@Param("id") String id, @Param("nombre") String nombre);
    
    @Modifying
    @Query("UPDATE Autor AS a SET a.alta = :alta WHERE a.id = :id")
    public void actualizarAlta(@Param("id") String id, @Param("alta") boolean alta);
    
    @Query("SELECT a FROM Autor a WHERE a.alta IS TRUE")
    public List<Autor> buscarPorAlta();
    
    @Query("SELECT a FROM Autor a WHERE a.alta IS NOT TRUE")
    public List<Autor> buscarPorBaja();
    
    @Query("SELECT a FROM Autor a WHERE a.nombre = :nombre")
    public Autor buscarPorAutor(@Param("nombre") String nombre);
}
