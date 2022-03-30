package com.ejercicio.libreria.servicios;

import com.ejercicio.libreria.entidades.Editorial;
import com.ejercicio.libreria.repositorios.EditorialRepositorio;
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
public class EditorialServicio {
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void guardarEditorial(String nombre, String alta) throws Exception{
        validar(nombre);
        
        Editorial e = new Editorial();
        
        e.setNombre(nombre);
        
        if(alta.equals("alta")){
            e.setAlta(true);
        }else{
            e.setAlta(false);
        }
        
        editorialRepositorio.save(e);
    }
    
    @Transactional(readOnly = true)
    public List<Editorial> buscarEditoriales(){
        List<Editorial> editoriales = editorialRepositorio.buscarPorAlta();
        
        return editoriales;
    }
    
    @Transactional(readOnly = true)
    public List<Editorial> buscarEditorialesDeBaja(){
        List<Editorial> editoriales = editorialRepositorio.buscarPorBaja();
        
        return editoriales;
    }
    
    @Transactional(readOnly = true)
    public Optional<Editorial> buscarEditorialPorID(String id){
        Optional<Editorial> e = editorialRepositorio.findById(id);
        
        return e;
    }
    
    @Transactional
    public void editarEditorial(String id, String nombre) throws Exception{
        
        validar(nombre);
        
        editorialRepositorio.actualizar(id, nombre);
    }
    
    @Transactional
    public void darDeAlta(String id, boolean alta){
        editorialRepositorio.actualizarAlta(id, alta);
    }
    
    @Transactional
    public void borrarEditorial(String id, boolean alta){
        editorialRepositorio.actualizarAlta(id, alta);
    }
    
    private void validar(String nombre) throws Exception{
        
        if(nombre.isEmpty() || nombre == null){
            throw new Exception("No se puede dejar el nombre en blanco");
        }
    }
}
