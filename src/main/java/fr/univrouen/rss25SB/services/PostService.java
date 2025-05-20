package fr.univrouen.rss25SB.services;


import java.io.StringReader;
import java.time.OffsetDateTime;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import org.w3c.dom.*;
import fr.univrouen.rss25SB.entities.Item;
import fr.univrouen.rss25SB.repositories.ItemRepository;

@Service
public class PostService {

    private final ItemRepository itemRepository;

    @Autowired
    public PostService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public boolean delete(long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean validateXMLSchema(String xsdPath, String xml) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(getClass().getResourceAsStream(xsdPath)));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new java.io.StringReader(xml)));
            return true;
        } catch (Exception e) {
            System.err.println("Erreur de validation XSD: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public int insertRssFeed(String rssXml) {
        if (!validateXMLSchema("/xml/rss25.tp.xsd", rssXml)) {
            return -1;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(rssXml)));

            NodeList itemNodes = doc.getElementsByTagNameNS("http://univrouen.fr/rss25", "item");

            long lastInsertedId = -1;

            for (int i = 0; i < itemNodes.getLength(); i++) {
                Element itemElem = (Element) itemNodes.item(i);
            
                String title = itemElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "title").item(0).getTextContent();
                String dateStr = null;
                if (itemElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "published").getLength() > 0) {
                    dateStr = itemElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "published").item(0).getTextContent();
                } else if (itemElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "updated").getLength() > 0) {
                    dateStr = itemElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "updated").item(0).getTextContent();
                }
            
                // Parse la date si elle existe, sinon laisse null
                OffsetDateTime date = null;
                if (dateStr != null) {
                    date = OffsetDateTime.parse(dateStr);
                }
            
                // Vérifie que la date n'est pas nulle avant de faire la requête
                boolean exists = false;
                if (date != null) {
                    exists = itemRepository.existsByTitleAndPublished(title, date);
                } else {
                    // Si tu veux gérer le cas où il n'y a pas de date, tu peux vérifier juste par title
                    exists = itemRepository.existsByTitle(title);
                }
            
                if (!exists) {
                    Item item = new Item();
                    item.setTitle(title);
                    item.setPublished(date);
                    Item savedItem = itemRepository.save(item);
                    lastInsertedId = savedItem.getId();
                } else {
                    return -2; // l'item existe déjà
                }
            }
            

            return (int) lastInsertedId;

        } catch (Exception e) {
            e.printStackTrace();
            return -3;
        }
    }
    
}
