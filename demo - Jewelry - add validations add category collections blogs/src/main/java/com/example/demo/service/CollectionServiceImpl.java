package com.example.demo.service;

import com.example.demo.entity.Collection;
import com.example.demo.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    @Override
    public List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }

    @Override
    public Collection getCollectionById(int id) {
        return collectionRepository.findById(id).orElse(null);
    }

    @Override
    public void saveCollection(Collection collection) {
        collectionRepository.save(collection);
    }

    @Override
    public void deleteCollectionById(int id) {
        collectionRepository.deleteById(id);
    }
}
