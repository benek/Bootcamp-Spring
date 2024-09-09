package org.benek.bootcamp.springbootdemo;

import org.benek.bootcamp.springbootdemo.model.Cliente;
import org.benek.bootcamp.springbootdemo.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootdemoApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringbootdemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringbootdemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ClienteRepository clienteRepository) {
		return (args) -> {
			clienteRepository.save(new Cliente("Javier", "Ramirez"));
			clienteRepository.save(new Cliente("Emily", "Chacon"));
			clienteRepository.save(new Cliente("Esteban", "Muñoz"));
			clienteRepository.save(new Cliente("Jesus", "Garcia"));
			clienteRepository.save(new Cliente("Freddy", "Alcarazo"));
			clienteRepository.save(new Cliente("Oscar", "Chacon"));

			log.info("Consultando clientes con findAll()");
			clienteRepository.findAll().forEach(cliente -> {
				log.info(cliente.toString());
			});
			log.info("");

			log.info("Consultando un cliente con ID 1");
			Cliente cliente = clienteRepository.findById(1L);
			log.info(cliente.toString());

			log.info("Consultar clientes con apellido Chacon");
			clienteRepository.findByApellido("Chacon").forEach(chacon -> {
				log.info(chacon.toString());
			});
		};
	}

}
