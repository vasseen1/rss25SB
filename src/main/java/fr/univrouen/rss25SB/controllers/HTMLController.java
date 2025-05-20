package fr.univrouen.rss25SB.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.univrouen.rss25SB.services.HtmlService;


@RestController
public class HTMLController {

    private final HtmlService HtmlService;

    public HTMLController(HtmlService HtmlService) {
        this.HtmlService = HtmlService;
    }

    

    @GetMapping("/rss25SB/resume/html")
    public String resume() {
        HtmlService.resume();
        return "ok";
    }
    @GetMapping("/rss25SB/html/{id}")
    public String id(@PathVariable int id) {
        HtmlService.resumeById(id);
        return "L'id récupéré est : " + id;
    }
    
}
