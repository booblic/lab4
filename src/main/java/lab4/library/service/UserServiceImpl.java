package lab4.library.service;

import lab4.library.ReflectionToString;
import lab4.library.controller.convert.FormUser;
import lab4.library.exception.PasswordException;
import lab4.library.repository.RoleRepository;
import lab4.library.repository.UserRepository;
import lab4.library.user.Role;
import lab4.library.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private RoleService roleService;

    @Autowired
    private ConversionService conversionService;

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
            //LOG.info("msg: userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();");
            userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException exc) {
            //LOG.error("msg: ClassCastException", exc);
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

    public void editUserProfile(FormUser formUser) throws PasswordException, DataIntegrityViolationException {

        LOG.info("msg: Integer id = {}", getCurrentUser().getUserId());
        Integer id = getCurrentUser().getUserId();

        LOG.info("msg: userService.getUser({})", id);
        User currentUser = getUser(id);

        LOG.info("msg: conversionService.convert(formUser, User.class)");
        User user = conversionService.convert(formUser, User.class);

        if (formUser.getOldPassword() != null && formUser.getPassword() != null && formUser.getConfirmedPassword() != null) {
            if (passwordEncoder.matches(formUser.getOldPassword(), getCurrentUser().getPassword()) && formUser.getPassword().compareTo(formUser.getConfirmedPassword()) == 0) {
                LOG.info("msg: user.setPassword(passwordEncoder.encode({}))", formUser.getPassword());
                user.setPassword(passwordEncoder.encode(formUser.getPassword()));

            } else {
                throw new PasswordException();
            }
        }

        if (user.getPassword() == null) {
            LOG.info("msg: user.setPassword(currentUser.getPassword());");
            user.setPassword(currentUser.getPassword());
        }
        LOG.info("msg: user.setRoles(currentUser.getRoles());", currentUser.getRoles());
        user.setRoles(currentUser.getRoles());

        LOG.info("msg: user.setReviews(currentUser.getReviews());", currentUser.getReviews());
        user.setReviews(currentUser.getReviews());

        LOG.info("msg: user.setUserId(currentUser.getUserId());", currentUser.getUserId());
        user.setUserId(currentUser.getUserId());

        updateUser(user);
    }

    public User editUserByAdmin(FormUser formUser) throws DataIntegrityViolationException {

        LOG.info("msg: formUser; " + ReflectionToString.reflectionToString(formUser));
        User currentUser = getUser(formUser.getUserId());

        LOG.info("msg: userService.getUser({})", formUser.getUserId());
        User user = conversionService.convert(formUser, User.class);

        if (formUser.getDropPassword() == null) {
            LOG.info("msg: user.setPassword({})", currentUser.getPassword());
            user.setPassword(currentUser.getPassword());
        } else {
            LOG.info("msg: user.setPassword(passwordEncoder.encode(\"12345\"))");
            user.setPassword(passwordEncoder.encode("12345"));
        }

        Set<Role> roleSet = new HashSet<>();
        if (formUser.getRole() != null) {
            LOG.info("msg: roleSet.add(roleService.getRoleByAuthority({}))", formUser.getRole());
            roleSet.add(roleService.getRoleByAuthority(formUser.getRole()));
        }
        LOG.info("msg: roleSet.add(roleService.getRoleByAuthority(\"ROLE_USER\"))");
        roleSet.add(roleService.getRoleByAuthority("ROLE_USER"));

        LOG.info("msg: user.setRoles(roleSet);", roleSet);
        user.setRoles(roleSet);

        LOG.info("msg: user.setReviews(currentUser.getReviews())");
        user.setReviews(currentUser.getReviews());

        LOG.info("msg: user.setUserId(currentUser.getUserId())");
        user.setUserId(currentUser.getUserId());

        LOG.info("msg: userService.updateUser(user)", user);
        return updateUser(user);
    }
}
