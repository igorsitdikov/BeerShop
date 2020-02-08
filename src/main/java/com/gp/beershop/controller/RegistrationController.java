package com.gp.beershop.controller;

import com.gp.beershop.dto.CustomerSignUpRequest;
import com.gp.beershop.dto.IdResponse;
import com.gp.beershop.service.RegistrationService;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

//version 1
@Data
@Log
@RestController
@RequestMapping("/api/user")
public class RegistrationController {

    private final RegistrationService registrationService;
    // version 2
//    @Autowired
//    private AuthService authService;

    // version 3
//    private final AuthService authService;
//
//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }

    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public IdResponse singUp(@RequestBody final CustomerSignUpRequest request) {
        return registrationService.signUp(request);
    }
}
