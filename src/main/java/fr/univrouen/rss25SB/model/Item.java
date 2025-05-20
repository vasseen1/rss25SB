package fr.univrouen.rss25SB.model;

import java.time.OffsetDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class Item {

    @Id
    private String guid;

    @Column(length = 128, nullable = false)
    private String title;

    @ElementCollection
@CollectionTable(
    name = "item_category",
    joinColumns = {
        @JoinColumn(name = "item_guid", referencedColumnName = "guid")
    }
)
@Column(name = "term")
private List<String> categories;

    private OffsetDateTime published; // ou updated (on choisit l’un des deux)

    // IMAGE
    private String imageType;
    private String imageHref;
    private String imageAlt;
    private Integer imageLength;

    // CONTENT
    private String contentType;
    private String contentSrc;

    // AUTHOR (simplifié à un seul auteur pour commencer)
    private String authorName;
    private String authorEmail;
    private String authorUri;

    // Getters/setters
    public String getGuid() { return guid; }
    public void setGuid(String guid) { this.guid = guid; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }

    public OffsetDateTime getPublished() { return published; }
    public void setPublished(OffsetDateTime published) { this.published = published; }

    public String getImageType() { return imageType; }
    public void setImageType(String imageType) { this.imageType = imageType; }

    public String getImageHref() { return imageHref; }
    public void setImageHref(String imageHref) { this.imageHref = imageHref; }

    public String getImageAlt() { return imageAlt; }
    public void setImageAlt(String imageAlt) { this.imageAlt = imageAlt; }

    public Integer getImageLength() { return imageLength; }
    public void setImageLength(Integer imageLength) { this.imageLength = imageLength; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public String getContentSrc() { return contentSrc; }
    public void setContentSrc(String contentSrc) { this.contentSrc = contentSrc; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getAuthorEmail() { return authorEmail; }
    public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }

    public String getAuthorUri() { return authorUri; }
    public void setAuthorUri(String authorUri) { this.authorUri = authorUri; }
}
