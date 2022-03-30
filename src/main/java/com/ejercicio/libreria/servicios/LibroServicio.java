package com.ejercicio.libreria.servicios;

import com.ejercicio.libreria.entidades.Autor;
import com.ejercicio.libreria.entidades.Editorial;
import com.ejercicio.libreria.entidades.Libro;
import com.ejercicio.libreria.repositorios.AutorRepositorio;
import com.ejercicio.libreria.repositorios.EditorialRepositorio;
import com.ejercicio.libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Franco
 */

@Service
public class LibroServicio {
    
    @Autowired
    private LibroRepositorio libroRepositorio;
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    
    @Transactional(readOnly = true)
    public List<Libro> buscarLibrosDeAlta(){
        List<Libro> libros = libroRepositorio.buscarLibroPorAlta();
        
        return libros;
    }
    
    @Transactional(readOnly = true)
    public List<Libro> buscarLibrosDeBaja(){
        List<Libro> libros = libroRepositorio.buscarLibroPorBaja();
        
        return libros;
    }
    
    @Transactional(readOnly = true)
    public Optional<Libro> buscarLibroPorID(String id){
        Optional<Libro> libro = libroRepositorio.findById(id);
        
        return libro;
    }
    
    @Transactional()
    public void editarLibro(String id, String titulo, Integer anio, String autor, String editorial, Long isbn, Integer ejemplares) throws Exception{
        validar(titulo, autor, editorial);
        Autor au = buscarAutor(autor);
        Editorial edi = buscarEditorial(editorial);
        
        libroRepositorio.actualizar(id, titulo, anio, au, edi, isbn, ejemplares);
    }
    
    @Transactional()
    public void agregarLibro(String titulo, Integer anio, String autor, String editorial, Long isbn, Integer ejemplares, String alta) throws Exception{
        
        validar(titulo, autor, editorial);
        Autor a = buscarAutor(autor);
        Editorial e = buscarEditorial(editorial);
        
        Libro libro = new Libro();
        
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setAutor(a);
        libro.setEditorial(e);
        libro.setIsbn(isbn);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(0);
        libro.setEjemplaresRestantes(ejemplares); // Mismo valor que ejemplares
        
        if(alta.equals("alta")){
            libro.setAlta(true);
        }else{
            libro.setAlta(false);
        }
        
        libroRepositorio.save(libro);
    }
    
    @Transactional()
    public void actualizarEjemplares(Libro l){
        libroRepositorio.actualizarEjemplares(l.getId(), l.getEjemplaresPrestados(), l.getEjemplaresRestantes());
    }
    
    @Transactional()
    public void darDeAtla(String id, boolean alta){
        libroRepositorio.actualizarAlta(id, alta);
    }
    
    @Transactional()
    public void borrarLibro(String id, boolean alta){
        libroRepositorio.actualizarAlta(id, alta);
    }
    
    @Transactional(readOnly = true)
    private Autor buscarAutor(String autor){
        try{
            Autor a = autorRepositorio.buscarPorAutor(autor);
            
            return a;
        }catch(Exception e){
            return null;
        }
    }
    
    @Transactional(readOnly = true)
    private Editorial buscarEditorial(String editorial){
        try{
            Editorial e = editorialRepositorio.buscarPorNombre(editorial);
            
            return e;
        }catch(Exception e){
            return null;
        }
    }
    
    private void validar(String titulo, String autor, String editorial) throws Exception{
        if(titulo.isEmpty() || titulo == null){
            throw new Exception("No se puede dejar el titulo en blanco");
        }
    }
}
