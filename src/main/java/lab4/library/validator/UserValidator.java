package lab4.library.validator;

import lab4.library.repository.UserRepository;
import lab4.library.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> paramClass) {
        return User.class.equals(paramClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        User user = (User) obj;
        User savedUser = userRepository.findByUsername(user.getUsername());
        if (Objects.nonNull(savedUser) && savedUser.getUsername().equals(user.getUsername())) {
            errors.rejectValue("username", "user.username.nonUnique");
        }
    }
}
