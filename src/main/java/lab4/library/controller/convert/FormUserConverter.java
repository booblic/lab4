package lab4.library.controller.convert;

import lab4.library.user.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FormUserConverter implements Converter<User, FormUser> {

    @Override
    public FormUser convert(User user) {

        FormUser formUser = new FormUser();

        formUser.setUsername(user.getUsername());
        formUser.setFirstName(user.getFirstName());
        formUser.setLastName(user.getLastName());
        formUser.setMiddleName(user.getMiddleName());
        formUser.setEmail(user.getEmail());
        formUser.setPassword(user.getPhoneNumber());

        return null;
    }
}
