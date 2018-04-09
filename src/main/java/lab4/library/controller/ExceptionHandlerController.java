package lab4.library.controller;

import lab4.library.exception.AccessDeniedException;
import lab4.library.exception.ResourceNotFoundException;
import lab4.library.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Определяет контроллер для обработки ощибок
 * @author Кирилл
 * @version 1.0
 */
@ControllerAdvice
public class ExceptionHandlerController {

    /**
     * Объект сервиса, реализующего бизнес логики для user
     */
    @Autowired
    private UserServiceImpl userService;

    /**
     * Метод возвращает имя jsp для отображения ошибки 404
     * @param model - defines a holder for model attributes
     * @return имя jsp
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String resourceNotFound(Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }
        return "error/404";
    }

    /**
     * Метод возвращает имя jsp для отображения ошибки 403
     * @param model - defines a holder for model attributes
     * @return имя jsp
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDenied(Model model) {

        if (userService.getCurrentUser() != null) {

            model.addAttribute("username", userService.getCurrentUser().getUsername());

            if (userService.hasRole("ROLE_ADMIN")) {

                model.addAttribute("role", "admin");
            }
        }

        return "error/403";
    }
}
