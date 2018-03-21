package lab4.library.book;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author VYZH
 * @since 20.03.2018
 */
@MappedSuperclass
public class AbstractBook {

    @Column(length = 1000)
    private String desciption;

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }
}
