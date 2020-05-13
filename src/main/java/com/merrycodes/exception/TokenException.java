package com.merrycodes.exception;

import com.merrycodes.constant.enums.ResponseEnum;
import lombok.Getter;

/**
 * @author MerryCodes
 * @date 2020/5/12 14:21
 */
@Getter
public class TokenException extends RuntimeException {

    private final Integer code;

    public TokenException(ResponseEnum responseEnum) {

        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }

    public TokenException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
