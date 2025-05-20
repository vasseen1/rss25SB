package fr.univrouen.rss25SB.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import fr.univrouen.rss25SB.services.XmlService;


@RestController
public class XMLController {

    private XmlService xmlService = new XmlService();
    
    
    @GetMapping("/rss25SB/resume/xml")
    public String resume() {
        xmlService.resume();
        return "ok";
    }

    @GetMapping("/rss25SB/xml/{id}")
    public String id(@PathVariable int id) {
        xmlService.resumeById(id);
        return "L'id récupéré est : " + id;
    }
}
