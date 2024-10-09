package org.benek.bootcamp.restservice.init;

import org.benek.bootcamp.restservice.model.Empleado;
import org.benek.bootcamp.restservice.repository.EmpleadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmpleadoRepository empleadoRepository) {
        return args -> {
            if (empleadoRepository.count() == 0) {
                Empleado javier = new Empleado("Javier Ramirez", "CTO");
                Empleado juan = new Empleado("Juan Rodriguez", "CEO");
                Empleado blanca = new Empleado("Blanca Pardo", "Project Manager");

                log.info("Carga inicial: {}", empleadoRepository.save(javier));
                log.info("Carga inicial: {}", empleadoRepository.save(juan));
                log.info("Carga inicial: {}", empleadoRepository.save(blanca));
            }
        };
    }

}
