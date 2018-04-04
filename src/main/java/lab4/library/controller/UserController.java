package lab4.library.controller;

import lab4.library.ReflectionToString;
import lab4.library.controller.convert.FormUser;
import lab4.library.exception.PasswordException;
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

                LOG.info("msg: model.addAttribute(\"NotUniqeUsername\", \"This username already exists\")");
                model.addAttribute("InvalidUsername", "This username already exists");

                LOG.info("msg: model.addAttribute(\"user\", formUser)");
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

        if (userService.getCurrentUser() != null) {
            LOG.info("msg: model.addAttribute(\"logout\", \"yes\");");
            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {
                LOG.info("msg: model.addAttribute(\"admin\", \"yes\");");
                model.addAttribute("role", "admin");
            }
        }

        LOG.info("msg: Integer id = userService.getCurrentUser().getUserId();");
        Integer id = userService.getCurrentUser().getUserId();

        LOG.info("msg: model.addAttribute(\"user\", userService.getUser(id));", id);
        model.addAttribute("user", userService.getUser(id));

        LOG.info("msg: return \"user/formshowuser\";");
        return "user/formshowuser";
    }

    @GetMapping(value = "/getedituserform")
    public String getEditUserForm(Model model) {

        if (userService.getCurrentUser() != null) {
            LOG.info("msg: model.addAttribute(\"logout\", \"yes\");");
            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {
                LOG.info("msg: model.addAttribute(\"admin\", \"yes\");");
                model.addAttribute("role", "admin");
            }
        }

        LOG.info("msg: Integer id = userService.getCurrentUser().getUserId();");
        Integer id = userService.getCurrentUser().getUserId();

        model.addAttribute("user", conversionService.convert(userService.getUser(id), FormUser.class));

        return "user/formedituser";
    }

    @PostMapping(value = "/edituserprofile")
    public String editUserProfile(@ModelAttribute("user") @Valid FormUser formUser, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            LOG.info("msg: return \"user/formedituser\";");
            return "user/formedituser";
        }

        try {
            userService.editUserProfile(formUser);

        } catch (DataIntegrityViolationException exception) {

            LOG.error("msg: DataIntegrityViolationException", exception);

            if (exception.getMessage().contains("org.hibernate.exception.ConstraintViolationException")) {

                LOG.info("msg: model.addAttribute(\"NotUniqeUsername\", \"This username already exists\")");
                model.addAttribute("error", "This username already exists");

                LOG.info("msg: model.addAttribute(\"user\", formUser)");
                model.addAttribute("user", formUser);

                LOG.info("msg: return \"user/registration\";");
                return "user/formedituser";
            }
        } catch (PasswordException exception) {
            model.addAttribute("error", "The current password is incorrect or new passwords are different");
            return "user/formedituser";
        }

        LOG.info("msg: return \"redirect:/user/showuserprofile\";");
        return "redirect:/user/showuserprofile";
    }

    @GetMapping(value = "/getshowalluserform")
    public String getShowAllUserform(Model model) {

        if (userService.getCurrentUser() != null) {
            LOG.info("msg: model.addAttribute(\"logout\", \"yes\");");
            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {
                LOG.info("msg: model.addAttribute(\"admin\", \"yes\");");
                model.addAttribute("role", "admin");
            }
        }

        model.addAttribute("users", userService.getAllUsers());
        return "user/showalluserform";
    }

    @GetMapping(value = "/getformedituserbyadmin")
    public String getFormEditUserByAdmin(@RequestParam("id") @NotNull Integer userId, Model model) {

        if (userService.getCurrentUser() != null) {
            LOG.info("msg: model.addAttribute(\"logout\", \"yes\");");
            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {
                LOG.info("msg: model.addAttribute(\"admin\", \"yes\");");
                model.addAttribute("role", "admin");
            }
        }

        User user = userService.getUser(userId);
        LOG.info("msg: userService.getUser({})", userId);

        LOG.info("msg: isSuperUser = false");
        Boolean isSuperUser = false;

        LOG.info("msg: isAdmin = false");
        Boolean isAdmin = false;

        for (Role userRole: userService.getCurrentUser().getRoles()) {

            if (userRole.getAuthority().compareTo(Role.ROLE_SUPER_USER) == 0) {
                LOG.info("msg: isSuperUser = true");
                isSuperUser = true;
            }
        }

        for (Role userRole: user.getRoles()) {

            if (userRole.getAuthority().compareTo(Role.ROLE_SUPER_USER) == 0) {
                LOG.info("msg: isAdmin = false");
                isAdmin = false;
                isSuperUser = false;
            } else if (userRole.getAuthority().compareTo(Role.ROLE_ADMINISTRATOR) == 0) {
                LOG.info("msg: isAdmin = true");
                isAdmin = true;
            }
        }


        if (isSuperUser == true) {
            LOG.info("msg: model.addAttribute(\"superUser\", \"yes\")");
            model.addAttribute("superUser", "isSuperUser");
        }
        if (isAdmin == true) {
            LOG.info("msg: model.addAttribute(\"admin\", \"yes\")");
            model.addAttribute("admin", "isAdministrator");
        }
        LOG.info("msg: model.addAttribute(\"user\", {}); ", user.toString());
        model.addAttribute("user", user);

        LOG.info("msg: return \"user/formedituserbyadmin\";");
        return "user/formedituserbyadmin";
    }

    @PostMapping(value = "/edituserbyadmin")
    public String editUserByAdmin(@ModelAttribute("user") @Valid FormUser formUser, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            LOG.info("msg: return \"user/formedituser\";");
            return "user/formedituser";
        }


        try {
            userService.editUserByAdmin(formUser);

        } catch (DataIntegrityViolationException exception) {

            LOG.error("msg: DataIntegrityViolationException", exception);

            if (exception.getMessage().contains("org.hibernate.exception.ConstraintViolationException")) {

                LOG.info("msg: model.addAttribute(\"NotUniqeUsername\", \"This username already exists\")");
                model.addAttribute("error", "This username already exists");

                LOG.info("msg: model.addAttribute(\"user\", formUser)");
                model.addAttribute("user", formUser);

                LOG.info("msg: return \"user/registration\";");
                return "user/formedituserbyadmin";
            }
        }

        LOG.info("msg: return \"redirect:/user/getshowalluserform\";");
        return "redirect:/user/getshowalluserform";
    }
}
