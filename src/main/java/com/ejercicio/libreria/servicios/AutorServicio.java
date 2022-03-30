package com.ejercicio.libreria.servicios;

import com.ejercicio.libreria.entidades.Autor;
import com.ejercicio.libreria.repositorios.AutorRepositorio;
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
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Transactional(readOnly = true)
    public List<Autor> buscarAutoresPorAlta(){
        List<Autor> autores = autorRepositorio.buscarPorAlta();
        
        return autores;
    }
    
    @Transactional(readOnly = true)
    public List<Autor> buscaAutoresPorBaja(){
        List<Autor> autores = autorRepositorio.buscarPorBaja();
        
        return autores;
    }
   
    @Transactional(readOnly = true)
    public Optional<Autor> buscarAutorPorId(String id){
        Optional<Autor> a = autorRepositorio.findById(id);
        
        return a;
    }
    
    @Transactional()
    public void guardarAutor(String nombre, String alta) throws Exception{
        validar(nombre);
        
        Autor a = new Autor();
        
        a.setNombre(nombre);
        
        if(alta.equals("alta")){
            a.setAlta(true);
        }else{
            a.setAlta(false);
        }
        
        autorRepositorio.save(a);
    }
    
    @Transactional()
    public void editarAutor(String id, String nombre) throws Exception{
        validar(nombre);
        
        autorRepositorio.actualizar(id, nombre);
    }
    
    @Transactional()
    public void darDeAltaAutor(String id, boolean alta){
        autorRepositorio.actualizarAlta(id, alta);
    }
    
    @Transactional()
    public void eliminarAutor(String id, boolean alta){
        autorRepositorio.actualizarAlta(id, alta);
    }
    
    private void validar(String nombre) throws Exception{
        if(nombre == null || nombre.isEmpty()){
            throw new Exception("No puede dejar el nombre en blanco");
        }
    }
}
