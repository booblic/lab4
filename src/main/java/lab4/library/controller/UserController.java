package lab4.library.controller;

import lab4.library.service.UserServiceImpl;
import lab4.library.user.User;
import lab4.library.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

/*    @Autowired
    private UserValidator userValidator;

    @InitBinder
    protected void initBuinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }*/

    @GetMapping(value = "/registrationform")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "user/registration";
    }

    /*@PostMapping(value = "/registeruser")
    public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String confirmPassword, Model model) {

        if (password.compareTo(confirmPassword) != 0) {
            model.addAttribute("error", "Passwords are different!");
            return "user/registration";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        userService.singupUser(user);

        model.addAttribute("registrationMessage", "You have successfully registered, now sign in to your account");

        return "startpage";
    }*/

    @PostMapping(value = "/registeruser")
    public String registerUser(@ModelAttribute(name = "user") @Valid User user, BindingResult bindingResult, @RequestParam String confirmPassword, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/registration";
        }

        if (user.getPassword().compareTo(confirmPassword) != 0) {
            model.addAttribute("error", "Passwords are different!");
            return "user/registration";
        }

        userService.singupUser(user);

        model.addAttribute("registrationMessage", "You have successfully registered, now sign in to your account");

        return "startpage";
    }

    @GetMapping(value = "/login")
    public String login(Model model, String error, String logout) {
        if (logout != null) {
            model.addAttribute("logout", "You have been successfully logged out.");
        }
        return "user/login";
    }

    @GetMapping(path = "/login", params = {"error"})
    public ModelAndView loginError() {
        LOG.warn("User not able to login");
        return new ModelAndView("user/login", "error", "Your username and password is invalid.");
    }

    @GetMapping(value = "/showuserprofile")
    public String showUserProfile(Model model) {
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
