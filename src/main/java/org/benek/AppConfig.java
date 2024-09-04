package org.benek;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public HolaMundo holaMundo() {
        return new HolaMundo();
    }

}
