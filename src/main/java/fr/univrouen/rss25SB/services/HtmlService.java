package fr.univrouen.rss25SB.services;

import fr.univrouen.rss25SB.entities.Item;
import fr.univrouen.rss25SB.repositories.ItemRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

@Service
public class HtmlService {

    private final ItemRepository itemRepository;

    public HtmlService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> resume() {
        return itemRepository.findAll();
    }

    public Optional<Item> resumeById(long id) {
        return itemRepository.findById(id);
    }

    public String generateHtmlFromItems(List<Item> items) throws Exception {
        // Initialiser le document XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        // Création de la racine <feed>
        Element feed = doc.createElementNS("http://univ.fr/rss25", "rss:feed");
        doc.appendChild(feed);

        for (Item item : items) {
            Element itemElement = doc.createElementNS("http://univ.fr/rss25", "rss:item");

            createElementWithText(doc, itemElement, "rss:title",
                    Optional.ofNullable(item.getTitle()).orElse(""));

            createElementWithText(doc, itemElement, "rss:published",
                    Optional.ofNullable(item.getPublished()).map(Object::toString).orElse(""));

            createElementWithText(doc, itemElement, "rss:content",
                    Optional.ofNullable(item.getContentSrc()).orElse(""));

            createElementWithText(doc, itemElement, "rss:guid",
                    String.valueOf(item.getId()));

            if (item.getCategory() != null) {
                Element category = doc.createElementNS("http://univ.fr/rss25", "rss:category");
                category.setAttribute("term", item.getCategory());
                itemElement.appendChild(category);
            }

            if (item.getAuthorName() != null) {
                Element author = doc.createElementNS("http://univ.fr/rss25", "rss:author");
                Element name = doc.createElementNS("http://univ.fr/rss25", "rss:name");
                name.setTextContent(item.getAuthorName());
                author.appendChild(name);
                itemElement.appendChild(author);
            }

            feed.appendChild(itemElement);
        }

        // Appliquer la transformation XSLT
        TransformerFactory transformerFactory = new net.sf.saxon.TransformerFactoryImpl();
        Source xslt = new StreamSource(new File("src/main/resources/xml/rss25.tp4.xslt"));
        Transformer transformer = transformerFactory.newTransformer(xslt);

        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));

        return writer.toString();
    }

    private void createElementWithText(Document doc, Element parent, String tagName, String textContent) {
        if (textContent != null && !textContent.isEmpty()) {
            Element elem = doc.createElementNS("http://univ.fr/rss25", tagName);
            elem.setTextContent(textContent);
            parent.appendChild(elem);
        }
    }
}
