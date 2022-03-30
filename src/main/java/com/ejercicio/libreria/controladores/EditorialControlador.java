package com.ejercicio.libreria.controladores;

import com.ejercicio.libreria.entidades.Editorial;
import com.ejercicio.libreria.servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/lista")
    public String listaEditoriales(ModelMap modelo){
        
        List<Editorial> editoriales = editorialServicio.buscarEditoriales();
        
        modelo.put("editoriales", editoriales);
        
        return "editoriales.html";
    }
    
    @GetMapping("/bajas")
    public String listaEditorialesDeBaja(ModelMap modelo){
        List<Editorial> editoriales = editorialServicio.buscarEditorialesDeBaja();
        
        modelo.put("editoriales", editoriales);
        
        return "editorialesDeBaja.html";
    }
    
    @GetMapping("/agregar")
    public String formAgregarEditorial(){
        return "editorialAgregar.html";
    }
    
    @PostMapping("/agregar")
    public String agregarEditorial(ModelMap modelo, @RequestParam String nombre, @RequestParam String alta){
        
        try{
            editorialServicio.guardarEditorial(nombre, alta);
        
            modelo.put("exito", true);
        
        return "editorialAgregar.html";
        }catch(Exception e){
            modelo.put("error", e.getMessage());
            return "editorialAgregar.html";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarEditorial(ModelMap modelo, @PathVariable String id){
        
        editorialServicio.borrarEditorial(id, false);
        
        return "redirect:/editorial/lista";
    }
    
    @GetMapping("/alta/{id}")
    public String darDeAlta(ModelMap modelo, @PathVariable String id){
        editorialServicio.darDeAlta(id, true);
        
        return"redirect:/editorial/bajas";
    }
    
    @GetMapping("/editar/{id}")
    public String formEditarEditorial(ModelMap modelo, @PathVariable String id){
        Optional<Editorial> editorial = editorialServicio.buscarEditorialPorID(id);
        
        modelo.put("editorial", editorial.get());
        
        return "editarEditorial.html";
    }
    
    @PostMapping("/editar/{id}")
    public String editarEditorial(ModelMap modelo, @PathVariable String id, @RequestParam String nombre){
        try{
            
            editorialServicio.editarEditorial(id, nombre);
        
            modelo.put("exito", true);
            
            return "editarEditorial.html";
        }catch(Exception e){
            modelo.put("error", e.getMessage());
            return "editarEditorial.html";
        }
    }
}
