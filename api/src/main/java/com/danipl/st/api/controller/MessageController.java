package com.danipl.st.api.controller;

import com.danipl.st.api.constant.SecurityConstant;
import com.danipl.st.dto.SendMessageDto;
import com.danipl.st.exception.ApiException;
import com.danipl.st.model.User;
import com.danipl.st.service.MessageService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@RestController
@RequestMapping("/message")
@Api(tags = "Message API")
@Secured(value = {SecurityConstant.ROLE_ADMIN, SecurityConstant.ROLE_USER})
public class MessageController {

    @Autowired
    private MessageService messageService;

    @ApiOperation(value = "Send a single message to the specified user.")
    @CircuitBreaker(name = "kafka", fallbackMethod = "sendMessageFallback")
    @PostMapping(value = "/send")
    public ResponseEntity sendMessage(@Valid @RequestBody final SendMessageDto sendMessageDto, final Authentication authentication) {

        Assert.isInstanceOf(User.class, authentication.getDetails());

        this.messageService.sendMessage((User) authentication.getDetails(), sendMessageDto);

        return ResponseEntity.ok(null);
    }

    public ResponseEntity sendMessageFallback(final SendMessageDto sendMessageDto, final Authentication authentication, final Exception ex) {

        if (ex instanceof ApiException) {

            throw (ApiException) ex;
        }

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

}
