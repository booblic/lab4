package lab4.library.controller.convert;

import lab4.library.ReflectionToString;
import lab4.library.annotation.Phone;
import lab4.library.annotation.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Класс объект которого генерируется при получении от пользователя заполненой формы
 * @author Кирилл
 * @version 1.0
 */
public class FormUser {

    @ToString
    private Integer userId;

    @ToString
    @NotEmpty
    private String username;

    private String password;

    private String oldPassword;

    private String confirmedPassword;

    /**
     * Contains the password reset mark
     */
    private String dropPassword;

    @ToString
    private String firstName;

    @ToString
    private String lastName;

    @ToString
    private String middleName;

    @NotEmpty
    @Email
    @ToString
    private String email;

    @Phone
    @ToString
    private String phoneNumber;

    @ToString
    private String role;

    public FormUser() {}

    public FormUser(String username, String password, String firstName, String lastName, String middleName, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public String getDropPassword() {
        return dropPassword;
    }

    public void setDropPassword(String dropPassword) {
        this.dropPassword = dropPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return ReflectionToString.reflectionToString(this);
    }
}
