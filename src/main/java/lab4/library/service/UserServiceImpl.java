package lab4.library.service;

import lab4.library.repository.RoleRepository;
import lab4.library.repository.UserRepository;
import lab4.library.user.Role;
import lab4.library.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private static final int ROLE_USER_ID = 1;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User singupUser(User user) throws Exception {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findOne(ROLE_USER_ID);

        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {

        UserDetails userDetails = null;
        try {
            userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException exc) {
            return null;
        }
        User user = null;

        if (userDetails instanceof User) {
            user = (User) userDetails;
        }
        return user;
    }

    @Override
    public boolean hasRole(String role) {

        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        boolean hasRole = false;

        for (GrantedAuthority authority: authorities) {
            hasRole = authority.getAuthority().equals(role);

            if (hasRole) {
                break;
            }
        }
        return hasRole;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Can't find user with username " + username);
        }
        return user;
    }

    public User getUser(Integer id) {
        return userRepository.getOne(id);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
