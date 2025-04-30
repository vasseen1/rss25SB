package fr.univrouen.rss25SB.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class XMLController {
    
    @GetMapping("/rss25SB/resume/xml")
    public String resume() {
        return "Travaux resume XML";
    }

    @GetMapping("/rss25SB/xml/id")
    public String id() {
        return "Travaux XML ID";
    }
}
