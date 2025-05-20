package fr.univrouen.rss25SB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.univrouen.rss25SB.services.PostService;

@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    public ResponseEntity<String> insertRssFeed(String rssXml) {

        boolean success = true; // simuler succès

        if (success) {
            String responseXml = "<response><id>123</id><status>INSERTED</status></response>";
            return ResponseEntity.ok()
                .header("Content-Type", "application/xml")
                .body(responseXml);
        } else {
            String responseXml = "<response><status>ERROR</status></response>";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "application/xml")
                .body(responseXml);
        }
    }

    @PostMapping("/rss25SB/delete/{id}")
    public String delete(@PathVariable int id) {
        boolean result = postService.delete(id);
        if (result) {
            return "L'article a bien été supprimé";
        }
        return "erreur, article introuvable";
    }
}
