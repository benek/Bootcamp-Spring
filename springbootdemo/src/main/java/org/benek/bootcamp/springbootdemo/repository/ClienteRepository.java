package org.benek.bootcamp.springbootdemo.repository;

import org.benek.bootcamp.springbootdemo.model.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

    Cliente findById(long id);

    List<Cliente> findByApellido(String apellido);

}
