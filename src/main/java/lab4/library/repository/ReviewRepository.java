package lab4.library.repository;

import lab4.library.book.Book;
import lab4.library.review.Review;
import lab4.library.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    public Review findByBookAndUser(Book book, User user);
}
