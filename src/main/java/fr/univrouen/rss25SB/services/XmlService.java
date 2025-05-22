package fr.univrouen.rss25SB.services;

import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;

import fr.univrouen.rss25SB.entities.Item;
import fr.univrouen.rss25SB.repositories.ItemRepository;

@Service
public class XmlService {

    private final ItemRepository itemRepository;

    public XmlService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Récupère les articles présent dans la base et les affiche
    public List<Item> resume() {
        return itemRepository.findAll();
    }

    // Récupère l'article dont l'id est id.
    public Optional<Item> resumeById(long id) {
        return itemRepository.findById(id);
    }

        // Genère un code xml des articles présents dans la liste entrée en paramêtre
    // et génère un code html à partir du code xml généré et du fichier 
    // src/main/resources/xml/rss25.tp4.xslt.

    public String generateXMLFromItems(List<Item> items) throws Exception {
    // Initialiser le document XML
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.newDocument();

    // Création de la racine <feed>
    Element feed = doc.createElementNS("http://univ.fr/rss25", "rss:feed");
    feed.setAttribute("xmlns:rss", "http://univ.fr/rss25");
    doc.appendChild(feed);

    for (Item item : items) {
        Element itemElement = doc.createElementNS("http://univ.fr/rss25", "rss:item");

        createElementWithText(doc, itemElement, "rss:title", Optional.ofNullable(item.getTitle()).orElse(""));
        createElementWithText(doc, itemElement, "rss:published", Optional.ofNullable(item.getPublished()).map(Object::toString).orElse(""));
        createElementWithText(doc, itemElement, "rss:content", Optional.ofNullable(item.getContentSrc()).orElse(""));
        createElementWithText(doc, itemElement, "rss:guid", item.getGuid());

        if (item.getCategory() != null) {
            Element category = doc.createElementNS("http://univ.fr/rss25", "rss:category");
            category.setAttribute("term", item.getCategory());
            itemElement.appendChild(category);
        }

        if (item.getImageHref() != null) {
            Element image = doc.createElementNS("http://univ.fr/rss25", "rss:image");
            image.setAttribute("href", item.getImageHref());
            image.setAttribute("alt", Optional.ofNullable(item.getImageAlt()).orElse("Image"));
            itemElement.appendChild(image);
        }

        if (item.getAuthorName() != null || item.getAuthorMail() != null || item.getAuthorUri() != null) {
            Element author = doc.createElementNS("http://univ.fr/rss25", "rss:author");

            if (item.getAuthorName() != null) {
                Element name = doc.createElementNS("http://univ.fr/rss25", "rss:name");
                name.setTextContent(item.getAuthorName());
                author.appendChild(name);
            }

            if (item.getAuthorMail() != null) {
                Element email = doc.createElementNS("http://univ.fr/rss25", "rss:email");
                email.setTextContent(item.getAuthorMail());
                author.appendChild(email);
            }

            if (item.getAuthorUri() != null) {
                Element uri = doc.createElementNS("http://univ.fr/rss25", "rss:uri");
                uri.setTextContent(item.getAuthorUri());
                author.appendChild(uri);
            }

            itemElement.appendChild(author);
        }

        feed.appendChild(itemElement);
    }

    // Sérialiser directement le document XML sans transformation XSLT
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

    StringWriter writer = new StringWriter();
    transformer.transform(new DOMSource(doc), new StreamResult(writer));

    return writer.toString();
}


    // Méthode utilisée dans generateHtmlFromItems pour crée des nodes.
    private void createElementWithText(Document doc, Element parent, String tagName, String textContent) {
        if (textContent != null && !textContent.trim().isEmpty()) {
            Element elem = doc.createElementNS("http://univ.fr/rss25", tagName);
            elem.setTextContent(textContent);
            parent.appendChild(elem);
        }
    }

    public String generateErrorXML(long id) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
    
        Element error = doc.createElementNS("http://univ.fr/rss25", "rss:error");
        error.setAttribute("xmlns:rss", "http://univ.fr/rss25");
        doc.appendChild(error);
    
        createElementWithText(doc, error, "rss:id", String.valueOf(id));
        createElementWithText(doc, error, "rss:status", "ERROR");
    
        return serializeDocumentToString(doc);
    }

    private String serializeDocumentToString(Document doc) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }
    
    
}
