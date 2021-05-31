package com.danipl.st.api.controller;

import com.danipl.st.api.constant.ProfileConstant;
import com.danipl.st.model.User;
import com.danipl.st.repository.UserRepository;
import com.danipl.st.util.Encrypt;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.AssertionErrors;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(ProfileConstant.TEST)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTests {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public void init() {

        final User testUser = User.builder().name("test_1").password(Encrypt.toMD5("test")).build();

        this.userRepository.save(testUser);
    }

    @Test
    public void testUserById() {

        ResponseEntity<User> result = this.template
                .withBasicAuth("test_1", "test")
                .getForEntity("/user/1", User.class);

        AssertionErrors.assertEquals("Is not status 200", HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testBadCredentials() {

        ResponseEntity<User> result = this.template
                .withBasicAuth("test_1", "wrong_test")
                .getForEntity("/user/1", User.class);

        AssertionErrors.assertEquals("Is not status 401", HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

}
