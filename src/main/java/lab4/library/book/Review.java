package lab4.library.book;

import lab4.library.user.User;

import javax.persistence.*;

@Entity
@Table
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reviewId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bookId")
    private Book book;

    private String text;

    Review() {}

    public Review(User user, Book book, String text) {
        this.user = user;
        this.book = book;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
