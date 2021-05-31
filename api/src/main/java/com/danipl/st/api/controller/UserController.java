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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@RestController
@RequestMapping("/user")
@Api(tags = "User API")
@Secured(value = {SecurityConstant.ROLE_USER})
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Get an user by the id", response = User.class)
    @CircuitBreaker(name = "database", fallbackMethod = "getByIdFallback")
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getById(final @PathVariable("id") long id) {

        return ResponseEntity.of(this.userService.findById(id));
    }

    public ResponseEntity<User> getByIdFallback(final long id, final Exception ex) {

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

}
