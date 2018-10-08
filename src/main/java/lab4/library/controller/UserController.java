package lab4.library.controller;

import lab4.library.controller.convert.FormUser;
import lab4.library.exception.PasswordException;
import lab4.library.service.RoleServiceImp;
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
import javax.validation.constraints.NotNull;

/**
 * Spring MVC controller for entity user
 * @author Кирилл
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    /**
     * The object of the service that implements the business logic for the user
     */
    @Autowired
    private UserServiceImpl userService;

    /**
     * Object for password encoding
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * The service object that implements the business logic for role
     */
    @Autowired
    private RoleServiceImp roleService;

    /**
     * The object of the service for converting
     */
    @Autowired
    private ConversionService conversionService;

    /**
     * The method returns the name jsp for user registration
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/getregistrationform")
    public String getRegistrationForm(Model model) {

        model.addAttribute("user", new FormUser());

        return "user/registrationform";
    }

    /**
     * The method checks the correctness of the entered data, converts the FormUser object to the User, saves it using the repository, and returns the name jsp to enter
     * @param formUser - object containing information about the user
     * @param bindingResult - arguments for method validation Validator
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @PostMapping(value = "/registeruser")
    public String registerUser(@ModelAttribute("user") @Valid FormUser formUser, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            return "user/registrationform";
        }

        if (formUser.getPassword().compareTo(formUser.getConfirmedPassword()) != 0) {

            model.addAttribute("invalidPassword", "Passwords are different");

            return "user/registrationform";
        }
        LOG.info("msg: conversionService.convert({}, {})", formUser.toString(), User.class);
        User user = conversionService.convert(formUser, User.class);

        LOG.info("msg: user.setPassword({})", formUser.getPassword());
        user.setPassword(formUser.getPassword());

        try {
            LOG.info("msg: userService.singupUser({}) ", user.toString());
            userService.singupUser(user);

        } catch (DataIntegrityViolationException exception) {

            LOG.error("msg: DataIntegrityViolationException", exception);

            if (exception.getMessage().contains("org.hibernate.exception.ConstraintViolationException")) {

                model.addAttribute("InvalidUsername", "This username already exists");

                model.addAttribute("user", formUser);

                return "user/registrationform";
            }
        }
        return "redirect:/user/login";
    }

    /**
     * The method informs the user about the incorrectly entered data and / or returns the name jsp to enter
     * @param error - contains information about the error of the entered data
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/login")
    public String login(Model model, String error) {

        if (error != null) {

            model.addAttribute("error", "Username or password is invalid");
        }
        return "user/login";
    }

    /**
     * The method gets information about the current user, adds it to defines a holder for model attributes, and returns the name jsp to display the profile
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/showuserprofile")
    public String showUserProfile(Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }

        LOG.info("msg: userService.getCurrentUser().getUserId()");
        Integer id = userService.getCurrentUser().getUserId();

        LOG.info("msg: userService.getUser({})", id);
        model.addAttribute("user", userService.getUser(id));

        return "user/formshowuser";
    }

    /**
     * The method gets information about the current user, adds it to defines a holder for model attributes, and returns the name jsp to edit the profile
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/getedituserform")
    public String getEditUserForm(Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }

        LOG.info("msg: userService.getCurrentUser().getUserId()");
        Integer id = userService.getCurrentUser().getUserId();

        model.addAttribute("user", conversionService.convert(userService.getUser(id), FormUser.class));

        return "user/formedituser";
    }

    /**
     * The method receives information about the user, verifies the correctness of the entered data, accesses the service to update the profile, and returns the name jsp to display the profile
     * @param formUser - object containing information about the user
     * @param bindingResult - arguments for method validation Validator
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @PostMapping(value = "/edituserprofile")
    public String editUserProfile(@ModelAttribute("user") @Valid FormUser formUser, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            return "user/formedituser";
        }

        try {
            LOG.info("msg: userService.editUserProfile({})", formUser.toString());
            userService.editUserProfile(formUser);

        } catch (DataIntegrityViolationException exception) {

            LOG.error("msg: DataIntegrityViolationException", exception);

            if (exception.getMessage().contains("org.hibernate.exception.ConstraintViolationException")) {

                model.addAttribute("error", "This username already exists");

                model.addAttribute("user", formUser);

                return "user/formedituser";
            }

        } catch (PasswordException exception) {

            model.addAttribute("error", "The current password is incorrect or new passwords are different");
            return "user/formedituser";
        }
        return "redirect:/user/showuserprofile";
    }

    /**
     * The method gets information about all users, adds it to defines a holder for model attributes, and returns the name jsp to display all users
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/getshowalluserform")
    public String getShowAllUserform(Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        LOG.info("userService.getAllUsers()");
        model.addAttribute("users", userService.getAllUsers());

        return "user/showalluserform";
    }

    /**
     * The method gets the user by id, defines its role and the roles of the current user, adds the information it receives to defines a holder for model attributes, and returns the name jsp for user editing by the administrator
     * @param userId - user id
     * @param model - defines a holder for model attributes
     * @return name
     */
    @GetMapping(value = "/getformedituserbyadmin")
    public String getFormEditUserByAdmin(@RequestParam("id") @NotNull Integer userId, Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        LOG.info("msg: userService.getUser({})", userId);
        User user = userService.getUser(userId);

        LOG.info("msg: isAdmin = false");
        Boolean isAdmin = false;

        LOG.info("msg: isModer = false");
        Boolean isModer = false;

        for (Role userRole: userService.getCurrentUser().getRoles()) {

            if (userRole.getAuthority().compareTo(Role.ROLE_ADMINISTRATOR) == 0) {

                LOG.info("msg: isAdmin = true");
                isAdmin = true;
            }
        }

        for (Role userRole: user.getRoles()) {

            if (userRole.getAuthority().compareTo(Role.ROLE_ADMINISTRATOR) == 0) {

                LOG.info("msg: isModer = false");
                isModer = false;

                LOG.info("msg: isAdmin = false");
                isAdmin = false;

            } else if (userRole.getAuthority().compareTo(Role.ROLE_MODERATOR) == 0) {

                LOG.info("msg: isModer = true");
                isModer = true;
            }
        }
        if (isAdmin == true) {

            model.addAttribute("admin", "isAdmin");
        }
        if (isModer == true) {

            model.addAttribute("moder", "isModer");
        }
        model.addAttribute("user", user);

        return "user/formedituserbyadmin";
    }

    /**
     * The method receives information about the user, verifies the correctness of the entered data, accesses the service to update the profile, and returns the name jsp to display all users
     * @param formUser - object containing information about the user
     * @param bindingResult - arguments for method validation Validator
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @PostMapping(value = "/edituserbyadmin")
    public String editUserByAdmin(@ModelAttribute("user") @Valid FormUser formUser, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            return "user/formedituser";
        }


        try {
            LOG.info("msg: userService.editUserByAdmin({})", formUser.toString());
            userService.editUserByAdmin(formUser);

        } catch (DataIntegrityViolationException exception) {

            LOG.error("msg: DataIntegrityViolationException", exception);

            if (exception.getMessage().contains("org.hibernate.exception.ConstraintViolationException")) {

                model.addAttribute("error", "This username already exists");

                model.addAttribute("user", formUser);

                return "user/formedituserbyadmin";
            }
        }
        return "redirect:/user/getshowalluserform";
    }

    /**
     * The method gets information about all users, adds it to defines a holder for model attributes, and returns the name jsp to display all users
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @GetMapping(value = "/getsubscribeform")
    public String getSubscriptionform(Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        LOG.info("userService.getAllUsers()");
        model.addAttribute("users", userService.getAllUsers());

        return "user/subscriptionform";
    }

    /**
     * The method gets information about all users, adds it to defines a holder for model attributes, and returns the name jsp to display all users
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @PostMapping(value = "/subscribe")
    public String subscribeUser(@RequestParam("props") @NotNull String props, Model model) {

        userService.subscribeUser(props);

        return "redirect:/";
    }
}
