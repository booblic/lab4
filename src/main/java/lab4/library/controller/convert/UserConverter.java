package lab4.library.controller.convert;

import lab4.library.ReflectionToString;
import lab4.library.service.UserService;
import lab4.library.service.UserServiceImpl;
import lab4.library.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<FormUser, User> {

    private static final Logger LOG = LoggerFactory.getLogger(UserConverter.class);

    @Override
    public User convert(FormUser formUser) {

        User user = new User();

        LOG.info("msg: user.setUsername(formUser.getUsername());");
        user.setUsername(formUser.getUsername());
        LOG.info("msg: user.setFirstName(formUser.getFirstName());");
        user.setFirstName(formUser.getFirstName());
        LOG.info("msg: user.setLastName(formUser.getLastName());");
        user.setLastName(formUser.getLastName());
        LOG.info("msg: user.setMiddleName(formUser.getMiddleName());");
        user.setMiddleName(formUser.getMiddleName());
        LOG.info("msg: user.setEmail(formUser.getEmail());");
        user.setEmail(formUser.getEmail());
        LOG.info("msg: user.setPhoneNumber(formUser.getPhoneNumber());");
        user.setPhoneNumber(formUser.getPhoneNumber());

        return user;
    }
}
