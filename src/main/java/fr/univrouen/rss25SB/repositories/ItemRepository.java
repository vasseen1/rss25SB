package fr.univrouen.rss25SB.repositories;

import fr.univrouen.rss25SB.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByGuid(String guid);
    boolean existsByTitleAndPublished(String title, OffsetDateTime published);
    boolean existsByTitle(String title);

}
