package com.gp.beershop.controller;

import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.dto.UserSignInResponse;
import com.gp.beershop.exception.NoSuchUserException;
import com.gp.beershop.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Tag(name = "User Authentication System")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/sign-in")
    @Operation(summary = "Sign-in user")
    @ApiResponses(value = {
        @ApiResponse(responseCode  = "200", description = "Successfully sign-in"),
        @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
        @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @ResponseStatus(HttpStatus.OK)
    public UserSignInResponse singIn(@RequestBody final AuthRequest request) throws NoSuchUserException {
        return authService.signIn(request);
    }
}
