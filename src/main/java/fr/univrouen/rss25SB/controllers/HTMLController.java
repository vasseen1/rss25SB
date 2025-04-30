package main.java.fr.univrouen.rss25SB.controllers;

@RestController
public class HTMLController {

    @GetMapping("/rss25SB/resume/html")
    public String resume() {
        return "travaux resume html";
    }
    @GetMapping("/rss25SB/html/id")
    public String id() {
        return "travaux id html";
    }
    
}
