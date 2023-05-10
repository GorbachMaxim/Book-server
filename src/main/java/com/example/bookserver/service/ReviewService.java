package com.example.bookserver.service;

import com.example.bookserver.dto.pojo.ReviewDTO;
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


    public void addReview(HttpServletRequest request, ReviewDTO reviewDTO, long id) throws IllegalAccessException {
        Book book = bookRepository.findById(id).orElseThrow(NullPointerException::new);
        User user = userService.getUserFromJWT(request);
        Review review = new Review();
        review.setText(reviewDTO.getText());
        review.setMark(reviewDTO.getMark());
        review.setBook(book);
        review.setUser(user);
        if(user.isVerified())
            reviewRepository.save(review);
        else
            throw new IllegalAccessException("User not verified!");
    }

    public List<Review> getReviewsByBookId(long id){
        return reviewRepository.findReviewsByBook_id(id);
    }


    public void deleteReviewById(HttpServletRequest request, long id){
        User user1 = userService.getUserFromJWT(request);

        Review review = reviewRepository.findById(id).orElseThrow(NullPointerException::new);
        User user2 = review.getUser();
        Book book = bookRepository.findById(review.getBook().getId()).orElseThrow(NullPointerException::new);

        if(Objects.equals(user1.getId(), user2.getId())){
            book.getReviews().remove(review);
            bookRepository.save(book);
            reviewRepository.deleteById(review.getId());
            return;
        }


        user1.getRoles().forEach(role -> {
            if (role.getName() == ERole.ROLE_ADMIN) {
                book.getReviews().remove(review);
                bookRepository.save(book);
                reviewRepository.deleteById(review.getId());
            }
            });
    }
}
