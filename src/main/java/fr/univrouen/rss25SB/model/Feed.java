package fr.univrouen.rss25SB.model;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "feed")
@XmlAccessorType(XmlAccessType.FIELD)
public class Feed {

    @XmlAttribute
    private String lang;

    @XmlAttribute
    private String version;

    @XmlElement
    private String title;

    @XmlElement
    private String pubDate;

    @XmlElement
    private String copyright;

    @XmlElement(name = "link")
    private List<Link> links;

    @XmlElement(name = "item")
    private List<Item> items;

    public Feed() {}

    public Feed(String lang, String version, String title, String pubDate, String copyright, List<Link> links, List<Item> items) {
        this.lang = lang;
        this.version = version;
        this.title = title;
        this.pubDate = pubDate;
        this.copyright = copyright;
        this.links = links;
        this.items = items;
    }
}
