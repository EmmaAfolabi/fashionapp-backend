package com.fashionapp.service;

import com.fashionapp.model.Review;
import com.fashionapp.model.Cloth;
import com.fashionapp.repository.ReviewRepository;
import com.fashionapp.repository.ClothRepository;
import com.fashionapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Service
public class ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ClothRepository clothRepository;

    public Review addReview(Long clothId, Review review) {
        logger.info("Adding review for cloth ID: {}", clothId);

        Cloth cloth = clothRepository.findById(clothId)
                .orElseThrow(() -> new ResourceNotFoundException("Cloth not found with ID: " + clothId));

        if (review.getRating() == null || review.getRating() < 1 || review.getRating() > 5) {
            logger.error("Invalid rating: {}", review.getRating());
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        review.setCloth(cloth);
        review.setCreatedAt(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);
        updateClothRating(clothId);

        logger.info("Review added successfully for cloth ID: {}", clothId);
        return savedReview;
    }

    public Page<Review> getReviewsForCloth(Long clothId, Pageable pageable) {
        logger.info("Fetching reviews for cloth ID: {}", clothId);

        if (!clothRepository.existsById(clothId)) {
            throw new ResourceNotFoundException("Cloth not found with ID: " + clothId);
        }

        return reviewRepository.findByClothId(clothId, pageable);
    }

    public Review getReviewById(Long reviewId) {
        logger.info("Fetching review with ID: {}", reviewId);
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + reviewId));
    }

    public void deleteReview(Long reviewId) {
        logger.info("Deleting review with ID: {}", reviewId);

        Review review = getReviewById(reviewId);
        Long clothId = review.getCloth().getId();

        reviewRepository.deleteById(reviewId);
        updateClothRating(clothId);

        logger.info("Review deleted successfully with ID: {}", reviewId);
    }

    private void updateClothRating(Long clothId) {
        Double averageRating = reviewRepository.getAverageRatingForCloth(clothId);

        Cloth cloth = clothRepository.findById(clothId).orElseThrow();
        cloth.setRating(averageRating != null ? averageRating.intValue() : 0);
        clothRepository.save(cloth);
    }

    public Double getAverageRatingForCloth(Long clothId) {
        logger.info("Fetching average rating for cloth ID: {}", clothId);

        if (!clothRepository.existsById(clothId)) {
            throw new ResourceNotFoundException("Cloth not found with ID: " + clothId);
        }

        return reviewRepository.getAverageRatingForCloth(clothId);
    }

    public long getReviewCountForCloth(Long clothId) {
        logger.info("Fetching review count for cloth ID: {}", clothId);

        if (!clothRepository.existsById(clothId)) {
            throw new ResourceNotFoundException("Cloth not found with ID: " + clothId);
        }

        return reviewRepository.countByClothId(clothId);
    }
}

