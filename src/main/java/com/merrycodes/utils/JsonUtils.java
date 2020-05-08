package com.merrycodes.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * Jackson 工具类
 *
 * @author MerryCodes
 * @date 2020/5/7 8:40
 */
@Slf4j
public class JsonUtils {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 序列化对象
     *
     * @param object 序列化对象
     * @return {@link String}
     */
    public static Optional<String> writeValue(Object object) {
        try {
            return Optional.ofNullable(MAPPER.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            log.error("Json 序列化出错 object={} msg={}", object, e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * 解析数据为 JavaBean
     *
     * @param inputStream 输入流
     * @param cls         解析成的对象
     * @param <T>         泛型
     * @return 解析完成的对象
     */
    public static <T> Optional<T> readValue(InputStream inputStream, Class<T> cls) {
        try {
            return Optional.ofNullable(MAPPER.readValue(inputStream, cls));
        } catch (IOException e) {
            log.error("Json 解析出错 msg={}", e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * 解析数据为 JavaBean
     *
     * @param string Json字符串
     * @param cls    解析成的对象
     * @param <T>    泛型
     * @return 解析完成的对象
     */
    public static <T> Optional<T> readValue(String string, Class<T> cls) {
        try {
            return Optional.ofNullable(MAPPER.readValue(string, cls));
        } catch (IOException e) {
            log.error("Json 解析出错 msg={}", e.getMessage());
            return Optional.empty();
        }
    }


}
