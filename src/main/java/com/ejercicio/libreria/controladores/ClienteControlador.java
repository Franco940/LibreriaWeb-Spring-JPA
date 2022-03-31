package com.ejercicio.libreria.controladores;

import com.ejercicio.libreria.entidades.Cliente;
import com.ejercicio.libreria.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Franco
 */

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {
    
    @Autowired
    private ClienteServicio clienteServicio;
    
    @GetMapping("/registro")
    public String registroForm(){
        return "clienteRegistro.html";
    }
    
    @PostMapping("/registro")
    public String registro(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam Long documento, @RequestParam String telefono){
        try{
                    
            clienteServicio.registrarCliente(nombre, apellido, telefono, documento);
            
            modelo.put("exito", true);
            
        }catch(Exception e){
            modelo.put("error", e.getMessage());
        }finally{
            return "clienteRegistro.html";
        }
    }
    
    @GetMapping("/estado")
    public String estadoCliente(ModelMap modelo){
        return "clienteEstado.html";
    }
    
    @PostMapping("/estado")
    public String mostrarEstadoCliente(ModelMap modelo, @RequestParam Long documento){
        Cliente cliente = clienteServicio.buscarEstadoCliente(documento);
        
        modelo.put("cliente", cliente);
        
        return "clienteEstado.html";
    }
    
    @GetMapping("/baja")
    public String formBaja(){
        return "clienteBaja.html";
    }
    
    @PostMapping("/baja")
    public String baja(ModelMap modelo, @RequestParam Long documento){
        try{
            clienteServicio.cambiarAlta(documento, false);
        
            modelo.put("exito", true);
            
        }catch(Exception e){
            modelo.put("error", true);
        }finally{
            return "clienteBaja.html";
        }
    }
    
    @GetMapping("/alta")
    public String formAlta(){
        return "clienteAlta.html";
    }
    
    @PostMapping("/alta")
    public String alta(ModelMap modelo, @RequestParam Long documento){
        try{
            clienteServicio.cambiarAlta(documento, true);
        
            modelo.put("exito", true);
        }catch(Exception e){
            modelo.put("error", true);
        }finally{
            return "clienteBaja.html";
        }
    }
    
}
