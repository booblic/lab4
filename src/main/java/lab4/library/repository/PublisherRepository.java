package lab4.library.repository;

import lab4.library.book.Book;
import lab4.library.publisher.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

    Publisher findByPublisherName(String publisherName);
}