package com.example.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class ClientController {
    private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);
    private final WebClient webClient;

    public ClientController(WebClient webClient) {
        this.webClient = webClient;
    }


    @GetMapping("/")
    String index(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        model.addAttribute("name", oidcUser.getIdToken().getFullName());
        model.addAttribute("token_claims", oidcUser.getIdToken().getClaims());
        model.addAttribute("userinfo_claims", oidcUser.getUserInfo().getClaims());
        return "index";
    }

    @GetMapping("/demo")
    String demo(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        model.addAttribute("name", oidcUser.getIdToken().getFullName());
        String message = webClient.get().uri("http://localhost:9091/api").retrieve().bodyToMono(String.class).block();
        model.addAttribute("message", message);

        return "demo";
    }
}
