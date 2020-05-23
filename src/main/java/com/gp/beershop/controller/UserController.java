package com.gp.beershop.controller;

import com.gp.beershop.dto.User;
import com.gp.beershop.dto.UserSignInResponse;
import com.gp.beershop.exception.NoSuchUserException;
import com.gp.beershop.exception.SuchUserAlreadyExistException;
import com.gp.beershop.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Data;
import org.springframework.data.rest.webmvc.BasePathAwareController;
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

@Data
@RestController
@BasePathAwareController
@RequestMapping("/users")
@Api(value = "User Management System")
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/sign-up")
    @ApiOperation(value = "Add user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserSignInResponse singUp(
        @Valid
        @ApiParam(value = "User object store in database table", required = true)
        @RequestBody final User user)
        throws SuchUserAlreadyExistException, NoSuchUserException {
        return userService.signUp(user);
    }

    @GetMapping
    @ApiOperation(value = "View a list of available users", response = List.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @ResponseStatus(HttpStatus.OK)
    public List<User> customers() {
        return userService.customers();
    }

    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(
        @Valid
        @ApiParam(value = "User Id from which user entity will delete from database table", required = true)
        @PathVariable final Long userId) throws NoSuchUserException {
        userService.deleteUser(userId);
    }

}
