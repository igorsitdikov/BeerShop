package com.gp.beershop.controller;

import com.gp.beershop.dto.UserDTO;
import com.gp.beershop.dto.UserSignInResponse;
import com.gp.beershop.exception.NoSuchUserException;
import com.gp.beershop.exception.SuchUserAlreadyExistException;
import com.gp.beershop.service.RegistrationService;
import lombok.Data;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@RestController
@BasePathAwareController
@RequestMapping("/user")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(value = "/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public final UserSignInResponse singUp(@RequestBody final UserDTO userDTO)
        throws SuchUserAlreadyExistException, NoSuchUserException {
        return registrationService.signUp(userDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public final List<UserDTO> customers() {
        return registrationService.customers();
    }
}
