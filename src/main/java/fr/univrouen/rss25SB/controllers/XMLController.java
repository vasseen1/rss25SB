package fr.univrouen.rss25SB.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.univrouen.rss25SB.services.XmlService;
import fr.univrouen.rss25SB.entities.Item;

@RestController
public class XMLController {

    private final XmlService xmlService;

    public XMLController(XmlService xmlService) {
        this.xmlService = xmlService;
    }

    // Récupère l'intégralité des articles et les affiche sous la forme d'une page html
    @GetMapping("/rss25SB/resume/xml")
    public List<Item> resume() {
        return xmlService.resume();
    }

    // Récupère l'article d'id id et le renvoie sous la forme d'une page html
    // Si l'article n'est pas toruvé, un message d'erreur est retourné.
    @GetMapping("/rss25SB/xml/{id}")
    public Object resumeById(@PathVariable int id) {
        Optional<Item> item = xmlService.resumeById((long) id);
        if (item.isPresent()) {
            return item.get();
        } else {
            return "Article non trouvé pour l'id : " + id;
        }
    }
}
