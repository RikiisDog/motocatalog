// package com.example.motocatalog.commons;

// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import com.example.motocatalog.services.MotorcycleAlreadyExistsException;

// @ControllerAdvice
// public class ErrorHandler {

// @ExceptionHandler(MotorcycleAlreadyExistsException.class)
// public String handleDuplicateMotoException(MotorcycleAlreadyExistsException
// ex, RedirectAttributes redirectAttrs) {
// redirectAttrs.addFlashAttribute("error", ex.getMessage());
// // 適切なリダイレクト先を返す
// return "redirect:/motos";
// }

// }
