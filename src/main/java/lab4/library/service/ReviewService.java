package lab4.library.service;

import lab4.library.book.Book;
import lab4.library.repository.ReviewRepository;
import lab4.library.review.Review;
import lab4.library.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review findByBookAndUser(Book book, User user) {
        return reviewRepository.findByBookAndUser(book, user);
    }
}
