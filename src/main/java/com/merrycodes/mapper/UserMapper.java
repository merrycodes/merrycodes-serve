package com.merrycodes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.merrycodes.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户 curd操作
 *
 * @author MerryCodes
 * @date 2020/5/4 18:17
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户和用户的角色
     *
     * @param username 用户名
     * @return 用户 {@link User}
     */
    User selectBynameWithRole(@Param("username") String username);

}
