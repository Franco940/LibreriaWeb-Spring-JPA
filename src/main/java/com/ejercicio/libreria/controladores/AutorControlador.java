package com.ejercicio.libreria.controladores;

import com.ejercicio.libreria.entidades.Autor;
import com.ejercicio.libreria.servicios.AutorServicio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Franco
 */

@Controller
@RequestMapping("/autor")
public class AutorControlador {
    
    @Autowired
    private AutorServicio autorServicio;
    
    @GetMapping("/lista")
    public String listaAutores(ModelMap modelo){
        List<Autor> autores = autorServicio.buscarAutoresPorAlta();
        
        modelo.put("autores", autores);
        
        return "autores.html";
    }
    
    @GetMapping("/bajas")
    public String listaAutoresDeBaja(ModelMap modelo){
        List<Autor> autores = autorServicio.buscaAutoresPorBaja();
        
        modelo.put("autores", autores);
        
        return "autoresDeBaja.html";
    }
    
    @GetMapping("/agregar")
    public String formAgregarAutor(){
        return "autoresAgregar.html";
    }
    
    @PostMapping("/agregar")
    public String agregarAutor(ModelMap modelo, @RequestParam String nombre, @RequestParam String alta){
        
        try{
            autorServicio.guardarAutor(nombre, alta);
        
            modelo.put("exito", true);
        
            return "autoresAgregar.html";
        }catch(Exception e){
            modelo.put("error", true);
            return "autoresAgregar.html";
        }
    }
    
    @GetMapping("/alta/{id}")
    public String darDeAlta(ModelMap modelo, @PathVariable String id){
        autorServicio.darDeAltaAutor(id, true);
        
        return "redirect:/autor/bajas";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarAutor(ModelMap modelo, @PathVariable String id){
        
        autorServicio.eliminarAutor(id, false);
        
        return "redirect:/autor/lista";
    }

    @GetMapping("/editar/{id}")
    public String formEditarAutor(ModelMap modelo, @PathVariable String id){
        Optional<Autor> a = autorServicio.buscarAutorPorId(id);
        
        modelo.put("autor", a.get());
        
        return "editarAutores.html";
    }
    
    @PostMapping("/editar/{id}")
    public String editarAutor(ModelMap modelo, @PathVariable String id, @RequestParam String nombre){
        try{
            
            autorServicio.editarAutor(id, nombre);
        
            modelo.put("exito", true);
            
            return "editarEditorial.html";
        }catch(Exception e){
            modelo.put("error", true);
            return "editarEditorial.html";
        }
    }
}
