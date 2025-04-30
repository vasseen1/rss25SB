package fr.univrouen.rss25SB.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Link {

    @XmlAttribute
    private String rel;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String href;

    public Link() {}

    public Link(String rel, String type, String href) {
        this.rel = rel;
        this.type = type;
        this.href = href;
    }
}
