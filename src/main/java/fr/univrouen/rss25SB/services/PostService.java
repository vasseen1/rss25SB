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

import java.util.Optional;
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

    // Supprime l'article dont l'id est id.
    // S'il existe et est supprimée, alors renvoie true,
    // Sinon, renvoie false.
    public boolean delete(long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Méthode qui vérifie que le fichier xml soit conforme au fichier xsd 
    // src/main/resources/xml/rss25.tp.xsd
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

    // Méthode d'insertion des articles dans la table.
    // Cette méthode va appeler valideXMLSchema. si le fichier 
    // n'est pas conforme, elle renvoie -1.
    // Si l'article en cours d'insertion existe déja 
    // dans la base de données, elle renvoie -2.
    // Si une erreur survient, elle renvoie -3.
    // Enfin, si tout ce passe bien, elle renvoie l'id du dernier 
    // élément insérée.
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
    
                // Guid et titre
                String guid = itemElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "guid").item(0).getTextContent();
                String title = itemElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "title").item(0).getTextContent();
    
                // Vérifier si l'item existe déjà
                Optional<Item> exists = itemRepository.findByGuid(guid);
                if (exists.isPresent()) {
                    return -2;
                }
    
                // Category (attribut "term")
                Element categoryElem = (Element) itemElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "category").item(0);
                String categoryTerm = null;
                if (categoryElem != null) {
                    categoryTerm = categoryElem.getAttribute("term");
                }
    
                // Dates
                String publishedStr = getTextContentIfExists(itemElem, "published");
                String updatedStr = getTextContentIfExists(itemElem, "updated");
    
                OffsetDateTime published = (publishedStr != null) ? OffsetDateTime.parse(publishedStr) : null;
                OffsetDateTime updated = (updatedStr != null) ? OffsetDateTime.parse(updatedStr) : null;
    
                // Création Item
                Item item = new Item();
                item.setGuid(guid);
                item.setTitle(title);
                item.setCategory(categoryTerm);
                item.setPublished(published);
                item.setUpdated(updated);
    
                // Content
                Element contentElem = (Element) itemElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "content").item(0);
                if (contentElem != null) {
                    String type = contentElem.getAttribute("type");
                    item.setContentType((type != null && !type.isEmpty()) ? type : "text");
                    item.setContentSrc(contentElem.getTextContent());
                } else {
                    item.setContentType("text");
                }
    
                // Image
                Element imageElem = (Element) itemElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "image").item(0);
                if (imageElem != null) {
                    item.setImageType(imageElem.getAttribute("type"));
                    item.setImageHref(imageElem.getAttribute("href"));
                    item.setImageAlt(imageElem.getAttribute("alt"));
                    String lengthAttr = imageElem.getAttribute("length");
                    if (lengthAttr != null && !lengthAttr.isEmpty()) {
                        try {
                            item.setImageLength(Integer.parseInt(lengthAttr));
                        } catch (NumberFormatException e) {
                            // Ignore or log le problème
                        }
                    }
                }
    
                // Auteur ou contributeur (priorité à author)
                Element authorElem = (Element) itemElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "author").item(0);
                Element contributorElem = (Element) itemElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "contributor").item(0);
    
                if (authorElem != null) {
                    Element nameElem = (Element) authorElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "name").item(0);
                    if (nameElem != null) {
                        item.setAuthorName(nameElem.getTextContent().trim());
                    }
                    Element mailElem = (Element) authorElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "email").item(0);
                    if (mailElem != null) {
                        item.setAuthorMail(mailElem.getTextContent().trim());
                    }
                    Element uriElem = (Element) authorElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "uri").item(0);
                    if (uriElem != null) {
                        item.setAuthorUri(uriElem.getTextContent().trim());
                    }
                } else if (contributorElem != null) {
                    Element nameElem = (Element) contributorElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "name").item(0);
                    if (nameElem != null) {
                        item.setAuthorName(nameElem.getTextContent().trim());
                    }
                    Element mailElem = (Element) contributorElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "email").item(0);
                    if (mailElem != null) {
                        item.setAuthorMail(mailElem.getTextContent().trim());
                    }
                    Element uriElem = (Element) contributorElem.getElementsByTagNameNS("http://univrouen.fr/rss25", "uri").item(0);
                    if (uriElem != null) {
                        item.setAuthorUri(uriElem.getTextContent().trim());
                    }
                }
    
                // Sauvegarde
                Item savedItem = itemRepository.save(item);
                lastInsertedId = savedItem.getId();
            }
    
            return (int) lastInsertedId;
    
        } catch (Exception e) {
            e.printStackTrace();
            return -3;
        }
    }
    
    // Méthode qui extrait le texte d'un élément s'il existe.
    private String getTextContentIfExists(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagNameNS("http://univrouen.fr/rss25", tagName);
        if (list.getLength() > 0) {
            return list.item(0).getTextContent();
        }
        return null;
    }
    
}
