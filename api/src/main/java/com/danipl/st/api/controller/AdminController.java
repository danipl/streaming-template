package com.danipl.st.api.controller;

import com.danipl.st.api.constant.SecurityConstant;
import com.danipl.st.model.User;
import com.danipl.st.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@RestController
@RequestMapping("/admin")
@Api(tags = "Admin API")
@Secured(value = {SecurityConstant.ROLE_ADMIN})
public class AdminController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Get all users", response = User.class)
    @CircuitBreaker(name = "database", fallbackMethod = "getAllUsersFallback")
    @GetMapping(value = "/user/all")
    public ResponseEntity<Collection<User>> getAllUsers() {

        return ResponseEntity.ok(this.userService.getAll());
    }

    public ResponseEntity<Collection<User>> getAllUsersFallback(final Exception ex) {

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

}