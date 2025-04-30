package fr.univrouen.rss25SB.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Image {

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String href;

    @XmlAttribute
    private String alt;

    @XmlAttribute
    private Integer length;

    public Image() {}

    public Image(String type, String href, String alt, Integer length) {
        this.type = type;
        this.href = href;
        this.alt = alt;
        this.length = length;
    }
}

