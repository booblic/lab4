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

        LOG.info("msg: formUser.setUsername({})", user.getUsername());
        formUser.setUsername(user.getUsername());

        LOG.info("msg: formUser.setFirstName({})", user.getFirstName());
        formUser.setFirstName(user.getFirstName());

        LOG.info("msg: formUser.setLastName({})", user.getLastName());
        formUser.setLastName(user.getLastName());

        LOG.info("msg: formUser.setMiddleName({})", user.getMiddleName());
        formUser.setMiddleName(user.getMiddleName());

        LOG.info("msg: formUser.setEmail({})", user.getEmail());
        formUser.setEmail(user.getEmail());

        LOG.info("msg: formUser.setPhoneNumber({})", user.getPhoneNumber());
        formUser.setPhoneNumber(user.getPhoneNumber());

        return formUser;
    }
}
