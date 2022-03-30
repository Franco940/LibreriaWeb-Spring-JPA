package com.ejercicio.libreria.repositorios;

import com.ejercicio.libreria.entidades.Libro;
import com.ejercicio.libreria.entidades.Prestamo;
import java.util.Date;
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
public interface PrestamoRepositorio extends JpaRepository<Prestamo, String>{
    
    @Modifying
    @Query("UPDATE Prestamo AS p SET p.alta = :alta WHERE p.id = :id")
    public void actualizarAlta(@Param("id") String id, @Param("alta") boolean alta);

    @Modifying
    @Query("UPDATE Prestamo AS p SET p.fechaDevolucion = :fechaDevolucion, p.libro = :libro WHERE p.id = :id")
    public void actualizarPrestamo(@Param("id") String id, @Param("fechaDevolucion") Date fechaDevolucion, @Param("libro") Libro libro);
    
    @Query("SELECT p FROM Prestamo AS p WHERE p.alta IS TRUE")
    public List<Prestamo> buscarPrestamosDeAlta();
    
    @Query("SELECT p FROM Prestamo AS p WHERE p.alta IS NOT TRUE")
    public List<Prestamo> buscarPrestamosDeBaja();
}
