package org.benek.bootcamp.springmvcdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @PostMapping("/iniciarSesion") //Mapeo de URI proveniente por HTTP POST
    public String login(@RequestParam String usuario, //Obtenemos query params presentes en el request
                        @RequestParam String password, Model model) {

        /* Simula logica para validacion de usuario y password */
        if (!usuario.isBlank() || !password.isBlank()) {
            //Agregamos atributos que queremos esten presentes en la siguiente view (dashboard)
            model.addAttribute("usuario", usuario);
        } else {
            //Agregamos atributos que queremos esten presentes en la siguiente view (error)
            model.addAttribute("mensajeError",
                    "Debes ingresar usuario y contraseña");
            return "error"; //ViewResolver busca la view error.html
        }
        return "dashboard"; //ViewResolver busca la view dashboard.html
    }
}
