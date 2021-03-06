package lab4.library.review;

import lab4.library.ReflectionToString;
import lab4.library.annotation.ToString;
import lab4.library.book.Book;
import lab4.library.user.User;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class representing entity - review
 * @author Кирилл
 * @version 1.0
 */
@Entity
@Table
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reviewId;

    @ToString
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ToString
    @ManyToOne
    @JoinColumn(name = "bookId", nullable = false, insertable = true, updatable = true)
    private Book book;

    /**
     * Map containing review text and rating
     */
    @ToString
    @ElementCollection(targetClass = Integer.class)
    @MapKeyColumn(name = "textReview", length = 1000)
    private Map<String, Integer> rating = new HashMap<>();

    /*@Column(length = 1000)
    private String text;

    private Integer rating;*/

    public Review() {}

    public Review(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    /*public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }*/

    public Map<String, Integer> getBookReview() {
        return rating;
    }

    public void setBookReview(Map<String, Integer> rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return Objects.equals(reviewId, review.reviewId) &&
                Objects.equals(user, review.user) &&
                Objects.equals(book, review.book);
    }

    @Override
    public int hashCode() {

        return Objects.hash(reviewId, user, book);
    }

    @Override
    public String toString() {
        return ReflectionToString.reflectionToString(this);
    }
}
