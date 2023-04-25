package com.example.bookserver.dto.pojo;

import com.example.bookserver.model.Book;
import com.example.bookserver.model.Review;
import com.example.bookserver.model.User;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class ReviewDTO {
    private long id;

    @Min(1)
    @Max(5)
    private int mark;

    private String text;

    private UserDTO user;

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.mark = review.getMark();
        this.text = review.getText();
        this.user = new UserDTO(review.getUser());
    }

    public ReviewDTO() {
    }
}
