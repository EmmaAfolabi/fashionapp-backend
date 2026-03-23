package com.fashionapp;

import com.fashionapp.model.Cloth;
import com.fashionapp.model.Category;
import com.fashionapp.repository.ClothRepository;
import com.fashionapp.service.ClothService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private ClothService clothService;

    @Autowired
    private ClothRepository clothRepository;

    @Test
    void contextLoads() {
        assertNotNull(clothService);
        assertNotNull(clothRepository);
    }

    @Test
    void testAddCloth() {
        Cloth cloth = new Cloth();
        cloth.setName("Test Shirt");
        cloth.setCategory(Category.SHIRT);
        cloth.setPrice(new BigDecimal("29.99"));
        cloth.setDescription("A test shirt");
        cloth.setStock(10);

        Cloth savedCloth = clothService.addCloth(cloth);

        assertNotNull(savedCloth.getId());
        assertEquals("Test Shirt", savedCloth.getName());
        assertEquals(new BigDecimal("29.99"), savedCloth.getPrice());
        assertEquals(10, savedCloth.getStock());
        assertNotNull(savedCloth.getCreatedAt());
    }

    @Test
    void testGetClothById() {
        Cloth cloth = new Cloth();
        cloth.setName("Unique Pants");
        cloth.setCategory(Category.TROUSER);
        cloth.setPrice(new BigDecimal("49.99"));
        cloth.setStock(5);

        Cloth savedCloth = clothService.addCloth(cloth);
        Cloth retrievedCloth = clothService.getClothById(savedCloth.getId());

        assertNotNull(retrievedCloth);
        assertEquals("Unique Pants", retrievedCloth.getName());
    }

    @Test
    void testUpdateCloth() {
        Cloth cloth = new Cloth();
        cloth.setName("Original Name");
        cloth.setCategory(Category.SHIRT);
        cloth.setPrice(new BigDecimal("39.99"));
        cloth.setStock(8);

        Cloth savedCloth = clothService.addCloth(cloth);

        Cloth updateDetails = new Cloth();
        updateDetails.setName("Updated Name");
        updateDetails.setPrice(new BigDecimal("49.99"));

        Cloth updatedCloth = clothService.updateCloth(savedCloth.getId(), updateDetails);

        assertEquals("Updated Name", updatedCloth.getName());
        assertEquals(new BigDecimal("49.99"), updatedCloth.getPrice());
        assertNotNull(updatedCloth.getUpdatedAt());
    }

    @Test
    void testDeleteCloth() {
        Cloth cloth = new Cloth();
        cloth.setName("To Delete");
        cloth.setCategory(Category.SHOES);
        cloth.setPrice(new BigDecimal("99.99"));

        Cloth savedCloth = clothService.addCloth(cloth);
        Long clothId = savedCloth.getId();

        clothService.deleteCloth(clothId);

        assertThrows(Exception.class, () -> clothService.getClothById(clothId));
    }

    @Test
    void testSearchByCategory() {
        Cloth shirt = new Cloth();
        shirt.setName("Blue Shirt");
        shirt.setCategory(Category.SHIRT);
        shirt.setPrice(new BigDecimal("25.00"));
        clothService.addCloth(shirt);

        Page<Cloth> results = clothService.searchByCategory(Category.SHIRT, PageRequest.of(0, 10));

        assertTrue(results.getContent().size() > 0);
        assertTrue(results.getContent().stream().allMatch(c -> c.getCategory() == Category.SHIRT));
    }

    @Test
    void testSearchByPriceRange() {
        Cloth cloth1 = new Cloth();
        cloth1.setName("Expensive Item");
        cloth1.setCategory(Category.SHIRT);
        cloth1.setPrice(new BigDecimal("100.00"));
        clothService.addCloth(cloth1);

        Cloth cloth2 = new Cloth();
        cloth2.setName("Cheap Item");
        cloth2.setCategory(Category.SHIRT);
        cloth2.setPrice(new BigDecimal("10.00"));
        clothService.addCloth(cloth2);

        Page<Cloth> results = clothService.searchByPriceRange(
            new BigDecimal("20.00"),
            new BigDecimal("80.00"),
            PageRequest.of(0, 10)
        );

        assertTrue(results.getContent().size() > 0);
        assertTrue(results.getContent().stream()
            .allMatch(c -> c.getPrice().compareTo(new BigDecimal("20.00")) >= 0 &&
                          c.getPrice().compareTo(new BigDecimal("80.00")) <= 0));
    }

    @Test
    void testSearchByName() {
        Cloth cloth = new Cloth();
        cloth.setName("Premium Cotton Shirt");
        cloth.setCategory(Category.SHIRT);
        cloth.setPrice(new BigDecimal("35.00"));
        clothService.addCloth(cloth);

        Page<Cloth> results = clothService.searchByName("Cotton", PageRequest.of(0, 10));

        assertTrue(results.getContent().size() > 0);
        assertTrue(results.getContent().stream()
            .allMatch(c -> c.getName().toLowerCase().contains("cotton")));
    }

    @Test
    void testUpdateStock() {
        Cloth cloth = new Cloth();
        cloth.setName("Stock Item");
        cloth.setCategory(Category.SHIRT);
        cloth.setPrice(new BigDecimal("25.00"));
        cloth.setStock(10);

        Cloth savedCloth = clothService.addCloth(cloth);
        clothService.updateStock(savedCloth.getId(), 5);

        Cloth updated = clothService.getClothById(savedCloth.getId());
        assertEquals(15, updated.getStock());
    }

    @Test
    void testInvalidPriceThrowsException() {
        Cloth cloth = new Cloth();
        cloth.setName("Invalid Price Item");
        cloth.setCategory(Category.SHIRT);
        cloth.setPrice(new BigDecimal("0"));

        assertThrows(Exception.class, () -> clothService.addCloth(cloth));
    }
}
