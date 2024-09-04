package org.benek;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        HolaMundo holaMundo = context.getBean(HolaMundo.class);
        holaMundo.saludar();
    }
}
