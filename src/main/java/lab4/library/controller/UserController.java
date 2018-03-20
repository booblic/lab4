package lab4.library.controller;

import lab4.library.service.UserServiceImpl;
import lab4.library.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/registrationform")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "user/registration";
    }

    @PostMapping(value = "/registeruser")
    public String registerUser(@ModelAttribute(name = "user") @Valid User user, BindingResult bindingResult, @RequestParam String confirmPassword, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/registration";
        }

        if (user.getPassword().compareTo(confirmPassword) != 0) {
            model.addAttribute("error", "Passwords are different!");
            return "user/registration";
        }

        try {
            userService.singupUser(user);
        } catch (DataIntegrityViolationException exception) {
            if (exception.getMessage().contains("org.hibernate.exception.ConstraintViolationException")) {
                model.addAttribute("NotUniqeUsername", "This username already exists");
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
        logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        User currentUser = userService.getCurrentUser();
        Integer id = currentUser.getUserId();
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "user/formshowuser";
    }

    @PostMapping(value = "/{id}/edituser")
    public String editUser(@PathVariable Integer id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "user/formedituser";
    }

    @PostMapping(value = "/{id}/edituserprofile")
    public String editUserProfile(@PathVariable Integer id, @ModelAttribute(name = "user") @Valid User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/registration";
        }

        if (user.getUsername() != null && user.getConfirmedPassword() != null) {

            if (user.getPassword().compareTo(user.getConfirmedPassword()) != 0) {
                model.addAttribute("error", "Passwords are different!");
                return "user/registration";
            }
            user.setConfirmedPassword(null);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        user.setUserId(id);

        userService.updateUser(user);

        return "redirect:/user/showuserprofile";
    }
}
