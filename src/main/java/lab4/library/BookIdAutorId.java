package lab4.library;

import java.io.Serializable;

public final class BookIdAutorId implements Serializable {
    private Integer bookId;

    private int authorId;

    public BookIdAutorId() {}

    public BookIdAutorId(Integer bookId, int authorId) {
        this.bookId = bookId;
        this.authorId = authorId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object otherOb) {
        if (this == otherOb) {
        return true;
        }

        if (!(otherOb instanceof BookIdAutorId)) {
            return false;
        }

        BookIdAutorId other = (BookIdAutorId) otherOb;
        return ((this.getBookId() == null
                ? other.getBookId() == null
                : this.getBookId().equals(other.getBookId()))
                && (this.getAuthorId() == other.getAuthorId()));
    }

    @Override
    public int hashCode() {
        return ((this.getBookId() == null
                ? 0
                :this.getBookId().hashCode())
                ^ ((int) this.getAuthorId()) );

    }

    @Override
    public String toString() {
        return "" + getBookId() + "-"+ getAuthorId();
    }
}
