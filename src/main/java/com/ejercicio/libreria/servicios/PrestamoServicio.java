package com.ejercicio.libreria.servicios;

import com.ejercicio.libreria.entidades.Cliente;
import com.ejercicio.libreria.entidades.Libro;
import com.ejercicio.libreria.entidades.Prestamo;
import com.ejercicio.libreria.repositorios.PrestamoRepositorio;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Franco
 */

@Service
public class PrestamoServicio {
    
    @Autowired
    private PrestamoRepositorio prestamoRepositorio;
    
    @Autowired
    private ClienteServicio clienteServicio;
    
    @Autowired
    private LibroServicio libroServicio;
    
    
    @Transactional()
    public void guardarPrestamo(Long documentoCliente, String idLibro, Date fechaPrestamo, Date fechaDevolucion) throws Exception{
        Cliente cliente = verificarCliente(documentoCliente);
        Libro libro = verificarEjemplares(idLibro);
        
        if(libro != null  && cliente != null){
            Prestamo prestamo = new Prestamo();
            
            prestamo.setAlta(true);
            prestamo.setCliente(cliente);
            prestamo.setLibro(libro);
            prestamo.setFechaPrestamo(fechaPrestamo);
            prestamo.setFechaDevolucion(fechaDevolucion);
            
            prestamoRepositorio.save(prestamo);
            
            libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() - 1);
            libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() + 1);
            libroServicio.actualizarEjemplares(libro);
            
        }else{
            throw new Exception();
        }
    }
    
    @Transactional()
    private Cliente verificarCliente(Long documento){
        Cliente cliente = clienteServicio.buscarClientePorDocumento(documento);
        
        if(cliente != null){
            return cliente;
        }else{
            return null;
        }
    }
    
    @Transactional()
    private Libro verificarEjemplares(String idLibro){
        Optional<Libro> libro = libroServicio.buscarLibroPorID(idLibro);
        
        if(libro.get().getEjemplaresRestantes() > 0){
            return libro.get();
        }else{
            return null;
        }
    }
}
