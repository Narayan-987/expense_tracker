package com.expense.controller;

import com.expense.dto.SignupRequest;
import com.expense.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(
            UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {

        return "signup";
    }

    @PostMapping("/signup")
    public String signup(
            SignupRequest request
    ) {

        userService.register(request);

        return "redirect:/login";
    }
}