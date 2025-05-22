package fr.univrouen.rss25SB.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import fr.univrouen.rss25SB.services.XmlService;
import fr.univrouen.rss25SB.entities.Item;

@RestController
public class XMLController {

    private final XmlService xmlService;

    public XMLController(XmlService xmlService) {
        this.xmlService = xmlService;
    }

    @GetMapping(value = "/rss25SB/resume/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> resumeXML() throws Exception {
        List<Item> items = xmlService.resume();
        String xmlContent = xmlService.generateXMLFromItems(items);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                .body(xmlContent);
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
