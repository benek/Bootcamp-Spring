package org.benek.bootcamp.restservice.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;
import org.benek.bootcamp.restservice.model.Empleado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmpleadoControllerTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnAllEmpleadosWhenListIsRequested() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/empleados", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int empleadosCount = documentContext.read("$.length()");
        assertThat(empleadosCount).isEqualTo(3);

        JSONArray ids = documentContext.read("$..id");
        assertThat(ids).containsExactlyInAnyOrder(1, 2, 3);

        JSONArray nombres = documentContext.read("$..nombre");
        assertThat(nombres).containsExactlyInAnyOrder(
                "Javier Ramirez", "Blanca Pardo", "Juan Rodriguez");
    }

    @Test
    void shouldReturnAnEmpleadoById() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/empleados/3", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(3);

        String nombre = documentContext.read("$.nombre");
        assertThat(nombre).isEqualTo("Blanca Pardo");

        String puesto = documentContext.read("$.puesto");
        assertThat(puesto).isEqualTo("Project Manager");
    }

    @Test
    void shouldNotReturnAnEmpleadoWithAnUnknownId() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("/empleados/9999", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }

    @Test
    @DirtiesContext
    void shouldCreateANewEmpleado() {
        Empleado empleado = new Empleado("Jose Arellano", "Intern");
        ResponseEntity<Void> response = restTemplate
                .postForEntity("/empleados", empleado, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = response.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity(location, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        String nombre = documentContext.read("$.nombre");
        String puesto = documentContext.read("$.puesto");

        assertThat(id).isNotNull();
        assertThat(nombre).isEqualTo("Jose Arellano");
        assertThat(puesto).isEqualTo("Intern");
    }

    @Test
    @DirtiesContext
    void shouldUpdateAnExistingEmpleado() {
        Empleado empleado = new Empleado("Javier Ramirez", "President");
        HttpEntity<Empleado> request = new HttpEntity<>(empleado);

        ResponseEntity<Void> response = restTemplate
                .exchange("/empleados/1", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/empleados/1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number id = documentContext.read("$.id");
        String nombre = documentContext.read("$.nombre");
        String puesto = documentContext.read("$.puesto");

        assertThat(id).isEqualTo(1);
        assertThat(nombre).isEqualTo("Javier Ramirez");
        assertThat(puesto).isEqualTo("President");
    }

    @Test
    @DirtiesContext
    void shouldDeleteAnEmpleadoById() {
        ResponseEntity<Void> response = restTemplate
                .exchange("/empleados/3", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/empleados/3", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
