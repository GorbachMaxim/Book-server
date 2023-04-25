package com.example.bookserver.controller;

import com.example.bookserver.dto.MessageResponse;
import com.example.bookserver.model.Genre;
import com.example.bookserver.model.Review;
import com.example.bookserver.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/review")
@CrossOrigin
public class ReviewController {

    @Autowired
    private ReviewService reviewService;



    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Review> getBookReviews(@PathVariable long id){
        List<Review> reviews = reviewService.getReviewsByBookId(id);
        return reviews;
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> addReview(HttpServletRequest request, @PathVariable long id, @RequestBody @Valid Review review) {
        reviewService.addReview(request, review, id);
        return ResponseEntity.ok(new MessageResponse("Review CREATED"));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteGenre(HttpServletRequest request, @PathVariable long id){
        reviewService.deleteReviewById(request, id);
        return ResponseEntity.ok(new MessageResponse("Review DELETED"));
    }
}
