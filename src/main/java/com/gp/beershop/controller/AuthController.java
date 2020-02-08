package com.gp.beershop.controller;

import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.dto.IdResponse;
import com.gp.beershop.service.AuthService;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Data
@Log
@RestController
@RequestMapping("/api/user")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/sign-in", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public IdResponse singUp(@RequestBody final AuthRequest request) {
        return authService.signIn(request);
    }
}
