package lab4.library.service;

import lab4.library.ReflectionToString;
import lab4.library.repository.RoleRepository;
import lab4.library.repository.UserRepository;
import lab4.library.user.Role;
import lab4.library.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final int ROLE_USER_ID = 1;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User singupUser(User user) throws DataIntegrityViolationException {
        LOG.info("msg: user.setPassword(passwordEncoder.encode(user.getPassword()));");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        LOG.info("msg: Role userRole = roleRepository.findOne(ROLE_USER_ID);" + ROLE_USER_ID);
        Role userRole = roleRepository.findOne(ROLE_USER_ID);

        LOG.info("msg: user.setRoles(Collections.singleton(userRole));");
        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {

        UserDetails userDetails = null;
        try {
            LOG.info("msg: userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();");
            userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException exc) {
            LOG.error("msg: ClassCastException", exc);
            return null;
        }
        User user = null;

        if (userDetails instanceof User) {
            LOG.info("msg: if (userDetails instanceof User) { user = (User) userDetails; }");
            user = (User) userDetails;
        }
        return user;
    }

    @Override
    public boolean hasRole(String role) {

        LOG.info("msg: Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();");
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        LOG.info("msg: boolean hasRole = false;");
        boolean hasRole = false;

        for (GrantedAuthority authority: authorities) {
            LOG.info("msg: for (GrantedAuthority authority: authorities) { hasRole = authority.getAuthority().equals(role); }" + authority.getAuthority().equals(role));
            hasRole = authority.getAuthority().equals(role);

            if (hasRole) {
                LOG.info("msg: if (hasRole) { break; }");
                break;
            }
        }
        return hasRole;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LOG.info("msg: User user = userRepository.findByUsername(username);" + username);
        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            LOG.error("msg: if (Objects.isNull(user)) { throw new UsernameNotFoundException(\"Can't find user with username \" + username); }");
            throw new UsernameNotFoundException("Can't find user with username " + username);
        }
        return user;
    }

    public User getUser(Integer id) {
        return userRepository.getOne(id);
    }

    public User getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
