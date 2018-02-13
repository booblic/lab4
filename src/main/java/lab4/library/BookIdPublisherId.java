package lab4.library;

import java.io.Serializable;

public final class BookIdPublisherId implements Serializable {
    private Integer bookId;

    private int publisherId;

    public BookIdPublisherId() {}

    public BookIdPublisherId(Integer bookId, int publisherId) {
        this.bookId = bookId;
        this.publisherId = publisherId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    @Override
    public boolean equals(Object otherOb) {
        if (this == otherOb) {
            return true;
        }

        if (!(otherOb instanceof BookIdPublisherId)) {
            return false;
        }

        BookIdPublisherId other = (BookIdPublisherId) otherOb;
        return ((this.getBookId() == null
                ? other.getBookId() == null
                : this.getBookId().equals(other.getBookId()))
                && (this.getPublisherId() == other.getPublisherId()));
    }

    @Override
    public int hashCode() {
        return ((this.getBookId() == null
                ? 0
                :this.getBookId().hashCode())
                ^ ((int) this.getPublisherId()) );

    }

    @Override
    public String toString() {
        return "" + getBookId() + "-"+ getPublisherId();
    }
}
