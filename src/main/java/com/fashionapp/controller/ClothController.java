package com.fashionapp.controller;

import com.fashionapp.model.Cloth;
import com.fashionapp.model.Category;
import com.fashionapp.service.ClothService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/clothes")
@Tag(name = "Clothes Management", description = "APIs for managing fashion clothes inventory")
public class ClothController {

    @Autowired
    private ClothService clothService;

    @PostMapping
    @Operation(summary = "Add a new cloth", description = "Create a new cloth item in the inventory")
    public ResponseEntity<Cloth> addCloth(@Valid @RequestBody Cloth cloth) {
        Cloth createdCloth = clothService.addCloth(cloth);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCloth);
    }

    @GetMapping
    @Operation(summary = "Get all clothes", description = "Retrieve all clothes with pagination support")
    public ResponseEntity<Page<Cloth>> getAllClothes(
            @PageableDefault(size = 20, page = 0, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<Cloth> clothes = clothService.getAllClothesPage(pageable);
        return ResponseEntity.ok(clothes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get cloth by ID", description = "Retrieve a specific cloth item by ID")
    public ResponseEntity<Cloth> getClothById(@PathVariable Long id) {
        Cloth cloth = clothService.getClothById(id);
        return ResponseEntity.ok(cloth);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update cloth", description = "Update an existing cloth item")
    public ResponseEntity<Cloth> updateCloth(@PathVariable Long id, @Valid @RequestBody Cloth clothDetails) {
        Cloth updatedCloth = clothService.updateCloth(id, clothDetails);
        return ResponseEntity.ok(updatedCloth);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete cloth", description = "Remove a cloth item from inventory")
    public ResponseEntity<String> deleteClothById(@PathVariable Long id) {
        clothService.deleteCloth(id);
        return ResponseEntity.ok("Cloth deleted successfully");
    }

    @GetMapping("/search/category")
    @Operation(summary = "Search by category", description = "Find clothes by category with pagination")
    public ResponseEntity<Page<Cloth>> searchByCategory(
            @RequestParam Category category,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        Page<Cloth> clothes = clothService.searchByCategory(category, pageable);
        return ResponseEntity.ok(clothes);
    }

    @GetMapping("/search/price-range")
    @Operation(summary = "Search by price range", description = "Find clothes within a price range")
    public ResponseEntity<Page<Cloth>> searchByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        Page<Cloth> clothes = clothService.searchByPriceRange(minPrice, maxPrice, pageable);
        return ResponseEntity.ok(clothes);
    }

    @GetMapping("/search/name")
    @Operation(summary = "Search by name", description = "Find clothes by name (case-insensitive)")
    public ResponseEntity<Page<Cloth>> searchByName(
            @RequestParam String searchTerm,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        Page<Cloth> clothes = clothService.searchByName(searchTerm, pageable);
        return ResponseEntity.ok(clothes);
    }

    @GetMapping("/stock/in-stock")
    @Operation(summary = "Get in-stock clothes", description = "Retrieve all clothes currently in stock")
    public ResponseEntity<Page<Cloth>> getInStockClothes(
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        Page<Cloth> clothes = clothService.getInStockClothes(pageable);
        return ResponseEntity.ok(clothes);
    }

    @GetMapping("/search/category-price")
    @Operation(summary = "Search by category and price", description = "Find clothes by category and price range")
    public ResponseEntity<Page<Cloth>> searchByCategoryAndPrice(
            @RequestParam Category category,
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        Page<Cloth> clothes = clothService.searchByCategoryAndPriceRange(category, minPrice, maxPrice, pageable);
        return ResponseEntity.ok(clothes);
    }

    @PatchMapping("/{clothId}/stock")
    @Operation(summary = "Update stock", description = "Adjust the stock quantity for a cloth item")
    public ResponseEntity<String> updateStock(
            @PathVariable Long clothId,
            @RequestParam Integer quantity) {
        clothService.updateStock(clothId, quantity);
        return ResponseEntity.ok("Stock updated successfully");
    }
}


