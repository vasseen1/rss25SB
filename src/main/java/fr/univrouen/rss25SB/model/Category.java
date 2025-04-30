package fr.univrouen.rss25SB.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Category {

    @XmlAttribute
    private String term;

    public Category() {}

    public Category(String term) {
        this.term = term;
    }
}
