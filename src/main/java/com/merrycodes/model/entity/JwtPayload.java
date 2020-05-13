package com.merrycodes.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Token的载荷对象
 *
 * @author MerryCodes
 * @date 2020/5/10 9:08
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtPayload {

    private String id;

    private User user;

    private LocalDateTime expire;

    @Tolerate
    public JwtPayload() {
    }

    @JsonIgnore
    public Boolean isTokenExpired() {
        return expire.isBefore(LocalDateTime.now());
    }

}
