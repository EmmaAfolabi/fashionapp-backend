package com.fashionapp.service;

import com.fashionapp.model.Cloth;
import com.fashionapp.repository.ClothRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClothService {

    private final ClothRepository clothRepository;

    public ClothService(ClothRepository clothRepository) {
        this.clothRepository = clothRepository;
    }

    public List<Cloth> getAllClothes() {
        return clothRepository.findAll();
    }

    public Cloth addCloth(Cloth cloth) {

        if (cloth.getPrice() == null || cloth.getPrice().doubleValue() < 0) {
            throw new RuntimeException("Price must be greater than zero");
        }


        cloth.setCreatedAt(LocalDateTime.now());

        return clothRepository.save(cloth);
    }
}
