package fr.univrouen.rss25SB.repositories;

import fr.univrouen.rss25SB.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // Méthode pour trouver un item par son GUID (UUID)
    Item findByGuid(UUID guid);
    boolean existsByTitleAndPublished(String title, OffsetDateTime published);
    boolean existsByTitle(String title);

}
