package com.ninja_squad.poneyserver.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Redirects to /swagger-ui.html
 * @author JB Nizet
 */
@Controller
@RequestMapping({"/swagger", "/swagger/"})
public class Swagger {
    @RequestMapping
    public String showDocumentation() {
        return "redirect:/swagger-ui.html";
    }
}
