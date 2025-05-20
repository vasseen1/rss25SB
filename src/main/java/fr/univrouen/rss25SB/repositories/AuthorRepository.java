package fr.univrouen.rss25SB.repositories;

import fr.univrouen.rss25SB.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    // Tu peux ajouter des méthodes personnalisées ici si besoin
}
