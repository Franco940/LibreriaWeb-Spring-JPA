package com.ejercicio.libreria.servicios;

import com.ejercicio.libreria.entidades.Cliente;
import com.ejercicio.libreria.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Franco
 */

@Service
public class ClienteServicio {
    
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @Transactional(readOnly = true)
    public Cliente buscarEstadoCliente(Long id){
        Cliente cliente = buscarClientePorDocumento(id);
        
        return cliente;
    }
    
    @Transactional()
    public void registrarCliente(String nombre, String apellido, String telefono, Long documento) throws Exception{
        validar(nombre, apellido, telefono, documento);
        
        Cliente clienteYaExisteConEseDocumento = buscarClientePorDocumento(documento);
        
        if(clienteYaExisteConEseDocumento != null){
            throw new Exception();
        }else{
            Cliente cliente = new Cliente(nombre, apellido, telefono, documento, true);
            clienteRepositorio.save(cliente);
        }
    }
    
    @Transactional()
    public void cambiarAlta(Long documento, boolean alta){
        Cliente c = buscarClientePorDocumento(documento);
        
        c.setAlta(alta);
        
        clienteRepositorio.actualizarAlta(c.getId(), alta);
    }
    
    @Transactional(readOnly = true)
    public Cliente buscarClientePorDocumento(Long documento){
        try{
            Cliente c = clienteRepositorio.buscarPorDocumento(documento);

            return c;
        }catch(Exception e){
            return null;
        }
    }
    
    private void validar(String nombre, String apellido, String telefono, Long documento) throws Exception{
        if(nombre == null || nombre.isEmpty()){
            throw new Exception("No se puede dejar el nombre en blanco.");
        }
        if(apellido == null || apellido.isEmpty()){
            throw new Exception("No se puede dejar el apellido en blanco.");
        }
        if(telefono == null || telefono.isEmpty()){
            throw new Exception("No se puede dejar el telefono en blanco.");
        }
    }
    
}
