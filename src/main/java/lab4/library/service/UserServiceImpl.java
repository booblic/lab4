package lab4.library.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private RoleServiceImp roleService;

    @Autowired
    private ConversionService conversionService;

    @Transactional
    public User getUser(Integer id) {
        return userRepository.getOne(id);
    }

    @Transactional
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User singupUser(User user) throws DataIntegrityViolationException {

        LOG.info("msg: user.setPassword(passwordEncoder.encode({}))", user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        LOG.info("msg: roleRepository.findOne({})", ROLE_USER_ID);
        Role userRole = roleRepository.findOne(ROLE_USER_ID);

        LOG.info("msg: user.setRoles(Collections.singleton({}))", userRole.toString());
        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {

        UserDetails userDetails = null;
        try {
            LOG.info("msg: SecurityContextHolder.getContext().getAuthentication().getPrincipal()");
            userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        } catch (ClassCastException exc) {

            return null;
        }
        User user = null;

        if (userDetails instanceof User) {

            LOG.info("msg: user = (User) userDetails");
            user = (User) userDetails;
        }
        return user;
    }

    @Override
    public boolean hasRole(String role) {

        LOG.info("msg: SecurityContextHolder.getContext().getAuthentication().getAuthorities()");
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        LOG.info("msg: hasRole = false;");
        boolean hasRole = false;

        for (GrantedAuthority authority: authorities) {

            LOG.info("msg: hasRole = {}" + authority.getAuthority().equals(role));
            hasRole = authority.getAuthority().equals(role);

            if (hasRole) {
                break;
            }
        }
        return hasRole;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LOG.info("msg: userRepository.findByUsername({});", username);
        User user = userRepository.findByUsername(username);

        if (Objects.isNull(user)) {

            throw new UsernameNotFoundException("Can't find user with username " + username);
        }
        return user;
    }

    public void editUserProfile(FormUser formUser) throws PasswordException, DataIntegrityViolationException {

        LOG.info("msg: Integer id = {}", getCurrentUser().getUserId());
        Integer id = getCurrentUser().getUserId();

        LOG.info("msg: userService.getUser({})", id);
        User currentUser = getUser(id);

        LOG.info("msg: conversionService.convert({}, {})", formUser.toString(), User.class);
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

            LOG.info("msg: user.setPassword({})", currentUser.getPassword());
            user.setPassword(currentUser.getPassword());
        }
        LOG.info("msg: user.setRoles({})", currentUser.getRoles());
        user.setRoles(currentUser.getRoles());

        LOG.info("msg: user.setReviews({})", currentUser.getReviews());
        user.setReviews(currentUser.getReviews());

        LOG.info("msg: user.setUserId({}))", currentUser.getUserId());
        user.setUserId(currentUser.getUserId());

        LOG.info("msg: updateUser({})", user.toString());
        updateUser(user);
    }

    public User editUserByAdmin(FormUser formUser) throws DataIntegrityViolationException {

        LOG.info("msg: currentUser = getUser({})", formUser.getUserId());
        User currentUser = getUser(formUser.getUserId());

        LOG.info("msg: conversionService.convert({}, {});", formUser.toString(), User.class);
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
        LOG.info("msg: roleSet.add(roleService.getRoleByAuthority({}))", Role.ROLE_USER);
        roleSet.add(roleService.getRoleByAuthority(Role.ROLE_USER));

        LOG.info("msg: user.setRoles(roleSet)");
        user.setRoles(roleSet);

        LOG.info("msg: user.setReviews(currentUser.getReviews())");
        user.setReviews(currentUser.getReviews());

        LOG.info("msg: user.setUserId({})", currentUser.getUserId());
        user.setUserId(currentUser.getUserId());

        LOG.info("msg: updateUser({})", user.toString());
        return updateUser(user);
    }
}
