package com.demo.app.api.controllers;

import com.demo.app.api.requests.AuthRequest;
import com.demo.app.api.responses.ApiResponse;
import com.demo.models.data.AuthUser;
import com.demo.services.IUserService;
import com.demo.services.dto.user.SaveUser;
import com.demo.services.dto.user.UserDetail;
import com.demo.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {
    private final IUserService usrSvc;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody SaveUser body) throws Exception {
        if (usrSvc.existsByUsername(body.getUsername())) {
            return ApiResponse.badRequest("Username has already existed");
        }
        if (usrSvc.existsByEmail(body.getEmail())) {
            return ApiResponse.badRequest("Email has already existed");
        }
         return ApiResponse.created(
                UserDetail.fromEntity(usrSvc.save(null, body)));
    }

    @PostMapping("login")
    public ResponseEntity<?> login(
            @RequestBody AuthRequest request
    ) throws Exception {
        var usr = usrSvc.findByUsername(request.getUsername());
        if (usr == null) {
            return ApiResponse.badRequest("Username does not exist");
        }
        if (!passwordEncoder.matches(request.getPassword(), usr.getPassword())) {
            return ApiResponse.badRequest("Password is incorrect");
        }

        var accessToken = usrSvc.invokeAccessToken(usr.getUsername());
        var jwt = jwtUtil.generateToken(usr.getUsername(), accessToken);

        return ApiResponse.ok(Collections.singletonMap("token", jwt));
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout() throws Exception {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var usrDetail = (AuthUser)auth.getPrincipal();
        usrSvc.invalidateAccessToken(usrDetail.getUsername());
        return ApiResponse.noContent();
    }
}

