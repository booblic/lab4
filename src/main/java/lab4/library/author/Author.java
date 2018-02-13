package lab4.library.author;

public class Author {
    private int authorId;

    private String name;

    private String surname;

    private String[] patronymic;

    Author() {}

    Author(int authorId, String name, String surname, String... patronymic) {
        this.authorId = authorId;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String[] getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String[] patronymic) {
        this.patronymic = patronymic;
    }
}
