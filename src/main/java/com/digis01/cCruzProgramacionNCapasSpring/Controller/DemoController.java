/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis01.cCruzProgramacionNCapasSpring.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("demo")
public class DemoController {

    @GetMapping("HolaMundo")
    public String HolaMundo() {
        return "HolaMundo";
    }

    @GetMapping("/persona/{persona}")
    public String Persona(@PathVariable String persona, Model model) {

        model.addAttribute("persona", persona); //Para invocarlo es "Persona", para darle valor el segundo parametro

        return "HolaMundo";
    }

    @GetMapping("saludito") //Este controlador trae la vista del saludo con Request Paaram
    public String Saludito() {
        return "saludito";
    }

    @PostMapping("/nombre")
    public String saludoWithRequestParam(@RequestParam String nombre, Model model) {

        model.addAttribute("persona", nombre);
        return "HolaMundo";
    }

}
