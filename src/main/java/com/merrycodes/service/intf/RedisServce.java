package com.merrycodes.service.intf;

/**
 * Redis缓存Service接口
 *
 * @author MerryCodes
 * @date 2020/6/6 17:12
 */
public interface RedisServce {

    /**
     * 存储数据
     *
     * @param key         key值
     * @param value       存储数据
     * @param expiredTime 过期时间（分钟）
     */
    void putObject(String key, Object value, Long expiredTime);

    /**
     * 获取数据
     *
     * @param key key值
     * @return 存储的数据
     */
    Object getObject(String key);

    /**
     * 移除数据
     *
     * @param key key值
     */
    void removeObject(String key);

}
