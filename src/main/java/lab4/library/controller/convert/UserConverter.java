package lab4.library.controller.convert;

import lab4.library.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * A class intended for converting a FormUser object to a User object
 * @author Кирилл
 * @version 1.0
 */
@Component
public class UserConverter implements Converter<FormUser, User> {

    private static final Logger LOG = LoggerFactory.getLogger(UserConverter.class);

    /**
     * The method retrieves information from the formUser object and forms a user object on it
     * @param formUser - object generated when a form is received from the user
     * @return user - object entity user
     */
    @Override
    public User convert(FormUser formUser) {

        User user = new User();

        LOG.info("msg: formUser = {}", formUser.toString());
        user.setUsername(formUser.getUsername());

        user.setFirstName(formUser.getFirstName());

        user.setLastName(formUser.getLastName());

        user.setMiddleName(formUser.getMiddleName());

        user.setEmail(formUser.getEmail());

        user.setPhoneNumber(formUser.getPhoneNumber());

        return user;
    }
}
