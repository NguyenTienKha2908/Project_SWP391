package com.example.demo.service;

import com.example.demo.entity.Collection;
import java.util.List;

public interface CollectionService {
    List<Collection> getAllCollections();
    Collection getCollectionById(int id);
    void saveCollection(Collection collection);
    void deleteCollectionById(int id);
}
