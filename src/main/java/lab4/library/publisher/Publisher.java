package lab4.library.publisher;

public class Publisher {

    private int publisherId;

    private String name;

    Publisher() {}

    Publisher(int publisherId, String name) {
        this.publisherId = publisherId;
        this.name = name;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
