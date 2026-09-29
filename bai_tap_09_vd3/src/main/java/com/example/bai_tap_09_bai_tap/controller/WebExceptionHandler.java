package com.example.bai_tap_09_bai_tap.controller;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
@ControllerAdvice
public class WebExceptionHandler {
 @ExceptionHandler(EntityNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND) public String notFound(Exception ex,Model model){model.addAttribute("message",ex.getMessage());return "error";}
 @ExceptionHandler(AccessDeniedException.class) @ResponseStatus(HttpStatus.FORBIDDEN) public String forbidden(Exception ex,Model model){model.addAttribute("message",ex.getMessage());return "error";}
}
