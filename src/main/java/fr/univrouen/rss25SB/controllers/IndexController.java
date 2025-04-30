package fr.univrouen.rss25SB.controllers;

@RestController
public class IndexController {
	
	@GetMapping("/")
	public String index() {
		return "Hello rss25SB !";
	}

	@GetMapping("/help")
	public String help() {
		return "travaux help";
	}
}
