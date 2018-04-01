package lab4.library.controller;

import lab4.library.ReflectionToString;
import lab4.library.controller.convert.FormUser;
import lab4.library.service.RoleService;
import lab4.library.service.UserServiceImpl;
import lab4.library.user.Role;
import lab4.library.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ConversionService conversionService;

    @GetMapping(value = "/getregistrationform")
    public String getRegistrationForm(Model model) {
        LOG.info("msg:  model.addAttribute(\"user\", new User());");
        model.addAttribute("user", new FormUser());
        LOG.info("msg: return \"user/registration\";");
        return "user/registrationform";
    }

    @PostMapping(value = "/registeruser")
    public String registerUser(@ModelAttribute("user") @Valid FormUser formUser, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            LOG.info("msg: if (bindingResult.hasErrors()) { return \"user/registration\"; }");
            return "user/registrationform";
        }

        if (formUser.getPassword().compareTo(formUser.getConfirmedPassword()) != 0) {
            model.addAttribute("invalidPassword", "Passwords are different!");
            return "user/registrationform";
        }
        LOG.info("msg: User user = conversionService.convert(formUser, User.class); " + ReflectionToString.reflectionToString(formUser));
        User user = conversionService.convert(formUser, User.class);

        user.setPassword(formUser.getPassword());

        try {
            LOG.info("msg: userService.singupUser(user); " + ReflectionToString.reflectionToString(user));
            userService.singupUser(user);
        } catch (DataIntegrityViolationException exception) {
            LOG.error("msg: DataIntegrityViolationException", exception);
            if (exception.getMessage().contains("org.hibernate.exception.ConstraintViolationException")) {
                LOG.info("msg: if (exception.getMessage().contains(\"org.hibernate.exception.ConstraintViolationException\")) { model.addAttribute(\"NotUniqeUsername\", \"This username already exists\"); model.addAttribute(\"user\", formUser); }");
                model.addAttribute("InvalidUsername", "This username already exists");
                model.addAttribute("user", formUser);
                LOG.info("msg: return \"user/registration\";");
                return "user/registrationform";
            }
        }
        LOG.info("mgs: return \"startpage\";");

        return "redirect:/user/login";
    }

    @GetMapping(value = "/login")
    public String login(Model model, String error, String logout) {

        if (error != null) {
            LOG.info("msg: if (error != null) { model.addAttribute(\"error\", \"Your username and password is invalid.\"); }");
            model.addAttribute("error", "Username or password is invalid");
        }

        LOG.info("msg: return \"user/login\";");
        return "user/login";
    }

    @GetMapping(value = "/showuserprofile")
    public String showUserProfile(Model model) {
        LOG.info("msg: Integer id = userService.getCurrentUser().getUserId();");
        Integer id = userService.getCurrentUser().getUserId();
        LOG.info("msg: model.addAttribute(\"user\", userService.getUser(id));", id);
        model.addAttribute("user", userService.getUser(id));
        LOG.info("msg: return \"user/formshowuser\";");
        return "user/formshowuser";
    }

    @GetMapping(value = "/getedituserform")
    public String getEditUserForm(Model model) {
        LOG.info("msg: Integer id = userService.getCurrentUser().getUserId();");
        Integer id = userService.getCurrentUser().getUserId();
        LOG.info("msg: model.addAttribute(\"user\", userService.getUser(id));", id);
        model.addAttribute("user", userService.getUser(id));
        return "user/formedituser";
    }

    @PostMapping(value = "/edituserprofile")
    public String editUserProfile(@ModelAttribute("user") @Valid FormUser formUser, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            LOG.info("msg: return \"user/formedituser\";");
            return "user/formedituser";
        }
        LOG.info("msg: Integer id = userService.getCurrentUser().getUserId();");
        Integer id = userService.getCurrentUser().getUserId();
        LOG.info("msg: User currentUser = userService.getUser(id);", id);
        User currentUser = userService.getUser(id);
        LOG.info("msg: User user = conversionService.convert(formUser, User.class); " + ReflectionToString.reflectionToString(formUser));
        User user = conversionService.convert(formUser, User.class);

        if (formUser.getOldPassword() != null && formUser.getPassword() != null && formUser.getConfirmedPassword() != null) {
            if (passwordEncoder.matches(formUser.getOldPassword(), userService.getCurrentUser().getPassword()) && formUser.getPassword().compareTo(formUser.getConfirmedPassword()) == 0) {
                LOG.info("msg: if (formUser.getOldPassword() != null && formUser.getPassword() != null && formUser.getConfirmedPassword() != null) { " +
                        "if (passwordEncoder.matches(formUser.getOldPassword(), userService.getCurrentUser().getPassword()) && formUser.getPassword().compareTo(formUser.getConfirmedPassword()) == 0) { " +
                        "user.setPassword(passwordEncoder.encode(formUser.getPassword()));");
                user.setPassword(passwordEncoder.encode(formUser.getPassword()));

            } else {
                LOG.info("msg: model.addAttribute(\"error\", \"Invalid password\");");
                model.addAttribute("error", "The current password is incorrect or new passwords are different");
                LOG.info("msg: return \"user/formedituser\";");
                return "user/formedituser";
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

        LOG.info("msg: userService.updateUser(user); " + ReflectionToString.reflectionToString(user));
        userService.updateUser(user);

        LOG.info("msg: return \"redirect:/user/showuserprofile\";");
        return "redirect:/user/showuserprofile";
    }

    @GetMapping(value = "/getshowalluserform")
    public String getshowalluserform(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/showalluserform";
    }

    @GetMapping(value = "/getformedituserbyadmin")
    public String getFormEditUserByAdmin(@RequestParam("id") @NotNull Integer userId, Model model) {

        User user = userService.getUser(userId);
        LOG.info("msg: User user = userService.getUser(userId); " + userId + " " + ReflectionToString.reflectionToString(user));

        LOG.info("msg: Boolean isSuperUser = false;");
        Boolean isSuperUser = false;

        LOG.info("msg: Boolean isAdmin = false;");
        Boolean isAdmin = false;

        for (Role userRole: userService.getCurrentUser().getRoles()) {

            if (userRole.getAuthority().compareTo(Role.ROLE_SUPER_USER) == 0) {
                LOG.info("msg: for (Role userRole: user.getRoles()) { if (userRole.getRoleName().compareTo(\"SuperUser\") == 0) { isSuperUser = true; } }");
                isSuperUser = true;
            }
        }

        for (Role userRole: user.getRoles()) {

            if (userRole.getAuthority().compareTo(Role.ROLE_SUPER_USER) == 0) {
                LOG.info("msg: for (Role userRole: user.getRoles()) { if (userRole.getRoleName().compareTo(\"SuperUser\") == 0) { isAdmin = false; } }");
                isAdmin = false;
                isSuperUser = false;
            } else if (userRole.getAuthority().compareTo(Role.ROLE_ADMINISTRATOR) == 0) {
                LOG.info("msg: for (Role userRole: user.getRoles()) { if (userRole.getRoleName().compareTo(\"Administrator\") == 0) { isAdmin = true; } }");
                isAdmin = true;
            }
        }


        if (isSuperUser == true) {
            LOG.info("msg: if (isSuperUser == true) { model.addAttribute(\"superUser\", \"yes\"); }");
            model.addAttribute("superUser", "isSuperUser");
        }
        if (isAdmin == true) {
            LOG.info("msg: if (isAdmin == true) { model.addAttribute(\"admin\", \"yes\"); }");
            model.addAttribute("admin", "isAdministrator");
        }
        LOG.info("msg: model.addAttribute(\"user\", user); " + ReflectionToString.reflectionToString(user));
        model.addAttribute("user", user);
        LOG.info("msg: return \"user/formedituserbyadmin\";");
        return "user/formedituserbyadmin";
    }

    @PostMapping(value = "/edituserbyadmin")
    public String editUserByAdminForm(@ModelAttribute("user") @Valid FormUser formUser, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            LOG.info("msg: return \"user/formedituser\";");
            return "user/formedituser";
        }
        LOG.info("msg: formUser; " + ReflectionToString.reflectionToString(formUser));
        User currentUser = userService.getUser(formUser.getUserId());
        LOG.info("msg: User currentUser = userService.getUser(formUser.getUserId()); " + ReflectionToString.reflectionToString(currentUser));
        User user = conversionService.convert(formUser, User.class);
        LOG.info("msg: User user = conversionService.convert(formUser, User.class); " + ReflectionToString.reflectionToString(user));

        if (formUser.getOldPassword() != null && formUser.getPassword() != null && formUser.getConfirmedPassword() != null) {

            if (passwordEncoder.matches(formUser.getOldPassword(), currentUser.getPassword()) && formUser.getPassword().compareTo(formUser.getConfirmedPassword()) == 0) {
                LOG.info("msg: if (formUser.getOldPassword() != null && formUser.getPassword() != null && formUser.getConfirmedPassword() != null) { " +
                        "if (passwordEncoder.matches(formUser.getOldPassword(), userService.getCurrentUser().getPassword()) && formUser.getPassword().compareTo(formUser.getConfirmedPassword()) == 0) { " +
                        "user.setPassword(passwordEncoder.encode(formUser.getPassword()));");
                user.setPassword(passwordEncoder.encode(formUser.getPassword()));

            } else {
                LOG.info("msg: model.addAttribute(\"error\", \"Invalid password\");");
                model.addAttribute("error", "The current password is incorrect or new passwords are different");
                LOG.info("msg: return \"user/formedituser\";");
                return "user/formedituser";
            }
        }

        if (user.getPassword() == null) {
            LOG.info("msg: user.setPassword(currentUser.getPassword());");
            user.setPassword(currentUser.getPassword());
        }

        Set<Role> roleSet = new HashSet<>();
        if (formUser.getRole() != null) {
            LOG.info("msg: if (formUser.getRole() != null) { roleSet.add(roleService.getRoleByAuthority(formUser.getRole())); }", formUser.getRole());
            roleSet.add(roleService.getRoleByAuthority(formUser.getRole()));
        }
        LOG.info("msg: roleSet.add(roleService.getRoleByAuthority(\"ROLE_USER\"));");
        roleSet.add(roleService.getRoleByAuthority("ROLE_USER"));

        LOG.info("msg: user.setRoles(roleSet);", roleSet);
        user.setRoles(roleSet);

        LOG.info("msg: user.setReviews(currentUser.getReviews());");
        user.setReviews(currentUser.getReviews());
        LOG.info("msg: user.setUserId(currentUser.getUserId());");
        user.setUserId(currentUser.getUserId());

        LOG.info("msg: userService.updateUser(user); " + ReflectionToString.reflectionToString(user));
        userService.updateUser(user);

        LOG.info("msg: return \"redirect:/user/getshowalluserform\";");
        return "redirect:/user/getshowalluserform";
    }
}
