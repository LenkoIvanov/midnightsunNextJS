package com.midnightsun.productservice.service;

import com.midnightsun.productservice.mapper.ReviewMapper;
import com.midnightsun.productservice.model.Review;
import com.midnightsun.productservice.repository.ReviewRepository;
import com.midnightsun.productservice.service.dto.ReviewDTO;
import com.midnightsun.productservice.web.exception.HttpBadRequestException;
import com.midnightsun.productservice.web.exception.HttpNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

    public Page<ReviewDTO> getAll(Pageable pageable) {
        log.debug("Request to get all REVIEWS");
        return reviewRepository.findAll(pageable).map(reviewMapper::toDTO);
    }

    public ReviewDTO getOne(Long id) {
        log.debug("Request to get REVIEW by ID: {}", id);
        return reviewMapper.toDTO(reviewRepository.findById(id).orElse(null));
    }

    public ReviewDTO save(ReviewDTO reviewDTO) {
        log.debug("Request to save REVIEW: {}", reviewDTO);
        if (reviewDTO.getId() != null) throw new HttpBadRequestException(HttpBadRequestException.ID_NON_NULL);
        final var review = reviewMapper.toEntity(reviewDTO);
        return saveEntity(review);
    }

    public ReviewDTO update(ReviewDTO reviewDTO) {
        log.debug("Request to update REVIEW: {}", reviewDTO);
        if (reviewDTO.getId() == null) throw new HttpBadRequestException(HttpBadRequestException.ID_NULL);
        final var review = reviewMapper.toEntity(reviewDTO);
        return saveEntity(review);
    }

    private ReviewDTO saveEntity(Review review) {
        final var savedReview = reviewRepository.save(review);
        return reviewMapper.toDTO(savedReview);
    }

    public void delete(Long id) {
        log.debug("Request to delete REVIEW with ID: {}", id);
        final var review = reviewRepository.findById(id);
        if (review.isEmpty()) throw new HttpNotFoundException("Entity not found");
        reviewRepository.deleteById(id);
    }
}
