package com.fashionapp.service;

import com.fashionapp.model.Cloth;
import com.fashionapp.model.Category;
import com.fashionapp.repository.ClothRepository;
import com.fashionapp.exception.InvalidPriceException;
import com.fashionapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClothService {

    private static final Logger logger = LoggerFactory.getLogger(ClothService.class);

    @Autowired
    private final ClothRepository clothRepository;

    public ClothService(ClothRepository clothRepository) {
        this.clothRepository = clothRepository;
    }

    public List<Cloth> getAllClothes() {
        logger.info("Fetching all clothes");
        return clothRepository.findAll();
    }

    public Page<Cloth> getAllClothesPage(Pageable pageable) {
        logger.info("Fetching clothes with pagination: page={}, size={}, sort={}",
                   pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        return clothRepository.findAll(pageable);
    }

    public Cloth addCloth(Cloth cloth) {
        logger.info("Adding new cloth: {}", cloth.getName());

        if (cloth.getPrice() == null || cloth.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            logger.error("Invalid price for cloth: {}", cloth.getPrice());
            throw new InvalidPriceException("Price must be greater than zero");
        }

        if (cloth.getStock() == null) {
            cloth.setStock(0);
        }

        cloth.setCreatedAt(LocalDateTime.now());
        cloth.setUpdatedAt(LocalDateTime.now());

        Cloth savedCloth = clothRepository.save(cloth);
        logger.info("Cloth added successfully with ID: {}", savedCloth.getId());
        return savedCloth;
    }

    public Cloth getClothById(Long id) {
        logger.info("Fetching cloth by ID: {}", id);
        return clothRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Cloth not found with ID: {}", id);
                    return new ResourceNotFoundException("Cloth not found with ID: " + id);
                });
    }

    public Cloth updateCloth(Long id, Cloth clothDetails) {
        logger.info("Updating cloth with ID: {}", id);

        Cloth cloth = getClothById(id);

        if (clothDetails.getName() != null) {
            cloth.setName(clothDetails.getName());
        }
        if (clothDetails.getCategory() != null) {
            cloth.setCategory(clothDetails.getCategory());
        }
        if (clothDetails.getPrice() != null) {
            if (clothDetails.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                logger.error("Invalid price for update: {}", clothDetails.getPrice());
                throw new InvalidPriceException("Price must be greater than zero");
            }
            cloth.setPrice(clothDetails.getPrice());
        }
        if (clothDetails.getDescription() != null) {
            cloth.setDescription(clothDetails.getDescription());
        }
        if (clothDetails.getImageUrl() != null) {
            cloth.setImageUrl(clothDetails.getImageUrl());
        }
        if (clothDetails.getStock() != null) {
            cloth.setStock(clothDetails.getStock());
        }

        cloth.setUpdatedAt(LocalDateTime.now());

        Cloth updatedCloth = clothRepository.save(cloth);
        logger.info("Cloth updated successfully with ID: {}", id);
        return updatedCloth;
    }

    public void deleteCloth(Long id) {
        logger.info("Deleting cloth with ID: {}", id);

        if (!clothRepository.existsById(id)) {
            logger.error("Cloth not found with ID: {}", id);
            throw new ResourceNotFoundException("Cloth not found with ID: " + id);
        }

        clothRepository.deleteById(id);
        logger.info("Cloth deleted successfully with ID: {}", id);
    }

    public Page<Cloth> searchByCategory(Category category, Pageable pageable) {
        logger.info("Searching clothes by category: {}", category);
        return clothRepository.findByCategory(category, pageable);
    }

    public Page<Cloth> searchByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        logger.info("Searching clothes by price range: {} - {}", minPrice, maxPrice);
        return clothRepository.findByPriceRange(minPrice, maxPrice, pageable);
    }

    public Page<Cloth> searchByName(String searchTerm, Pageable pageable) {
        logger.info("Searching clothes by name: {}", searchTerm);
        return clothRepository.searchByName(searchTerm, pageable);
    }

    public Page<Cloth> getInStockClothes(Pageable pageable) {
        logger.info("Fetching in-stock clothes");
        return clothRepository.findInStock(pageable);
    }

    public Page<Cloth> searchByCategoryAndPriceRange(Category category, BigDecimal minPrice,
                                                      BigDecimal maxPrice, Pageable pageable) {
        logger.info("Searching clothes by category: {} and price range: {} - {}", category, minPrice, maxPrice);
        return clothRepository.findByCategoryAndPriceRange(category, minPrice, maxPrice, pageable);
    }

    public void updateStock(Long clothId, Integer quantity) {
        logger.info("Updating stock for cloth ID: {} by quantity: {}", clothId, quantity);

        Cloth cloth = getClothById(clothId);
        int newStock = cloth.getStock() + quantity;

        if (newStock < 0) {
            logger.error("Insufficient stock for cloth ID: {}", clothId);
            throw new RuntimeException("Insufficient stock available");
        }

        cloth.setStock(newStock);
        cloth.setUpdatedAt(LocalDateTime.now());
        clothRepository.save(cloth);

        logger.info("Stock updated successfully for cloth ID: {}", clothId);
    }
}


