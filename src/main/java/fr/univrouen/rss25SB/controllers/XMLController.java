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

    @GetMapping(value = "/rss25SB/xml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> resumeById(@PathVariable long id) throws Exception {
        Optional<Item> item = xmlService.resumeById(id);
        String xml;

        if (item.isPresent()) {
            xml = xmlService.generateXMLFromItems(List.of(item.get()));
        } else {
            xml = xmlService.generateErrorXML(id);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                .body(xml);
    }

}
