package lab4.library.service;

import lab4.library.user.User;
import org.h2.jdbc.JdbcSQLException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User singupUser(User user) throws Exception;

    User getCurrentUser();

    boolean hasRole(String role);
}
