package fr.univrouen.rss25SB.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.univrouen.rss25SB.services.PostService;

@RestController
public class PostController {

    private PostService postService = new PostService();

    @PostMapping("/rss25SB/insert")
    public String insert() {
        boolean result = postService.insert();
        if (result) {
            return "Ok";
        }
        return "not ok, not inserted";
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
