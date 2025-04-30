package fr.univrouen.rss25SB.model;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

    @XmlAttribute
    private String guid;

    @XmlElement
    private String title;

    @XmlElement
    private String published;

    @XmlElement(name = "category")
    private List<Category> categories;

    @XmlElement
    private Content content;

    @XmlElement
    private Image image;

    @XmlElement(name = "author")
    private List<Author> authors;

    public Item() {}

    public Item(String guid, String title, String published, List<Category> categories, Content content, Image image, List<Author> authors) {
        this.guid = guid;
        this.title = title;
        this.published = published;
        this.categories = categories;
        this.content = content;
        this.image = image;
        this.authors = authors;
    }
}
