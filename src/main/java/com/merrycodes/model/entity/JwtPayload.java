package com.merrycodes.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "Token的载荷对象")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtPayload {

    /**
     * Tokenid
     */
    @ApiModelProperty("Tokenid")
    private String id;

    /**
     * Token中存储的User对象
     *
     * @see User
     */
    @ApiModelProperty("Token中存储的User对象")
    private User user;

    /**
     * Token过期时间
     */
    @ApiModelProperty("Token过期时间")
    private LocalDateTime expire;

    @Tolerate
    public JwtPayload() {
    }

    @JsonIgnore
    public Boolean isTokenExpired() {
        return expire.isBefore(LocalDateTime.now());
    }

}
