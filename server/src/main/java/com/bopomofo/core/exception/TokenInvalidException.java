package com.bopomofo.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 令牌失效
 */
public class TokenInvalidException extends AuthenticationException {
    private static final long serialVersionUID = -8359366644023599056L;

    public TokenInvalidException(String msg) {
        super(msg);
    }
}
