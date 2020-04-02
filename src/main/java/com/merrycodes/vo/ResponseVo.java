package com.merrycodes.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 统一返回前端对象
 *
 * @author MerryCodes
 * @date 2020/4/1 21:43
 */
@ApiModel(description = "通用返回模型")
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVo<T> {

    /**
     * 返回状态码
     */
    @ApiModelProperty("返回状态码")
    private Integer code;

    /**
     * 返回信息
     */
    @ApiModelProperty("返回信息")
    private String message;

    /**
     * 返回数据体
     */
    @ApiModelProperty("返回数据体")
    private T data;

    public ResponseVo(Integer code) {
        this.code = code;
    }

    public ResponseVo(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseVo(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseVo(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
