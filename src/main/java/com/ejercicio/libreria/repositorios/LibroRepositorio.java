package com.ejercicio.libreria.repositorios;

import com.ejercicio.libreria.entidades.Autor;
import com.ejercicio.libreria.entidades.Editorial;
import com.ejercicio.libreria.entidades.Libro;
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
public interface LibroRepositorio extends JpaRepository<Libro, String>{
    
    @Modifying
    @Query("UPDATE Libro AS l SET l.titulo = :titulo, l.anio = :anio, l.autor = :autor, l.editorial = :editorial, l.isbn = :isbn, l.ejemplares = :ejemplares, l.ejemplaresRestantes = :ejemplares WHERE l.id = :id") // l ES UNA L minuscula
    public void actualizar(@Param("id") String id, @Param("titulo") String titulo, @Param("anio") Integer anio, @Param("autor") Autor autor, 
            @Param("editorial") Editorial editorial, @Param("isbn") Long isbn, @Param("ejemplares") Integer ejemplares);
    
    @Modifying
    @Query("UPDATE Libro AS l SET l.alta = :alta WHERE l.id = :id") // l ES UNA L minuscula
    public void actualizarAlta(@Param("id") String id, @Param("alta") boolean alta);
    
    @Modifying
    @Query("UPDATE Libro AS l SET l.ejemplaresPrestados = :ejemplaresPrestados, l.ejemplaresRestantes = :ejemplaresRestantes WHERE l.id = :id")
    public void actualizarEjemplares(@Param("id") String id, @Param("ejemplaresPrestados") Integer ejemplaresPrestados, @Param("ejemplaresRestantes") Integer ejemplaresRestantes);
    
    @Query("SELECT l FROM Libro AS l JOIN Autor AS a ON l.autor = a.id JOIN Editorial AS e ON l.editorial = e.id WHERE a.alta IS TRUE AND e.alta IS TRUE AND l.alta IS TRUE") // l ES UNA L minuscula
    public List<Libro> buscarLibroPorAlta();
    
    @Query("SELECT l FROM Libro AS l WHERE l.alta IS NOT TRUE") // l ES UNA L minuscula
    public List<Libro> buscarLibroPorBaja();  
}
