package com.danipl.st.util;

import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
public class Encrypt {

    private Encrypt() {
    }

    public static String toMD5(final String text) {

        Assert.notNull(text, "Text cannot be null.");

        return DigestUtils.md5DigestAsHex(text.getBytes());
    }

}
