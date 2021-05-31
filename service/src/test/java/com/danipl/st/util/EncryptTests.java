package com.danipl.st.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.AssertionErrors;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
public class EncryptTests {

    @Test
    public void testMD5Encryption() {

        final String textToEncrypt = "test";
        final String textEncripted = "098f6bcd4621d373cade4e832627b4f6";

        AssertionErrors.assertEquals("Wrong result", textEncripted, Encrypt.toMD5(textToEncrypt));
    }

    @Test
    public void testNullValueEncryption() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> Encrypt.toMD5(null));
    }

}
