package fr.univrouen.rss25SB.services;

import org.springframework.beans.factory.annotation.Autowired;

import fr.univrouen.rss25SB.repositories.ItemRepository;

public class PostService {

    @Autowired
    private ItemRepository itemRepository;

    public boolean delete(long id) {
        // Vérifier si l'entité existe
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean insert() {

        return false;

    }
    
}
