package com.fashionapp.repository;

import com.fashionapp.model.Cloth;
import com.fashionapp.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClothRepository extends JpaRepository<Cloth, Long> {

    Optional<Cloth> findByName(String name);

    Page<Cloth> findByCategory(Category category, Pageable pageable);

    @Query("SELECT c FROM Cloth c WHERE c.price BETWEEN :minPrice AND :maxPrice")
    Page<Cloth> findByPriceRange(@Param("minPrice") BigDecimal minPrice, 
                                 @Param("maxPrice") BigDecimal maxPrice, 
                                 Pageable pageable);

    @Query("SELECT c FROM Cloth c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Cloth> searchByName(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT c FROM Cloth c WHERE c.stock > 0")
    Page<Cloth> findInStock(Pageable pageable);

    @Query("SELECT c FROM Cloth c WHERE c.category = :category AND c.price BETWEEN :minPrice AND :maxPrice")
    Page<Cloth> findByCategoryAndPriceRange(@Param("category") Category category,
                                            @Param("minPrice") BigDecimal minPrice,
                                            @Param("maxPrice") BigDecimal maxPrice,
                                            Pageable pageable);
}


