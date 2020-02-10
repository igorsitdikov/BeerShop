package com.gp.beershop.controller;

import com.gp.beershop.dto.Customer;
import com.gp.beershop.dto.IdResponse;
import com.gp.beershop.service.RegistrationService;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//version 1
@Data
@Log
@RestController
@BasePathAwareController
@RequestMapping("/user")
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

    @PostMapping(value = "/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public IdResponse singUp(@RequestBody final Customer request) {
        return registrationService.signUp(request);
    }
}
