package fr.univrouen.rss25SB.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
public class PostController {

    @PostMapping("/rss25SB/insert")
    public String insert() {
        return "travaux insert";
    }

    @PostMapping("/rss25SB/delete/id")
    public String delete() {
        return "travaux delete";
    }
}