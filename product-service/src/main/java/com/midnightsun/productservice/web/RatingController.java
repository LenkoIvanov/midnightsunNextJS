package com.midnightsun.productservice.web;

import com.midnightsun.productservice.service.RatingService;
import com.midnightsun.productservice.service.dto.RatingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    
    @GetMapping
    public ResponseEntity<Page<RatingDTO>> getAll(Pageable pageable) {
        log.debug("REST request to get all RATINGS sorted by {}, page number: {} and page size: {}",
                pageable.getSort(), pageable.getPageNumber(), pageable.getPageSize());

        final var ratings = ratingService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ratings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RatingDTO> getOne(@PathVariable Long id) {
        log.debug("REST request to get RATING by ID: {}", id);
        final var rating = ratingService.getOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(rating);
    }

    @PostMapping
    public ResponseEntity<RatingDTO> save(@RequestBody RatingDTO ratingDTO) {
        log.debug("REST request to save RATING with content: {}", ratingDTO);
        final var savedRating = ratingService.save(ratingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
    }

    @PutMapping
    public ResponseEntity<RatingDTO> update(@RequestBody RatingDTO ratingDTO) {
        log.debug("REST request to updated RATING with ID: {} with content {}", ratingDTO.getId(), ratingDTO);
        final var updatedRating = ratingService.update(ratingDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedRating);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete RATING with ID: {}", id);
        ratingService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
