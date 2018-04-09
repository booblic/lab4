package lab4.library;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * Class description whose mapping information is applied to the entities that inherit from it (Book, Publisher, Author, Genre)
 * @author Кирилл
 * @version 1.0
 */
@MappedSuperclass
public abstract class Description {

    @Column(length = 1000)
    private String description;

    public Description() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
