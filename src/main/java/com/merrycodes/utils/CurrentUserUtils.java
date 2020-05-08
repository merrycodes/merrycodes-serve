package com.merrycodes.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * 获取当前的用户名
 *
 * @author MerryCodes
 * @date 2020/5/8 10:32
 */
public class CurrentUserUtils {

    public static Optional<String> getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return Optional.ofNullable((String) authentication.getPrincipal());
        }
        return Optional.empty();
    }

}
