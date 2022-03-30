package com.ejercicio.libreria.controladores;

import com.ejercicio.libreria.entidades.Libro;
import com.ejercicio.libreria.entidades.Prestamo;
import com.ejercicio.libreria.servicios.LibroServicio;
import com.ejercicio.libreria.servicios.PrestamoServicio;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Franco
 */

@Controller
@RequestMapping("/prestamo")
public class PrestamoControlador {
    
    @Autowired
    private PrestamoServicio prestamosServicio;
    
    @Autowired
    private LibroServicio libroServicio;
    
    @GetMapping("/lista")
    public String index(ModelMap modelo){
        
        List<Prestamo> prestamos = prestamosServicio.prestamosDeAtla();
        
        modelo.put("prestamos", prestamos);
        
        return "prestamo.html";
    }
    
    @GetMapping("/baja")
    public String listaPretamosDeBaja(ModelMap modelo){
        
        List<Prestamo> prestamos = prestamosServicio.prestamosDeBaja();
        
        modelo.put("prestamos", prestamos);
        
        return "prestamosDebaja.html";
    }
    
    @GetMapping("/baja/{id}")
    public String darDeBaja(@PathVariable String id){
        prestamosServicio.actualizarAlta(id, false);
        
        return "redirect:/prestamo/lista";
    }
    @GetMapping("/alta/{id}")
    public String darDeAlta(@PathVariable String id){
        prestamosServicio.actualizarAlta(id, true);
        
        return "redirect:/prestamo/baja";
    }
    
    @GetMapping("/editar/{id}")
    public String formEditarPrestamo(ModelMap modelo, @PathVariable String id){
        Optional<Prestamo> prestamo = prestamosServicio.buscarPrestamoPorId(id);
        List<Libro> libros = libroServicio.buscarLibrosDeAlta();
        
        
        modelo.put("prestamo", prestamo.get());
        modelo.put("libros", libros);
        
        return "prestamoEditar.html";
    }
    
    @PostMapping("/editar/{id}")
    public String editarPrestamo(ModelMap modelo, @PathVariable String id, @RequestParam String idLibro, @RequestParam String fechaDevolucion){
        try{
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaDevolver = formateador.parse(fechaDevolucion);
            
            prestamosServicio.editarPrestamo(id, idLibro, fechaDevolver);
            
            modelo.put("exito", true);
            return "prestamoEditar.html";
        }catch(Exception e){
            modelo.put("error", true);
            return "prestamoEditar.html";
        }
    }
    
    @GetMapping("/hacer")
    public String formPrestamo(ModelMap modelo){
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy"); // Para mostrar la fecha en el HTML
        Date fechaActual = new Date();
        String fechaPrestamo = formateador.format(fechaActual);
        
        List<Libro> libros = libroServicio.buscarLibrosDeAlta();
    
        modelo.put("libros", libros);
        modelo.put("fechaPrestamo", fechaPrestamo);
        
        return "prestamoHacer.html";
    }
    
    @PostMapping("/hacer")
    public String hacerPrestamo(ModelMap modelo, @RequestParam() Long documento, @RequestParam String libro,
            @RequestParam String fechaDevolucion){
        try{
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaActual = new Date();
            String fechaActual2 = formateador.format(fechaActual);
            
            Date fechaPrestamo = formateador.parse(fechaActual2);
            Date fechaDevolver = formateador.parse(fechaDevolucion);
            
            prestamosServicio.guardarPrestamo(documento, libro, fechaPrestamo, fechaDevolver);
            
            modelo.put("exito", true);
            return "prestamoHacer.html";
        }catch(Exception e){
            modelo.put("error", true);
            return "prestamoHacer.html";
        }
    }
}
