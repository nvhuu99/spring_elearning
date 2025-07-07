package com.demo.app.admin.controllers;

import com.demo.app.api.requests.AuthRequest;
import com.demo.app.api.responses.ApiResponse;
import com.demo.exceptions.errors.ErrorsBag;
import com.demo.models.data.AuthUser;
import com.demo.services.IUserService;
import com.demo.services.dto.user.SaveUser;
import com.demo.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;

@Controller
@RequestMapping("admin")
@AllArgsConstructor
public class AdminAuthController extends AdminBaseController {
    private final IUserService usrSvc;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("register")
    public String showRegister(Model model) {
        model.addAttribute("user", new SaveUser());
        model.addAttribute("errors", new HashMap<String, String>());
        return "admin/auth/register";
    }

    @PostMapping("register")
    public String register(
            @Valid @ModelAttribute("user") SaveUser body,
            BindingResult bindingResult,
            Model model
    ) throws Exception {
        if (usrSvc.existsByUsername(body.getUsername())) {
            bindingResult.rejectValue("username", null, "Username already exists");
        }
        if (usrSvc.existsByEmail(body.getEmail())) {
            bindingResult.rejectValue("email", null, "Email already exists");
        }
        if (bindingResult.hasErrors()) {
            var errBag = ErrorsBag.parseValidationException(bindingResult.getAllErrors());
            model.addAttribute("errors", errBag.getPlainMessages());
            return "admin/auth/register";
        }
        usrSvc.save(null, body);
        return "redirect:/admin/login";
    }

    @GetMapping("login")
    public String showLogin(Model model) {
        model.addAttribute("user", new AuthRequest());
        model.addAttribute("errors", new HashMap<String, String>());
        return "admin/auth/login";
    }

    @PostMapping("login")
    public String login(
            @Valid @ModelAttribute("user") AuthRequest request,
            BindingResult bindingResult,
            HttpServletResponse response,
            Model model
    ) throws Exception {
        var usr = usrSvc.findByUsername(request.getUsername());
        if (usr == null) {
            bindingResult.rejectValue("username", null, "Username does not exist");
        } else if (!passwordEncoder.matches(request.getPassword(), usr.getPassword())) {
            bindingResult.rejectValue("password", null, "Password is incorrect");
        }
        if (bindingResult.hasErrors()) {
            var errBag = ErrorsBag.parseValidationException(bindingResult.getAllErrors());
            model.addAttribute("errors", errBag.getPlainMessages());
            return "admin/auth/login";
        }

        var accessToken = usrSvc.invokeAccessToken(usr.getUsername());
        var jwt = jwtUtil.generateToken(usr.getUsername(), accessToken);
        var cookie = ResponseCookie.from("JWT", jwt)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(24 * 60 * 60) // 24 hour
                .build();
        response.addHeader("Set-Cookie", cookie.toString());

        return "redirect:/admin/categories";
    }

    @GetMapping("logout")
    public String logout() throws Exception {
        if (isLoggedIn()) {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            var usrDetail = (AuthUser)auth.getPrincipal();
            usrSvc.invalidateAccessToken(usrDetail.getUsername());
        }
        return "redirect:/admin/login";
    }
}

