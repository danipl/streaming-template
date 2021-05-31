package com.danipl.st.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Daniel Pardo Ligorred<daniel.pardo@adidas.com>
 * @since 2021-04
 **/
public final class ApiException extends IllegalStateException {

    private final HttpStatus httpStatus;

    public ApiException(final HttpStatus httpStatus, final Throwable cause) {

        super(cause);
        this.httpStatus = httpStatus;
    }

    public ApiException(final String message, final HttpStatus httpStatus, final Throwable cause) {

        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {

        return super.getMessage() != null && !super.getMessage().isEmpty()
                ? super.getMessage()
                : this.httpStatus.getReasonPhrase();
    }

}
