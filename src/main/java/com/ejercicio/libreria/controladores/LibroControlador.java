package com.ejercicio.libreria.controladores;


import com.ejercicio.libreria.entidades.*;
import com.ejercicio.libreria.servicios.*;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Franco
 */

@Controller
@RequestMapping("/libro")
public class LibroControlador {
    
    @Autowired
    private LibroServicio libroServicio;
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @Autowired
    private AutorServicio autorServicio;
    
    @GetMapping("/lista") // localhost:8080/libro/lista
    public String vistaListaLibros(ModelMap modelo){
        List<Libro> libros = libroServicio.buscarLibrosDeAlta();
        
        modelo.put("libros", libros);
        
        return "libros.html";
    }
    
    @GetMapping("/agregar") // localhost:8080/libro/agregar
    public String formAgregarLibro(ModelMap modelo){
        List<Editorial> editoriales = listaEditoriales();
        List<Autor> autores = listaAutores();
        
        modelo.put("autores", autores);
        modelo.put("editoriales", editoriales);
        
        return "libroAgregar.html";
    }
    
    @PostMapping("/agregar")
    public String agregarLibro(ModelMap modelo, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam String autor, @RequestParam String editorial,
            @RequestParam Long isbn, @RequestParam Integer ejemplares, @RequestParam String alta){
        
        try{
            
            libroServicio.agregarLibro(titulo, anio, autor, editorial, isbn, ejemplares, alta);
            
            // Si no pongo estos modelos.put de listas se rompen los inputs de autores y editoriales
            modelo.put("autores", listaAutores());
            modelo.put("editoriales", listaEditoriales());
            modelo.put("exito", true);
            
        }catch(Exception e){
            // Si no pongo estos modelos.put se rompen los inputs de autores y editoriales
            modelo.put("autores", listaAutores());
            modelo.put("editoriales", listaEditoriales());
            modelo.put("error", e.getMessage());
            
        }finally{
            return "libroAgregar.html";
        }
    }
    
    @GetMapping("/editar/{id}")
    public String fromEditarLibro(ModelMap modelo, @PathVariable String id){
        
        modelo.put("libro", libro(id));
        
        modelo.put("autores", listaAutores());
        modelo.put("editoriales", listaEditoriales());
        
        return "editarLibro.html";
    }
    
    @PostMapping("/editar/{id}")
    public String editarLibro(ModelMap modelo, @PathVariable String id, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam String autor, @RequestParam String editorial,
            @RequestParam Long isbn, @RequestParam Integer ejemplares){
        
        try{
            
            libroServicio.editarLibro(id, titulo, anio, autor, editorial, isbn, ejemplares);
            
            // Se carga de nuevo la lista de autores y editoriales y se muestra mensaje de exito
            modelo.put("libro", libro(id));
            modelo.put("autores", listaAutores());
            modelo.put("editoriales", listaEditoriales());
            modelo.put("exito", true);
            
        }catch(Exception e){
            // Se carga de nuevo la lista de autores y editoriales y se muestra mensaje de error
            modelo.put("libro", libro(id));
            modelo.put("autores", listaAutores());
            modelo.put("editoriales", listaEditoriales());
            modelo.put("error", e.getMessage());
            
        }finally{
            return "editarLibro.html";
        }
    }
    
    @GetMapping("/bajas")
    public String listaLibrosDeBaja(ModelMap modelo){
        List<Libro> libros = libroServicio.buscarLibrosDeBaja();
        
        modelo.put("libros", libros);
        
        return "librosDeBaja.html";
    }
    
    @GetMapping("/alta/{id}")
    public String darDeAlta(ModelMap modelo, @PathVariable String id){
        libroServicio.darDeAtla(id, true);
        
        return"redirect:/libro/bajas";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarLibro(ModelMap modelo, @PathVariable String id){
        libroServicio.borrarLibro(id, false);
        
        return "redirect:/libro/lista";
    }
    
    private Libro libro(String id){
        Optional<Libro> libro = libroServicio.buscarLibroPorID(id);
        
        return libro.get();
    }
    
    private List<Autor> listaAutores(){
        List<Autor> autores = autorServicio.buscarAutoresPorAlta();
        
        return autores;
    }
    
    private List<Editorial> listaEditoriales(){
        List<Editorial> editoriales = editorialServicio.buscarEditoriales();
        
        return editoriales;
    }
    
}
