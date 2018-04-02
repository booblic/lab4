package lab4.library.controller.convert;

import lab4.library.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserReverseConvertor implements Converter<User, FormUser> {

    private static final Logger LOG = LoggerFactory.getLogger(UserReverseConvertor.class);

    @Override
    public FormUser convert(User user) {

        FormUser formUser = new FormUser();


        formUser.setUsername(user.getUsername());

        formUser.setFirstName(user.getFirstName());

        formUser.setLastName(user.getLastName());

        formUser.setMiddleName(user.getMiddleName());

        formUser.setEmail(user.getEmail());

        formUser.setPhoneNumber(user.getPhoneNumber());

        return formUser;
    }
}
