package org.benek.bootcamp.restservice.repository;

import org.benek.bootcamp.restservice.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Los "repositorios" JPA son interfaces con metodos que soportan operaciones
 * hacia el almacen de datos, comunmente Create, Read, Update, Delete.
 *
 * Spring Data realiza la implementacion basada en convenciones de acuerdo al
 * nombre de los metodos de las interfaces repository.
 */
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}
