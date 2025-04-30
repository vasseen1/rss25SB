package main.java.fr.univrouen.rss25SB.controllers;

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
