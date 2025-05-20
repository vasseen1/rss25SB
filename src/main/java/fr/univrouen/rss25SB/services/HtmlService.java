package fr.univrouen.rss25SB.services;

import java.util.List;
import java.util.Optional;

import fr.univrouen.rss25SB.entities.Item;
import fr.univrouen.rss25SB.repositories.ItemRepository;

public class HtmlService {

    private final ItemRepository itemRepository;

    public HtmlService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> resume() {
        return itemRepository.findAll();
    }

    public Optional<Item> resumeById(long id) {
        return itemRepository.findById(id);
    }
    
}
