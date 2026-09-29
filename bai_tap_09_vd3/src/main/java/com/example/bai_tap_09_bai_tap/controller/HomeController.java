package com.example.bai_tap_09_bai_tap.controller;

import com.example.bai_tap_09_bai_tap.security.CustomUserDetails;
import com.example.bai_tap_09_bai_tap.service.UserService;
import com.example.bai_tap_09_bai_tap.service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService userService;
    private final ProductService productService;

    public HomeController(UserService userService, ProductService productService) { this.userService = userService; this.productService = productService; }

    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails principal) model.addAttribute("principal", principal);
        model.addAttribute("userCount", userService.countUsers());
        model.addAttribute("productCount", productService.countProducts());
        return "home";
    }
}
