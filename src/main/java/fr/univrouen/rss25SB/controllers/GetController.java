package fr.univrouen.rss25SB.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetController {
	
	@GetMapping("/resume")
	public String getListRSSinXML() {
		return "Envoi de la liste des flux RSS";
	}
	
	@GetMapping("/guid")
	public String getRSSinXML(
		@RequestParam(value="guid") String texte) {
		return "Détail du contenu RSS demandé : " + texte;
	}
	
	@GetMapping("/test")
	public String getTest(
			@RequestParam(value="nb") String nb,
			@RequestParam(value="search") String texte) {
		return "Test : " + System.lineSeparator() 
		+ "guid = " + nb + System.lineSeparator()
		+ "\n titre = " + texte;
	}

}
