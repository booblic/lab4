package lab4.library;

public class BookPublisher {
    private BookIdPublisherId bookPublisherId;

    private int bookId;

    private int publisherId;

    BookPublisher() {}

    BookPublisher(BookIdPublisherId bookPublisherId, int bookId, int publisherId) {
        this.bookPublisherId = bookPublisherId;
        this.bookId = bookId;
        this.publisherId = publisherId;
    }

    public BookIdPublisherId getBookPublisherId() {
        return bookPublisherId;
    }

    public void setBookPublisherId(BookIdPublisherId bookAutorId) {
        this.bookPublisherId = bookPublisherId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }
}
