package com.gp.beershop.controller;

import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.dto.UserSignInResponse;
import com.gp.beershop.service.AuthService;
import lombok.Data;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@BasePathAwareController
@RequestMapping("/user")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public final UserSignInResponse singIn(@RequestBody final AuthRequest request) {
        return authService.signIn(request);
    }
}
