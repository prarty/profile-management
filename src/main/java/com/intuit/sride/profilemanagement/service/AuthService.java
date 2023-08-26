package com.intuit.sride.profilemanagement.service;

import com.intuit.sride.profilemanagement.api.response.AuthorizationResponse;
import com.intuit.sride.profilemanagement.exception.UserNotFoundException;
import com.intuit.sride.profilemanagement.model.*;
import com.intuit.sride.profilemanagement.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.security.Key;
import java.util.*;

@Service
public class AuthService {

    private final String SECRET = "eb87cc3949d7cbc49a5fd62dd25b191baa5ad4118f437c672bbcb4c47a6ad283";

    @Autowired
    UserRepository userRepository;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();


    public String generateToken(String username, Long driverId) {
        //Header payload and signature all the component are claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("id", driverId);
        return createToken(claims);
    }

    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] key = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(key);
    }

    private boolean getAuthoritiesByEmail(String email, String resource, String userId) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(String.format("User not found for email ", email)));

        Permission permissions = user.getRole().getPermissions();
        Set<Resource> resources = permissions.getResources();

        return resources.stream().anyMatch(protectedResource -> antPathMatcher.match(protectedResource.getResourceId(), resource));
    }

    public AuthorizationResponse isAuthorised(String userId, String username, String resource) {
        return new AuthorizationResponse(getAuthoritiesByEmail(username, resource, userId));
    }
}
