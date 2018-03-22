package lab4.library.controller.convert;

import lab4.library.service.UserService;
import lab4.library.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<FormUser, User> {

    @Override
    public User convert(FormUser formUser) {

        User user = new User();

        user.setUsername(formUser.getUsername());
        user.setFirstName(formUser.getFirstName());
        user.setLastName(formUser.getLastName());
        user.setMiddleName(formUser.getMiddleName());
        user.setEmail(formUser.getEmail());
        user.setPhoneNumber(formUser.getPhoneNumber());

        return user;
    }
}
