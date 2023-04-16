package com.example.bookserver.repository;

import com.example.bookserver.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByOrderByBook_IdAsc();
    List<Review> findReviewsByBook_id(long book_id);
}
