package fr.univrouen.rss25SB.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.univrouen.rss25SB.services.HtmlService;
import fr.univrouen.rss25SB.entities.Item;

@RestController
public class HtmlController {

    private final HtmlService HtmlService;

    public HtmlController(HtmlService HtmlService) {
        this.HtmlService = HtmlService;
    }

    // Récupère l'intégralité des articles et les affiche sous la forme d'une page html
    @GetMapping(value = "/rss25SB/resume/html", produces = "text/html")
    public String resumeHtml() throws Exception {
        List<Item> items = HtmlService.resume();
        return HtmlService.generateHtmlFromItems(items);
    }

    // Récupère l'article d'id id et le renvoie sous la forme d'une page html
    // Si l'article n'est pas toruvé, un message d'erreur est retourné.
    @GetMapping(value = "/rss25SB/html/{id}", produces = "text/html")
    public String resumeByIdHtml(@PathVariable int id) throws Exception {
        Optional<Item> item = HtmlService.resumeById((long) id);
        if (item.isPresent()) {
            return HtmlService.generateHtmlFromItems(List.of(item.get()));
        } else {
            return "<html><head><title>Article invisible</title></head><body><h1>Article non trouvé pour l'id : " + id + "</h1></body></html>";
        }
    }

}

