package com.jewelry.KiraJewelry.service.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.Collection;
import com.jewelry.KiraJewelry.repository.CollectionRepository;

import java.util.List;

@Service
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    public List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }

    public Collection getCollectionById(int id) {
        return collectionRepository.findById(id).orElse(null);
    }

    public void saveCollection(Collection collection) {
        collectionRepository.save(collection);
    }

    public void deleteCollectionById(int id) {
        collectionRepository.deleteById(id);
    }
}

