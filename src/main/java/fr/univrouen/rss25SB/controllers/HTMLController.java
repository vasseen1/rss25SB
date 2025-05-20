package fr.univrouen.rss25SB.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.univrouen.rss25SB.services.HtmlService;
import fr.univrouen.rss25SB.entities.Item;

@RestController
public class HTMLController {

    private final HtmlService HTMLService;

    public HTMLController(HtmlService HTMLService) {
        this.HTMLService = HTMLService;
    }

    // Récupère tous les articles
    @GetMapping("/rss25SB/resume/HTML")
    public List<Item> resume() {
        return HTMLService.resume();
    }

    // Récupère un article par ID
    @GetMapping("/rss25SB/HTML/{id}")
    public Object resumeById(@PathVariable int id) {
        Optional<Item> item = HTMLService.resumeById((long) id);
        if (item.isPresent()) {
            return item.get();
        } else {
            return "Article non trouvé pour l'id : " + id;
        }
    }
}

