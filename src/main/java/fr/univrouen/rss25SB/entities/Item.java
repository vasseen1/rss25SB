package fr.univrouen.rss25SB.entities;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID guid;

    @Column(length = 128, nullable = false)
    private String title;

    private OffsetDateTime published;

    private OffsetDateTime updated;

    @Column(name = "content_type", nullable = false, length = 10)
    private String contentType; // "text" ou "html"

    @Column(name = "content_src")
    private String contentSrc;

    // Image attributes
    @Column(name = "image_type", length = 50)
    private String imageType;

    @Column(name = "image_href")
    private String imageHref;

    @Column(name = "image_alt", length = 255)
    private String imageAlt;

    @Column(name = "image_length")
    private Integer imageLength;

    // Relations
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Author> authors;

    // getters & setters
}

