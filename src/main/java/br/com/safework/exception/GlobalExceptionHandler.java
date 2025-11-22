package br.com.safework.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class, NoHandlerFoundException.class})
    public String notFound(Exception ex, Model model) {
        model.addAttribute("messageKey", "error.notfound");
        model.addAttribute("details", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String internal(Exception ex, Model model) {
        model.addAttribute("messageKey", "error.internal");
        model.addAttribute("details", ex.getMessage());
        return "error";
    }
}
