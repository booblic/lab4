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
 * Defines the controller for error handling
 * @author Кирилл
 * @version 1.0
 */
@ControllerAdvice
public class ExceptionHandlerController {

    /**
     * The object of the service that implements the business logic for the user
     */
    @Autowired
    private UserServiceImpl userService;

    /**
     * The method returns the name jsp to display error 404
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String resourceNotFound(Model model) {
        userService.fillHeader(model);
        return "error/404";
    }

    /**
     * The method returns the name jsp to display error 403
     * @param model - defines a holder for model attributes
     * @return name jsp
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDenied(Model model) {
        userService.fillHeader(model);
        return "error/403";
    }
}
