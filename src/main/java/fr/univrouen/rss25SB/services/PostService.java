package fr.univrouen.rss25SB.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univrouen.rss25SB.repositories.ItemRepository;

@Service
public class PostService {

    private final ItemRepository itemRepository;

    @Autowired  // injecte via constructeur
    public PostService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public boolean delete(long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean insert() {

        return false;

    }
    
}
