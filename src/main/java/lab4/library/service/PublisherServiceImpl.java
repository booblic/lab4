package lab4.library.service;

import lab4.library.publisher.Publisher;
import lab4.library.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public Publisher savePublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Publisher findByPublisherName(String publisherName) {
        return publisherRepository.findByPublisherName(publisherName);
    }
}
