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
 * Spring MVC controller для сущности user
 * @author Кирилл
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    /**
     * Объект сервиса, реализующего бизнес логики для user
     */
    @Autowired
    private UserServiceImpl userService;

    /**
     * Объект для кодирования паролей
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Объект сервиса, реализующего бизнес логики для role
     */
    @Autowired
    private RoleServiceImp roleService;

    /**
     * Объект сервиса для конвертаций
     */
    @Autowired
    private ConversionService conversionService;

    /**
     * Метод возвращает имя jsp для регистрации пользователей
     * @param model - defines a holder for model attributes
     * @return имя jsp
     */
    @GetMapping(value = "/getregistrationform")
    public String getRegistrationForm(Model model) {

        model.addAttribute("user", new FormUser());

        return "user/registrationform";
    }

    /**
     * Метод проверяет правильность введенных данных, конвертирует объект FormUser в User, сохраняет его используя репозиторий и возвращает имя jsp для входа
     * @param formUser - объект, содержащий информацию о пользователе
     * @param bindingResult - аргумента для метода проверки Validator
     * @param model - defines a holder for model attributes
     * @return имя jsp
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
     * Метод сообщает пользователю об неправлиьно введных данных и/или возвращает имя jsp для входа
     * @param error - содержит информацию об ошибки введных данных
     * @param model - defines a holder for model attributes
     * @return имя jsp
     */
    @GetMapping(value = "/login")
    public String login(Model model, String error) {

        if (error != null) {

            model.addAttribute("error", "Username or password is invalid");
        }
        return "user/login";
    }

    /**
     * Метод получает информацию о текущем пользователе, добаляет ее в defines a holder for model attributes и возвращает имя jsp для отображения профиля
     * @param model - defines a holder for model attributes
     * @return имя jsp
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
     * Метод получает информацию о текущем пользователе, добаляет ее в defines a holder for model attributes и возвращает имя jsp для редактирования профиля
     * @param model - defines a holder for model attributes
     * @return имя jsp
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
     * Метод получает информацию о пользователе, проверяет правильность введенных данных, обращается к сервису для обновления профиля и возвращает имя jsp для отображения профиля
     * @param formUser - объект, содержащий информацию о пользователе
     * @param bindingResult - аргумента для метода проверки Validator
     * @param model - defines a holder for model attributes
     * @return имя jsp
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
     * Метод получает информацию о всех пользователях, добаляет ее в defines a holder for model attributes и возвращает имя jsp для отображения всех пользователей
     * @param model - defines a holder for model attributes
     * @return имя jsp
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
     * Метод получает пользователя по id, определяет его роли и роли текущего пользователя, добавляет полученную информацию в defines a holder for model attributes и возвращает имя jsp для редактирования пользователя администратором
     * @param userId - id поьзователя
     * @param model - defines a holder for model attributes
     * @return имя jsp
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
     * Метод получает информацию о пользователе, проверяет правильность введенных данных, обращается к сервису для обновления профиля и возвращает имя jsp для отображения всех пользователей
     * @param formUser - объект, содержащий информацию о пользователе
     * @param bindingResult - аргумента для метода проверки Validator
     * @param model - defines a holder for model attributes
     * @return имя jsp
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
}
