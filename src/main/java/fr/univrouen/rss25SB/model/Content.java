package fr.univrouen.rss25SB.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Content {

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String src;

    @XmlValue
    private String value;

    public Content() {}

    public Content(String type, String src, String value) {
        this.type = type;
        this.src = src;
        this.value = value;
    }
}

