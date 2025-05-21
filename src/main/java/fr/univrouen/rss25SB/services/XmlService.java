package fr.univrouen.rss25SB.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.univrouen.rss25SB.entities.Item;
import fr.univrouen.rss25SB.repositories.ItemRepository;

@Service
public class XmlService {

    private final ItemRepository itemRepository;

    public XmlService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Récupère les articles présent dans la base et les affiche
    public List<Item> resume() {
        return itemRepository.findAll();
    }

    // Récupère l'article dont l'id est id.
    public Optional<Item> resumeById(long id) {
        return itemRepository.findById(id);
    }
}
