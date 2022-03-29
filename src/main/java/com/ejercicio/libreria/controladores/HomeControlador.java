package com.ejercicio.libreria.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 *
 * @author Franco
 */

@Controller
@RequestMapping("/")
public class HomeControlador {
    
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
}
