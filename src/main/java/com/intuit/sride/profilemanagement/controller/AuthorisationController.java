package com.intuit.sride.profilemanagement.controller;

import com.intuit.sride.profilemanagement.api.response.AuthorizationResponse;
import com.intuit.sride.profilemanagement.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/authorised")
public class AuthorisationController {

    final AuthService authService;

    public AuthorisationController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    AuthorizationResponse isAuthorised(@PathVariable("userId") String userId, @RequestParam("resource") String resource, @RequestParam("username") String username) {
        return authService.isAuthorised(userId, username, resource);
    }
}
