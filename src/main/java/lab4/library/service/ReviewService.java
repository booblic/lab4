package lab4.library.service;

import lab4.library.book.Book;
import lab4.library.repository.ReviewRepository;
import lab4.library.review.Review;
import lab4.library.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review saveReview(String textReview, Integer rating, Book book, User user) {
        if (findByBookAndUser(book, user) != null) {
            Review review = findByBookAndUser(book, user);
            review.setText(textReview);
            review.setRating(rating);
            return reviewRepository.save(review);
        }
        Review review = new Review();
        review.setText(textReview);
        review.setRating(rating);
        review.setBook(book);
        review.setUser(user);
        return reviewRepository.save(review);
    }

    public Review findByBookAndUser(Book book, User user) {
        return reviewRepository.findByBookAndUser(book, user);
    }
}
