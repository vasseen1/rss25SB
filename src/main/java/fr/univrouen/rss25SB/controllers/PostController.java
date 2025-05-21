package fr.univrouen.rss25SB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.univrouen.rss25SB.services.PostService;

@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Endpoint d'insertion. Il prend un fichier xml et enregistre les articles/ item dans la base.
    @PostMapping(value = "/rss25SB/insert", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<String> insertRssFeed(@RequestBody String rssXml) {
        int result = postService.insertRssFeed(rssXml);

        String responseXml;
        HttpStatus status;

        switch (result) {
            // Une erreur xml/xsd. Le fichier xml n'est pas conforme au fichier xsd
            case -1:
                responseXml = "<response><status>ERROR</status><message>Invalid XML/XSD</message></response>";
                status = HttpStatus.BAD_REQUEST;
                break;
            // Un des articles du fichier est déja dans la base de données
            case -2:
                responseXml = "<response><status>ERROR</status><message>Item already exists</message></response>";
                status = HttpStatus.CONFLICT;
                break;
            // Une erreur interne au serveur (aucun accès à la base par exemple)
            case -3:
                responseXml = "<response><status>ERROR</status><message>Internal server error</message></response>";
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            // L'insertion s'est bien passée. Renvoie l'id du dernier article inséré.
            default:
                responseXml = "<response><id>" + result + "</id><status>INSERTED</status></response>";
                status = HttpStatus.OK;
                break;
        }

        return ResponseEntity.status(status)
                .header("Content-Type", "application/xml")
                .body(responseXml);
    }

    // Suppression d'un article dont l'id correspond à id.
    @PostMapping("/rss25SB/delete/{id}")
    public String delete(@PathVariable int id) {
        boolean result = postService.delete(id);
        if (result) {
            // L'article à bien été supprimé de la base.
            return "<response><status>DELETED</status><message>Article "+ id + "deleted</message></response>";
        }
        // Erreur. L'article est introuvable par exemple.
        return "<response><status>ERROR</status><message>Article introuvable ou erreur interne</message></response>";
    }
}
