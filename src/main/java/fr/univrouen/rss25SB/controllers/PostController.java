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

    @PostMapping(value = "/rss25SB/insert", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<String> insertRssFeed(@RequestBody String rssXml) {
        int result = postService.insertRssFeed(rssXml);

        String responseXml;
        HttpStatus status;

        switch (result) {
            case -1:
                responseXml = "<response><status>ERROR</status><message>Invalid XML/XSD</message></response>";
                status = HttpStatus.BAD_REQUEST;
                break;
            case -2:
                responseXml = "<response><status>ERROR</status><message>Item already exists</message></response>";
                status = HttpStatus.CONFLICT;
                break;
            case -3:
                responseXml = "<response><status>ERROR</status><message>Internal server error</message></response>";
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            default:
                responseXml = "<response><id>" + result + "</id><status>INSERTED</status></response>";
                status = HttpStatus.OK;
                break;
        }

        return ResponseEntity.status(status)
                .header("Content-Type", "application/xml")
                .body(responseXml);
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
