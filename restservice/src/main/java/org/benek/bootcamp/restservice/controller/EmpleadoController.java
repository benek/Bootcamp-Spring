package org.benek.bootcamp.restservice.controller;

import org.benek.bootcamp.restservice.model.Empleado;
import org.benek.bootcamp.restservice.repository.EmpleadoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController()
public class EmpleadoController {
    private final EmpleadoRepository empleadoRepository;
    public EmpleadoController(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    /* Lista todos los empleados */
    @GetMapping("/empleados")
    public ResponseEntity<List<Empleado>> listAll() {
        return ResponseEntity.ok(empleadoRepository.findAll());
    }

    /* Crea un empleado a partir de un objeto JSON presente en el request */
    @PostMapping("/empleados")
    public ResponseEntity<Empleado> createEmpleado(@RequestBody Empleado nuevoEmpleado,
                                                   UriComponentsBuilder ucb) {
        Empleado empleadoGuardado = empleadoRepository.save(nuevoEmpleado);
        URI uriEmpleado = ucb
                .path("empleados/{id}")
                .buildAndExpand(empleadoGuardado.getId())
                .toUri();
        return ResponseEntity.created(uriEmpleado).build();
    }

    /* Obtiene un empleado por medio de su ID */
    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> findById(@PathVariable Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        if (empleado.isPresent()) {
            return ResponseEntity.ok(empleado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<?> updateEmpleado(@RequestBody Empleado empleadoActualizado, @PathVariable Long id) {
        return ResponseEntity.ok(empleadoRepository.findById(id)
                .map(empleado -> {
                    empleado.setNombre(empleadoActualizado.getNombre());
                    empleado.setPuesto(empleadoActualizado.getPuesto());
                    empleadoRepository.save(empleado);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> {
                    empleadoRepository.save(empleadoActualizado);
                    return ResponseEntity.ok(empleadoActualizado);
                }));
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Long id) {
        empleadoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
