package com.merrycodes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.merrycodes.model.entity.Role;
import org.springframework.stereotype.Repository;

/**
 * 角色 curd操作
 *
 * @author MerryCodes
 * @date 2020/5/4 18:19
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {
}
