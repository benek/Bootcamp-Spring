# Bootcamp-Spring

Ejemplo simple de un Hello World utilizando Spring Core sin el uso de Spring Boot.

En este ejemplo solo hacemos uso de:
* Un pom descriptor con la dependencia a spring-context
* Un POJO `HolaMundo.java` con un método que imprime a consola
* Una _configuration class_ `AppConfig.java` con un método que produce un bean
* Una clase Main que crea un `ApplicationContext` y obtene el bean `holaMundo` del contenedor DI
