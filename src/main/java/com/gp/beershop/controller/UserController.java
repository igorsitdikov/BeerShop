package com.gp.beershop.controller;

import com.gp.beershop.dto.User;
import com.gp.beershop.dto.UserSignInResponse;
import com.gp.beershop.exception.NoSuchUserException;
import com.gp.beershop.exception.SuchUserAlreadyExistException;
import com.gp.beershop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
@Tag(name = "User Management System")
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/sign-up")
    @Operation(description = "Add user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserSignInResponse singUp(
        @Valid
        @Parameter(description = "User object store in database table")
        @RequestBody final User user)
        throws SuchUserAlreadyExistException, NoSuchUserException {
        return userService.signUp(user);
    }

    @GetMapping
    @Operation(description = "View a list of available users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
        @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
    })
    @ResponseStatus(HttpStatus.OK)
    public List<User> customers() {
        return userService.customers();
    }

    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(
        @Valid
        @Parameter(description = "User Id from which user entity will delete from database table", required = true)
        @PathVariable final Long userId) throws NoSuchUserException {
        userService.deleteUser(userId);
    }

}
