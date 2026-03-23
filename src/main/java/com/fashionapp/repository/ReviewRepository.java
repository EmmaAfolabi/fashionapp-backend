package com.fashionapp.repository;

import com.fashionapp.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByClothId(Long clothId, Pageable pageable);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.cloth.id = :clothId")
    Double getAverageRatingForCloth(@Param("clothId") Long clothId);

    long countByClothId(Long clothId);
}

