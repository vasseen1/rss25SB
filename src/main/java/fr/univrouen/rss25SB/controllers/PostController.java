package fr.univrouen.rss25SB.controllers;

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