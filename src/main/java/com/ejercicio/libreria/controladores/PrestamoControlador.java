package com.ejercicio.libreria.controladores;

import com.ejercicio.libreria.entidades.Libro;
import com.ejercicio.libreria.entidades.Prestamo;
import com.ejercicio.libreria.servicios.LibroServicio;
import com.ejercicio.libreria.servicios.PrestamoServicio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        
        modelo.put("prestamo", prestamo.get());
        modelo.put("libros", listaLibros());
        
        return "prestamoEditar.html";
    }
    
    @PostMapping("/editar/{id}")
    public String editarPrestamo(ModelMap modelo, @PathVariable String id, @RequestParam String idLibro, @RequestParam String fechaDevolucion){
        try{
            Date fechaDevolver = fechaEnDate(fechaDevolucion);
            
            prestamosServicio.editarPrestamo(id, idLibro, fechaDevolver);
            
            modelo.put("exito", true);
            
        }catch(Exception e){
            modelo.put("error", e.getMessage());
            
        }finally{
            return "prestamoEditar.html";
        }
    }
    
    @GetMapping("/hacer")
    public String formPrestamo(ModelMap modelo){
        String fechaPrestamo = fechaActualEnString("normal");
    
        modelo.put("libros", listaLibros());
        modelo.put("fechaPrestamo", fechaPrestamo);
        
        return "prestamoHacer.html";
    }
    
    @PostMapping("/hacer")
    public String hacerPrestamo(ModelMap modelo, @RequestParam() Long documento, @RequestParam String libro,
            @RequestParam String fechaDevolucion){
        try{
            Date fechaPrestamo = fechaEnDate(fechaActualEnString("ingles"));
            Date fechaDevolver = fechaEnDate(fechaDevolucion);
            
            prestamosServicio.guardarPrestamo(documento, libro, fechaPrestamo, fechaDevolver);
            
            // Muestran los datos. Sin ellos muestra el exito y los inputs se rompen
            modelo.put("exito", true);
            modelo.put("fechaPrestamo", fechaActualEnString("normal"));
            modelo.put("libros", listaLibros()); // Permite que el selector de libros no se quede vacio al dar el mensaje de exito
        }catch(Exception e){
            // Muestran los datos. Sin ellos muestra el error y los inputs se rompen
            modelo.put("libros", listaLibros());
            modelo.put("fechaPrestamo", fechaActualEnString("normal"));
            modelo.put("error", e.getMessage());
        }finally{
            return "prestamoHacer.html";
        }
    }
    
    private List<Libro> listaLibros(){ // Para el seleccionador de libros
        List<Libro> libros = libroServicio.buscarLibrosDeAlta();
        
        return libros;
    }
    
    private Date fechaEnDate(String fecha) throws ParseException{
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaDate = formateador.parse(fecha);
        
        return fechaDate;
    }
    
    private String fechaActualEnString(String formato){
        if(formato.equals("normal")){
            SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
            Date fechaActual = new Date();
            String fechaPrestamo = formateador.format(fechaActual);
            return fechaPrestamo;
        }else{
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaActual = new Date();
            String fechaPrestamo = formateador.format(fechaActual);
            return fechaPrestamo;
        }
    }
    
}
