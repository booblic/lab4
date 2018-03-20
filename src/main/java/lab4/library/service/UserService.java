package lab4.library.service;

import lab4.library.user.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User singupUser(User user) throws DataIntegrityViolationException;

    User getCurrentUser();

    boolean hasRole(String role);
}
