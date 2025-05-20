package fr.univrouen.rss25SB.repositories;

import fr.univrouen.rss25SB.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;

public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByTitleAndPublished(String title, OffsetDateTime published);
    boolean existsByTitle(String title);

}
