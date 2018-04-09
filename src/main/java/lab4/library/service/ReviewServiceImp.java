package lab4.library.service;

import lab4.library.book.Book;
import lab4.library.repository.ReviewRepository;
import lab4.library.review.Review;
import lab4.library.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Service that implements business logic for review
 * @author Кирилл
 * @version 1.0
 */
@Service
public class ReviewServiceImp implements ReviewService {

    private static final Logger LOG = LoggerFactory.getLogger(ReviewServiceImp.class);

    @Autowired
    private UserService userService;

    @Autowired
    private BookServiceImpl bookServices;

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Transactional
    public Review findByBookAndUser(Book book, User user) {
        return reviewRepository.findByBookAndUser(book, user);
    }

    /**
     * The method receives the current user, a book by id, creates and preserves a new overview, or unveils an existing one
     * @param id - book id
     * @param textReview - text review
     * @param rating - rating
     * @return review
     */
    public Review addOrEditReview(Integer id, String textReview, Integer rating) {

        Review review;

        LOG.info("msg: getCurrentUser()");
        User user = userService.getCurrentUser();

        LOG.info("msg: findBook({});", id);
        Book book = bookServices.findOne(id);

        Map<String, Integer> bookReview = new HashMap<>();

        LOG.info("msg: bookReview.put({}, {});", textReview, rating);
        bookReview.put(textReview, rating);

        if (findByBookAndUser(book, user) != null) {

            LOG.info("msg: review.setBookReview({})", bookReview);
            review = findByBookAndUser(book, user);

            LOG.info("msg: review.setBookReview({})", bookReview.toString());
            review.setBookReview(bookReview);

        } else {

            review = new Review();

            LOG.info("msg: review.setUser({})", bookReview);
            review.setUser(user);

            LOG.info("msg: review.setBook({})", book);
            review.setBook(book);

            LOG.info("review.setBookReview({})", bookReview);
            review.setBookReview(bookReview);
        }

        return saveReview(review);
    }
}
