package com.example.bookserver.repository;

import com.example.bookserver.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByOrderByBook_IdAsc();
    List<Review> findReviewsByBook_id(long book_id);

}
