package com.example.server;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class DemoApi {

    @GetMapping("/api")
    String api(@AuthenticationPrincipal Jwt jwt) {
        return String.format("It works for '%s'   ---    Token Claims: %s", fullName(jwt), jwt.getClaims());
    }

    private String fullName(Jwt jwt) {
        return String.format("%s %s", jwt.getClaimAsString("given_name"), jwt.getClaimAsString("family_name"));
    }
}
