package com.icloud.my_portfolio.controller;


import com.icloud.my_portfolio.domain.User;
import com.icloud.my_portfolio.exception.UserEmailDuplicateException;
import com.icloud.my_portfolio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/register")
    public String register() {
        return "account/register";
    }

    @PostMapping("/register")
    public String register(User user, Model model) {
        try {
            userService.join(user);
        } catch (UserEmailDuplicateException e) {
            model.addAttribute("errorMsg", user.getEmail() + "은 이미 있는 이메일입니다.");
            return "/account/register";
        }
        return "redirect:/";
    }
}
