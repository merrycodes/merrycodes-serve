package com.merrycodes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.merrycodes.model.entity.UserRole;
import org.springframework.stereotype.Repository;

/**
 * 用户角色 crud操作
 *
 * @author MerryCodes
 * @date 2020/5/5 18:02
 */
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
