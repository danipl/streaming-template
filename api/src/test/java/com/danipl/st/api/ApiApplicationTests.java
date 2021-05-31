package com.danipl.st.api;

import com.danipl.st.api.constant.ProfileConstant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(ProfileConstant.TEST)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiApplicationTests {

    @Test
    void contextLoads() {
    }

}
