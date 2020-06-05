package com.merrycodes.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.merrycodes.model.entity.Role;
import com.merrycodes.model.vo.RoleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 角色 curd操作
 *
 * @author MerryCodes
 * @date 2020/5/4 18:19
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 角色分页查询 包括对应用户的数量
     *
     * @param page    简单分页模型 {@link Page}
     * @param wrapper 条件构造抽象类 {@link Wrapper}
     * @return 分页 Page 对象接口 {@link IPage}
     */
    IPage<RoleVo> selectRolePageWithUserCount(Page<RoleVo> page, @Param(Constants.WRAPPER) Wrapper<Role> wrapper);

}
