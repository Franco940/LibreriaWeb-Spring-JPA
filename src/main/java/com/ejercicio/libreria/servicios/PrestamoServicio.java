package com.ejercicio.libreria.servicios;

import com.ejercicio.libreria.entidades.Cliente;
import com.ejercicio.libreria.entidades.Libro;
import com.ejercicio.libreria.entidades.Prestamo;
import com.ejercicio.libreria.repositorios.PrestamoRepositorio;
import java.util.Date;
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
public class PrestamoServicio {
    
    @Autowired
    private PrestamoRepositorio prestamoRepositorio;
    
    @Autowired
    private ClienteServicio clienteServicio;
    
    @Autowired
    private LibroServicio libroServicio;
    
    
    @Transactional(readOnly = true)
    public List<Prestamo> prestamosDeAtla(){
        List<Prestamo> prestamos = prestamoRepositorio.buscarPrestamosDeAlta();
        
        return prestamos;
    }
    
    @Transactional(readOnly = true)
    public List<Prestamo> prestamosDeBaja(){
        List<Prestamo> prestamos = prestamoRepositorio.buscarPrestamosDeBaja();
        
        return prestamos;
    }
    
    @Transactional()
    public void actualizarAlta(String id, boolean alta){
        prestamoRepositorio.actualizarAlta(id, alta);
    }
    
    @Transactional(readOnly = true)
    public Optional<Prestamo> buscarPrestamoPorId(String id){
        Optional<Prestamo> prestamo = prestamoRepositorio.findById(id);
        
        return prestamo;
    }
    
    @Transactional()
    public void editarPrestamo(String idPrestamo, String idLibro, Date fechaDevolucion){
        Optional<Libro> libro = buscarLibro(idLibro);
        
        prestamoRepositorio.actualizarPrestamo(idPrestamo, fechaDevolucion, libro.get());
    }
    
    @Transactional()
    public void guardarPrestamo(Long documentoCliente, String idLibro, Date fechaPrestamo, Date fechaDevolucion) throws Exception{
        Cliente cliente = verificarCliente(documentoCliente);
        Libro libro = verificarEjemplares(idLibro);
            
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
    }
    
    @Transactional(readOnly = true)
    private Optional<Libro> buscarLibro(String id){
        Optional<Libro> libro = libroServicio.buscarLibroPorID(id);
        
        return libro;
    }
    
    @Transactional(readOnly = true)
    private Cliente verificarCliente(Long documento) throws Exception{
        Cliente cliente = clienteServicio.buscarClientePorDocumento(documento);
        
        if(cliente != null){
            return cliente;
        }else{
            throw new Exception("No se encontró un ningún cliente con ese documento.");
        }
    }
    
    @Transactional(readOnly = true)
    private Libro verificarEjemplares(String idLibro) throws Exception{
        Optional<Libro> libro = libroServicio.buscarLibroPorID(idLibro);
        
        if(libro.get().getEjemplaresRestantes() > 0){
            return libro.get();
        }else{
            throw new Exception("No hay más ejemplares del libro seleccionado.");
        }
    }
    
}
