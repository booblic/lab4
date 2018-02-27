package lab4.library.services;

import lab4.library.publisher.Publisher;
import lab4.library.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class PublisherServices {

    @Autowired
    private PublisherRepository publisherRepository;

    public Publisher savePublisher(Publisher publisher) {
        Example<Publisher> publisherExample = Example.of(publisher);
        if (publisherRepository.exists(publisherExample)) {
            return publisherRepository.findByPublisherName(publisher.getPublisherName());
        }
        return publisherRepository.save(publisher);
    }
}
