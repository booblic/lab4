package lab4.library.service;

import lab4.library.publisher.Publisher;
import lab4.library.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public Publisher savePublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Set<Publisher> findOrCreatePublisher(String[] publisherNames) {

        Set<Publisher> publisherSet = new HashSet<>();

        for (String publisherName: publisherNames) {

            if (publisherName.compareTo("") != 0) {

                Publisher publisher = findByPublisherName(publisherName);

                if (publisher != null) {
                    publisherSet.add(publisher);
                } else {
                    publisherSet.add(savePublisher(new Publisher(publisherName)));
                }
            }
        }
        return publisherSet;
    }

    public Publisher findByPublisherName(String publisherName) {
        return publisherRepository.findByPublisherName(publisherName);
    }
}
