package fr.univrouen.rss25SB.entities;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String guid;

    @Column(length = 128, nullable = false)
    private String title;

    private OffsetDateTime published;

    private OffsetDateTime updated;

    @Column(name = "content_type", nullable = false, length = 10)
    private String contentType; 

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
    
    @Column(name = "category")
    private String category;

    @Column(name = "authorName")
    private String authorName;

    @Column(name = "authorMail")
    private String authorMail;

    @Column(name = "authorUri")
    private String authorUri;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OffsetDateTime getPublished() {
        return published;
    }

    public void setPublished(OffsetDateTime published) {
        this.published = published;
    }

    public OffsetDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(OffsetDateTime updated) {
        this.updated = updated;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentSrc() {
        return contentSrc;
    }

    public void setContentSrc(String contentSrc) {
        this.contentSrc = contentSrc;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    public Integer getImageLength() {
        return imageLength;
    }

    public void setImageLength(Integer imageLength) {
        this.imageLength = imageLength;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String name) {
        this.authorName = name;
    }

    public String getAuthorMail() {
        return authorMail;
    }

    public void setAuthorMail(String mail) {
        this.authorMail = mail;
    }

    public String getAuthorUri() {
        return authorUri;
    }


    public void setAuthorUri(String uri) {
        this.authorUri = uri;
    }
    
}

