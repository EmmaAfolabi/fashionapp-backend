package com.fashionapp.controller;

import com.fashionapp.model.Review;
import com.fashionapp.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Reviews Management", description = "APIs for managing cloth reviews and ratings")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/cloth/{clothId}")
    @Operation(summary = "Add review for cloth", description = "Create a new review for a specific cloth item")
    public ResponseEntity<Review> addReview(@PathVariable Long clothId, @RequestBody Review review) {
        Review createdReview = reviewService.addReview(clothId, review);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @GetMapping("/cloth/{clothId}")
    @Operation(summary = "Get reviews for cloth", description = "Retrieve all reviews for a specific cloth with pagination")
    public ResponseEntity<Page<Review>> getReviewsForCloth(
            @PathVariable Long clothId,
            @PageableDefault(size = 20, page = 0, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<Review> reviews = reviewService.getReviewsForCloth(clothId, pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "Get review by ID", description = "Retrieve a specific review by ID")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "Delete review", description = "Remove a review and recalculate cloth rating")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("Review deleted successfully");
    }

    @GetMapping("/cloth/{clothId}/rating")
    @Operation(summary = "Get average rating", description = "Get the average rating for a cloth item")
    public ResponseEntity<Map<String, Object>> getAverageRating(@PathVariable Long clothId) {
        Double averageRating = reviewService.getAverageRatingForCloth(clothId);
        long reviewCount = reviewService.getReviewCountForCloth(clothId);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("clothId", clothId);
        response.put("averageRating", averageRating != null ? averageRating : 0.0);
        response.put("reviewCount", reviewCount);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/cloth/{clothId}/count")
    @Operation(summary = "Get review count", description = "Get the total number of reviews for a cloth item")
    public ResponseEntity<Map<String, Object>> getReviewCount(@PathVariable Long clothId) {
        long reviewCount = reviewService.getReviewCountForCloth(clothId);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("clothId", clothId);
        response.put("reviewCount", reviewCount);

        return ResponseEntity.ok(response);
    }
}

