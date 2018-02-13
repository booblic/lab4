package lab4.library;

public class BookAuthor {
    private BookIdAutorId bookAutorId;

    private int bookId;

    private int authorId;

    BookAuthor() {}

    BookAuthor(BookIdAutorId bookAutorId, int bookId, int authorId) {
        this.bookAutorId = bookAutorId;
        this.bookId = bookId;
        this.authorId = authorId;
    }

    public BookIdAutorId getBookAutorId() {
        return bookAutorId;
    }

    public void setBookAutorId(BookIdAutorId bookAutorId) {
        this.bookAutorId = bookAutorId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
