package com.project.blog.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.exceptions.TemplateInputException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TemplateInputException.class)
    public String handleTemplateException(TemplateInputException ex, Model model) {
        ex.printStackTrace();

        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");

        return "error";
    }
}

