package lab4.library.services;

import lab4.library.publisher.Publisher;
import lab4.library.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PublisherServices {

    @Autowired
    private PublisherRepository publisherRepository;

    public Publisher savePublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Set<Publisher> getPublisher(String publisherNames) {

        Set<Publisher> publisherSet = new HashSet<>();

        for (String publisherName: publisherNames.split(",")) {

            Publisher publisher = findByPublisherName(publisherName.trim());

            if (publisher != null) {
                publisherSet.add(publisher);
            } else {
                publisherSet.add(savePublisher(new Publisher(publisherName)));
            }
        }
        return publisherSet;
    }

    public Publisher findByPublisherName(String publisherName) {
        return publisherRepository.findByPublisherName(publisherName);
    }
}
