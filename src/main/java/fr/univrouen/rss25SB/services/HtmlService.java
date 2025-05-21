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

    // renvoie la liste des articles présent dans la bdd
    public List<Item> resume() {
        return itemRepository.findAll();
    }

    // Renvoie l'article associée à l'id dans la bdd
    public Optional<Item> resumeById(long id) {
        return itemRepository.findById(id);
    }

    // Genère un code xml des articles présents dans la liste entrée en paramêtre
    // et génère un code html à partir du code xml généré et du fichier 
    // src/main/resources/xml/rss25.tp4.xslt.

    public String generateHtmlFromItems(List<Item> items) throws Exception {
        // Initialiser le document XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        // Création de la racine <feed>
        Element feed = doc.createElementNS("http://univ.fr/rss25", "rss:feed");
        feed.setAttribute("xmlns:rss", "http://univ.fr/rss25");  // <-- clé !
        doc.appendChild(feed);


        for (Item item : items) {
            Element itemElement = doc.createElementNS("http://univ.fr/rss25", "rss:item");

            // Ajout du titre
            createElementWithText(doc, itemElement, "rss:title",
                    Optional.ofNullable(item.getTitle()).orElse(""));

            // Ajout de la date de publication
            createElementWithText(doc, itemElement, "rss:published",
                    Optional.ofNullable(item.getPublished()).map(Object::toString).orElse(""));

            // Ajout du contenu
            createElementWithText(doc, itemElement, "rss:content",
                    Optional.ofNullable(item.getContentSrc()).orElse(""));

            // Ajout du GUID
            createElementWithText(doc, itemElement, "rss:guid", item.getGuid());

            // Si la catégorie n'est pas null, ajout de la catégorie
            if (item.getCategory() != null) {
                Element category = doc.createElementNS("http://univ.fr/rss25", "rss:category");
                category.setAttribute("term", item.getCategory());
                itemElement.appendChild(category);
            }

            // Si une image est présente, ajout de l'image.
            if (item.getImageHref() != null) {
                Element image = doc.createElementNS("http://univ.fr/rss25", "rss:image");
                image.setAttribute("href", item.getImageHref());
                image.setAttribute("alt", Optional.ofNullable(item.getImageAlt()).orElse("Image"));
                itemElement.appendChild(image);
            }
            

            // Si un auteur est assodiée, ajout des infos de l'auteur.
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

        // Une fois le flux xml généré, on applique la transformation du fichier xslt.
        TransformerFactory transformerFactory = new net.sf.saxon.TransformerFactoryImpl();
        Source xslt = new StreamSource(new File("src/main/resources/xml/rss25.tp4.xslt"));
        Transformer transformer = transformerFactory.newTransformer(xslt);

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
}
