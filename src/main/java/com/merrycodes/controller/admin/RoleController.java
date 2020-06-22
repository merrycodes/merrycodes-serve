package com.merrycodes.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.merrycodes.model.entity.Role;
import com.merrycodes.model.form.query.RoleQueryForm;
import com.merrycodes.model.vo.PaginationVo;
import com.merrycodes.model.vo.ResponseVo;
import com.merrycodes.model.vo.RoleVo;
import com.merrycodes.service.intf.RoleService;
import com.merrycodes.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MerryCodes
 * @date 2020/5/18 23:22
 */
@Api(value = "API - RoleController - admin", tags = "角色API")
@Slf4j
@RestController
@RequestMapping("/admin/role")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RoleController {

    private final RoleService roleService;

    /**
     * 角色分页查询
     *
     * @param current       当前页数
     * @param size          当前分页总页数
     * @param roleQueryForm 角色查询表当类
     * @return 角色列表实体类 (分页) {@link RoleVo}
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation(value = "获取角色列表接口", notes = "获取角色列表接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", value = "当前分页总页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "roleQueryForm", value = "角色表当查询类", dataTypeClass = RoleQueryForm.class)
    })
    public ResponseVo<PaginationVo<RoleVo>> list(@RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                                                 @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                                 RoleQueryForm roleQueryForm) {
        IPage<RoleVo> iPage = roleService.selectRolePage(current, size, roleQueryForm);
        log.info("【list 获取角色列表】 总条数={} 当前分页总页数={} 当前页数={}", iPage.getTotal(), iPage.getSize(), iPage.getCurrent());
        return ResponseUtils.success(new PaginationVo<>(iPage));
    }

    /**
     * 根据用户id查询用户的角色
     *
     * @param id 用户id
     * @return 角色集合
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation(value = "获取用户的角色列表接口", notes = "获取用户的角色列表接口")
    @ApiImplicitParam(name = "id", value = "用户id", dataTypeClass = Integer.class)
    public ResponseVo<List<Role>> selectRoleByUserId(@PathVariable("id") Integer id) {
        log.info("【selectRoleByUserId 获取用户的角色】用户id={}", id);
        List<Role> roles = roleService.selectRoleByUserId(id);
        return ResponseUtils.success(roles);
    }

    /**
     * 查询角色的名字和描述（前端用于给用户添加角色）
     *
     * @return 角色集合
     */
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation(value = "获取所有角色列表接口", notes = "获取所有角色列表接口")
    public ResponseVo<List<Role>> selectAll() {
        List<Role> roles = roleService.selectRoleWithNameDescription();
        log.info("【selectAll 获取所有角色列表】");
        return ResponseUtils.success(roles);
    }
}
