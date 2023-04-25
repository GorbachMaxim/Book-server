package com.example.bookserver.service;

import com.example.bookserver.model.Book;
import com.example.bookserver.model.ERole;
import com.example.bookserver.model.Review;
import com.example.bookserver.model.User;
import com.example.bookserver.repository.BookRepository;
import com.example.bookserver.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Service
public class ReviewService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserDetailsServiceImpl userService;


    public void addReview(HttpServletRequest request, Review review, long id){
        Book book = bookRepository.findById(id).orElseThrow(NullPointerException::new);
        User user = userService.getUserFromJWT(request);
        review.setBook(book);
        review.setUser(user);
        reviewRepository.save(review);
    }

    public List<Review> getReviewsByBookId(long id){
        return reviewRepository.findReviewsByBook_id(id);
    }


    public void deleteReviewById(HttpServletRequest request, long id){
        User user1 = userService.getUserFromJWT(request);

        Review review = reviewRepository.findById(id).orElseThrow(NullPointerException::new);
        User user2 = review.getUser();

        if(Objects.equals(user1.getId(), user2.getId())){
            reviewRepository.deleteById(id);
            return;
        }


        user1.getRoles().forEach(role -> {
            if (role.getName() == ERole.ROLE_ADMIN)
                reviewRepository.deleteById(id);
            });
    }
}
