package fr.univrouen.rss25SB.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Author {

    @XmlElement
    private String name;

    @XmlElement
    private String email;

    @XmlElement
    private String uri;

    public Author() {}

    public Author(String name, String email, String uri) {
        this.name = name;
        this.email = email;
        this.uri = uri;
    }
}
