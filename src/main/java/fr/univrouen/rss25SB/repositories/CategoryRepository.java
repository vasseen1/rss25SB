package fr.univrouen.rss25SB.repositories;

import fr.univrouen.rss25SB.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Tu peux ajouter des méthodes personnalisées ici si besoin
}
