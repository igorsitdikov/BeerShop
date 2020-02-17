package com.gp.beershop.controller;

import com.gp.beershop.dto.Customer;
import com.gp.beershop.service.RegistrationService;
import lombok.Data;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Data
@RestController
@BasePathAwareController
@RequestMapping("/user")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(value = "/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer singUp(@RequestBody final Customer request) {
        return registrationService.signUp(request);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> customers() {
        return registrationService.customers();
    }
}
