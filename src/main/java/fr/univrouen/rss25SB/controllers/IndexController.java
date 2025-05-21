package fr.univrouen.rss25SB.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	// Page d'accueil contenant nos noms, prénoms, logo de l'université...
	@GetMapping("/")
	public String index() {
		return "<!DOCTYPE html>" +
				"<html>" +
				"<head>" +
				"<meta charset='UTF-8'>" +
				"<title>Page d'accueil</title>" +
				"<style>" +
				"body { font-family: Arial, sans-serif; text-align: center; margin-top: 50px; }" +
				".logo { width: 200px; height: auto; margin-top: 20px; }" +
				"</style>" +
				"</head>" +
				"<body>" +
				"<h1>Bienvenue sur le projet RSS25SB</h1>" +
				"<p><strong>Nom du projet:</strong> RSS25SB</p>" +
				"<p><strong>Numéro de version:</strong> 0.0.1-SNAPSHOT</p>" +
				"<p><strong>Développeur(s):</strong> VASSE Enzo et BEN HAMMOU Eddinne</p>" +
				"<img src='/logo-univ-rouen-normandie-couleur.png' alt='Logo Université de Rouen' class='logo' />" +
				"</body>" +
				"</html>";
	}

	// Page contenant un tableau, qui renvoie les endpoints testables
	@GetMapping("/help")
	public String help() {
		return "<!DOCTYPE html>" +
				"<html>" +
				"<head>" +
				"<meta charset='UTF-8'>" +
				"<title>Aide - Service REST</title>" +
				"<style>" +
				"body { font-family: Arial, sans-serif; text-align: center; margin-top: 50px; }" +
				"h1 { color: #333; }" +
				"table { margin-top: 30px; border-collapse: collapse; width: 80%; margin-left: auto; margin-right: auto; }" +
				"th, td { padding: 10px; border: 1px solid #ddd; }" +
				"th { background-color: #f2f2f2; text-align: left; }" +
				"</style>" +
				"</head>" +
				"<body>" +
				"<h1>Liste des opérations disponibles</h1>" +
				"<p>Voici la liste des opérations gérées par notre service REST :</p>" +

				"<table>" +
				"<thead>" +
				"<tr><th>URL</th><th>Méthode</th><th>Résumé</th></tr>" +
				"</thead>" +
				"<tbody>" +
				"<tr><td>/</td><td>GET</td><td>Affiche la page d'accueil du projet avec des informations sur le projet (nom, version, développeur(s), logo).</td></tr>" +
				"<tr><td>/help</td><td>GET</td><td>Affiche la liste des opérations gérées par le service REST, avec leurs détails (page actuelle).</td></tr>" +
				"<tr><td>/rss25SB/resume/html</td><td>GET</td><td>Affiche la liste des articles stockés au format HTML.</td></tr>" +
				"<tr><td>/rss25SB/html/{id}</td><td>GET</td><td>Affiche le contenu complet de l’article dont l’identifiant est {id} au format HTML.</td></tr>" +
				"<tr><td>/rss25SB/resume/xml</td><td>GET</td><td>Affiche la liste des articles stockés au format XML.</td></tr>" +
				"<tr><td>/rss25SB/xml/{id}</td><td>GET</td><td>Affiche le contenu complet de l’article dont l’identifiant est {id} au format XML.</td></tr>" +
				"<tr><td>/rss25SB/insert</td><td>POST</td><td>" +
				"Ajoute un flux RSS25SB à la base de données. Le flux doit être transmis en XML conforme au schéma XSD. " +
				"Si le flux existe déjà (même titre et date), une erreur est retournée. " +
				"Si l'ajout est réussi, le flux est inséré dans la base et un ID unique est attribué à l'article." +
				"<br>Retour : Flux XML avec <strong>id</strong> (identifiant de l'article) et <strong>status</strong> (INSERTED). " +
				"En cas d'échec, seule la valeur <strong>status</strong> est retournée avec la valeur ERROR." +
				"</td></tr>" +
				"<tr><td>/rss25SB/delete/{id}</td><td>DELETE</td><td>" +
				"Supprime un article spécifié par son identifiant {id}. Si l'article est le dernier, le flux RSS25SB vide est aussi supprimé." +
				"<br>Retour : Flux XML avec <strong>id</strong> (identifiant de l'article supprimé) et <strong>status</strong> (DELETED)." +
				" En cas d'échec, seule la valeur <strong>status</strong> est retournée avec la valeur ERROR." +
				"</td></tr>" +
				"</tbody>" +
				"</table>" +

				"<p>Chaque opération permet d'effectuer des actions spécifiques avec les formats attendus et les réponses appropriées. Pour plus de détails sur chaque opération, veuillez consulter la documentation du projet.</p>" +

				"</body>" +
				"</html>";
	}
}

