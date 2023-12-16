package com.midnightsun.productservice.web;

import com.midnightsun.productservice.service.ReviewService;
import com.midnightsun.productservice.service.dto.ReviewDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<Page<ReviewDTO>> getAll(Pageable pageable) {
        log.debug("REST request to get all REVIEWS sorted by {}, page number: {} and page size: {}",
                pageable.getSort(), pageable.getPageNumber(), pageable.getPageSize());

        final var reviews = reviewService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(reviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getOne(@PathVariable Long id) {
        log.debug("REST request to get REVIEW by ID: {}", id);
        final var review = reviewService.getOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(review);
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> save(@RequestBody ReviewDTO reviewDTO) {
        log.debug("REST request to save REVIEW with content: {}", reviewDTO);
        final var savedReview = reviewService.save(reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    @PutMapping
    public ResponseEntity<ReviewDTO> update(@RequestBody ReviewDTO reviewDTO) {
        log.debug("REST request to updated REVIEW with ID: {} with content {}", reviewDTO.getId(), reviewDTO);
        final var updatedReview = reviewService.update(reviewDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedReview);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete REVIEW with ID: {}", id);
        reviewService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
