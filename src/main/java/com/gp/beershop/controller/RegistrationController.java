package com.gp.beershop.controller;

import com.gp.beershop.dto.Customer;
import com.gp.beershop.dto.IdResponse;
import com.gp.beershop.service.RegistrationService;
import lombok.Data;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Data
@RestController
@BasePathAwareController
@RequestMapping("/user")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(value = "/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public IdResponse singUp(@RequestBody final Customer request) {
        return registrationService.signUp(request);
    }
}
