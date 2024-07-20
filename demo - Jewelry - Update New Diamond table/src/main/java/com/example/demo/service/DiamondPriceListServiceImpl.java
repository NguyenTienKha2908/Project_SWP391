package com.example.demo.service;

import com.example.demo.entity.DiamondPriceList;
import com.example.demo.repository.DiamondPriceListRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class DiamondPriceListServiceImpl implements DiamondPriceListService {
    @Autowired
    private DiamondPriceListRepository diaPriceListRepository;

    @Override
    public Page<DiamondPriceList> getAllDiaPriceLists(Pageable pageable) {
        return diaPriceListRepository.findAll(pageable);
    }

    @Override
    public DiamondPriceList getDiaPriceListById(int id) {
        Optional<DiamondPriceList> optional = diaPriceListRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void saveDiaPriceList(DiamondPriceList diaPriceList) {
        diaPriceListRepository.save(diaPriceList);
    }

    @Override
    public void deleteDiaPriceListById(int id) {
        diaPriceListRepository.deleteById(id);
    }
}
