package fr.univrouen.rss25SB.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.univrouen.rss25SB.entities.Item;
import fr.univrouen.rss25SB.services.HtmlService;


@RestController
public class HTMLController {

    private final HtmlService htmlService;

    public HTMLController(HtmlService HtmlService) {
        this.htmlService = HtmlService;
    }

    

    // Récupère tous les articles
    @GetMapping("/rss25SB/resume/html")
    public List<Item> resume() {
        return htmlService.resume();
    }

    // Récupère un article par ID
    @GetMapping("/rss25SB/html/{id}")
    public Object resumeById(@PathVariable int id) {
        Optional<Item> item = htmlService.resumeById((long) id);
        if (item.isPresent()) {
            return item.get();
        } else {
            return "Article non trouvé pour l'id : " + id;
        }
    }
    
}
