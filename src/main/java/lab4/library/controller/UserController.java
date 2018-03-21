package lab4.library.controller;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ConversionService conversionService;

    @GetMapping(value = "/registrationform")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "user/registration";
    }

    @PostMapping(value = "/registeruser")
    public String registerUser(@ModelAttribute("user") @Valid FormUser formUser, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/registration";
        }

        if (formUser.getPassword().compareTo(formUser.getConfirmedPassword()) != 0) {
            model.addAttribute("differentPassword", "Passwords are different!");
            return "user/registration";
        }

        User user = conversionService.convert(formUser, User.class);

        try {
            userService.singupUser(user);
        } catch (DataIntegrityViolationException exception) {
            if (exception.getMessage().contains("org.hibernate.exception.ConstraintViolationException")) {
                model.addAttribute("NotUniqeUsername", "This username already exists");
                model.addAttribute("user", formUser);
                return "user/registration";
            }
        }

        model.addAttribute("registrationMessage", "You have successfully registered, now sign in to your account");

        return "startpage";
    }

    @GetMapping(value = "/login")
    public String login(Model model, String error, String logout) {

        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }

        if (logout != null) {
            model.addAttribute("logout", "You have been successfully logged out.");
        }
        return "user/login";
    }

    @GetMapping(value = "/showuserprofile")
    public String showUserProfile(Model model) {
        User user = userService.getCurrentUser();
        logger.info("userService.getCurrentUser()", user);
        model.addAttribute("user", user);
        return "user/formshowuser";
    }

    @GetMapping(value = "/edituser")
    public String editUser(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "user/formedituser";
    }

    @PostMapping(value = "/edituserprofile")
    public String editUserProfile(@ModelAttribute("user") @Valid FormUser formUser, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/formedituser";
        }

        User user = null;

        User currentUser = userService.getCurrentUser();

        if (userService.hasRole("ROLE_ADMIN")) {
            currentUser = userService.getUser(formUser.getFormUserId());
        }

        if (formUser.getOldPassword() != null && formUser.getPassword() != null && formUser.getConfirmedPassword() != null) {
            if (passwordEncoder.matches(formUser.getOldPassword(), userService.getCurrentUser().getPassword()) && formUser.getPassword().compareTo(formUser.getConfirmedPassword()) == 0) {
                user = conversionService.convert(formUser, User.class);
                user.setPassword(passwordEncoder.encode(formUser.getPassword()));
                if (userService.hasRole("ROLE_ADMIN")) {
                    Set<Role> roleSet = new HashSet<>();
                    for (String roleName: formUser.getRoles()) {
                        if (roleName.compareTo("Administrator") == 0) {
                            roleSet.add(roleService.getRole(2));
                        }
                    }
                    roleSet.add(roleService.getRole(1));
                    user.setRoles(roleSet);
                } else {
                    user.setRoles(currentUser.getRoles());
                }
                user.setReviews(currentUser.getReviews());
                user.setUserId(currentUser.getUserId());
                userService.updateUser(user);
            } else {
                model.addAttribute("error", "Invalid password");
                return "user/formedituser";
            }
        }

        if (user == null) {
            user = conversionService.convert(formUser, User.class);
            user.setPassword(currentUser.getPassword());
            user.setReviews(currentUser.getReviews());
            if (userService.hasRole("ROLE_ADMIN")) {
                Set<Role> roleSet = new HashSet<>();
                for (String roleName: formUser.getRoles()) {
                    if (roleName.compareTo("Administrator") == 0) {
                        roleSet.add(roleService.getRole(2));
                    }
                }
                roleSet.add(roleService.getRole(1));
                user.setRoles(roleSet);
            } else {
                user.setRoles(currentUser.getRoles());
            }
            user.setUserId(currentUser.getUserId());
            userService.updateUser(user);
        }

        return "redirect:/user/showuserprofile";
    }

    @GetMapping(value = "/getshowalluserform")
    public String getshowalluserform(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/showalluserform";
    }

    @PostMapping(value = "/{id}/edituserbyadmin")
    public String editUserByAdmin(@PathVariable(value = "id") Integer id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("admin", "yes");
        model.addAttribute("user", user);
        System.out.println("-------------------------------------------------------------------");
        return "user/formedituser";
    }
}
