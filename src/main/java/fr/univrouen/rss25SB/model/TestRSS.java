package fr.univrouen.rss25SB.model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

public class TestRSS {
	
	private Resource resource;
	
	public TestRSS() {
		this.resource = new DefaultResourceLoader().getResource("classpath:xml/item.xml");
	}
	
	public String loadFileXML() {
		StringBuilder content = new StringBuilder();
		try (InputStream inputStream = resource.getInputStream();
	             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

	            String line;
	            while ((line = reader.readLine()) != null) {
	                content.append(line).append("\n");
	            }
	            return content.toString(); // Retourne le contenu du fichier XML

	        } catch (IOException e) {
	            return "Erreur lors de la lecture du fichier XML : " + e.getMessage();
	     }
		
	}
}
