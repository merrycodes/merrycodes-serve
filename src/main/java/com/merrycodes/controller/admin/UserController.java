package com.merrycodes.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.merrycodes.constant.enums.ResponseEnum;
import com.merrycodes.constant.enums.RoleTypeEnum;
import com.merrycodes.model.entity.Article;
import com.merrycodes.model.entity.Role;
import com.merrycodes.model.entity.User;
import com.merrycodes.model.form.ChangePasswordForm;
import com.merrycodes.model.form.UserForm;
import com.merrycodes.model.form.query.UserQueryForm;
import com.merrycodes.model.vo.PaginationVo;
import com.merrycodes.model.vo.ResponseVo;
import com.merrycodes.service.intf.RedisServce;
import com.merrycodes.service.intf.RoleService;
import com.merrycodes.service.intf.UserRoleService;
import com.merrycodes.service.intf.UserService;
import com.merrycodes.utils.CurrentUserUtils;
import com.merrycodes.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;

import static com.merrycodes.constant.consist.CacheValueConsist.*;

/**
 * 用户
 *
 * @author MerryCodes
 * @date 2020/5/8 10:35
 */
@Api(value = "API - UserController - admin", tags = "用户API")
@Slf4j
@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    private final RedisServce redisServce;

    private final UserRoleService userRoleService;

    /**
     * 获取列表列表
     *
     * @param current       当前页数
     * @param size          当前分页总条数
     * @param userQueryForm 用户查询表单类
     * @return 用户列表实体类 (分页) {@link Article}
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation(value = "获取用户列表接口", notes = "获取用户列表接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", value = "当前分页总页数", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "userQueryForm", value = "用户查询表当类", dataTypeClass = UserQueryForm.class)
    })
    public ResponseVo<PaginationVo<User>> list(@RequestParam(value = "current", required = false, defaultValue = "1") Integer current,
                                               @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                               UserQueryForm userQueryForm) {
        IPage<User> iPage = userService.selectUserPage(current, size, userQueryForm);
        log.info("【list 获取用户列表】 总条数={} 当前分页总页数={} 当前页数={}", iPage.getTotal(), iPage.getSize(), iPage.getCurrent());
        return ResponseUtils.success(new PaginationVo<>(iPage));
    }

    /**
     * 获取当前用户的角色
     *
     * @return 用户角色数组
     */
    @GetMapping("/role")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation(value = "查询当前登录用户的角色", notes = "查询当前登录用户的角色")
    public ResponseVo<String[]> getUserRole() {
        log.info("【getUserRole 获取用户角色】");
        String[] roles = userService.selectUserRole();
        return ResponseUtils.success(roles);
    }

    /**
     * 修改密码
     *
     * @param changePasswordForm 修改密码表单类
     * @return 提示信息
     */
    @PutMapping("/change-pwd")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "用户修改密码接口", notes = "用户修改密码接口")
    @ApiImplicitParam(name = "changePasswordForm", value = "修改密码表单类", dataTypeClass = ChangePasswordForm.class)
    public ResponseVo<String> changePassword(ChangePasswordForm changePasswordForm) {
        String username = CurrentUserUtils.getCurrentUsername().orElse(null);
        if (username == null) {
            log.info("【changePassword 修改密码】 未找到用户信息，重新登录");
            // 获取不到用户名，提示前端重新登录
            return ResponseUtils.fail(ResponseEnum.ILLEGAL_TOKEN);
        }
        Boolean flag = userService.changePassword(changePasswordForm, username);
        if (flag) {
            log.info("【changePassword 修改密码】 修改成功");
            return ResponseUtils.success();
        }
        log.error("【changePassword 修改密码】 修改失败");
        return ResponseUtils.fail("修改密码失败");
    }

    /**
     * 保存用户
     *
     * @param userForm 用户表单类
     * @return 添加用户的id
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "新建用户接口", notes = "新建用户接口")
    @ApiImplicitParam(name = "userForm", value = "用户表单类", dataTypeClass = UserForm.class)
    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE_VALUE_USER, beforeInvocation = true, allEntries = true),
            @CacheEvict(cacheNames = CACHE_VALUE_USER_ROLE, beforeInvocation = true, allEntries = true)
    })
    public ResponseVo<Integer> save(UserForm userForm) {
        Integer userId = userService.saveUser(userForm);
        log.info("【save 新建用户】id={}", userId);
        return ResponseUtils.success(userId);
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return {@link ResponseEnum#getCode()}
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "删除用户接口", notes = "删除用户接口")
    @ApiImplicitParam(name = "id", value = "用户id", dataTypeClass = Integer.class)
    @Caching(evict = {
            @CacheEvict(cacheNames = CACHE_VALUE_USER, beforeInvocation = true, allEntries = true),
            @CacheEvict(cacheNames = CACHE_VALUE_USER_ROLE, beforeInvocation = true, allEntries = true),
            @CacheEvict(cacheNames = CACHE_VALUE_ROLE, beforeInvocation = true, allEntries = true)
    })
    public ResponseVo<Integer> delect(@PathVariable("id") Integer id) {
        log.info("【delect 删除用户】id={}", id);
        // 删除用户
        userService.removeById(id);
        // 删除用户和角色关联
        userRoleService.deleteByUserId(id);
        // 删除Redis中的Token
        redisServce.removeObject(CACHE_VALUE_TOKEN + id);
        return ResponseUtils.success();
    }

    /**
     * 生效/失效用户
     *
     * @param userForm {@link UserForm}
     * @return {@link ResponseEnum#getCode()}
     */
    @PutMapping("/enable")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "用户生效/失效接口", notes = "用户生效/失效接口")
    @ApiImplicitParam(name = "userForm", value = "用户表单类", dataTypeClass = UserForm.class)
    public ResponseVo<Integer> updateUserEnable(UserForm userForm) {
        log.info("【updateUserEnable 用户生效/失效】 用户id='{}'----{}", userForm.getId(), userForm.getEnabled() ? "生效" : "失效");
        if (userRoleService.checkAdminRole(userForm.getId())) {
            log.error("【updateUserEnable 用户生效/失效】msg={}", ResponseEnum.ADMIN_ROLE_NO_CHANGE.getMessage());
            return ResponseUtils.fail(ResponseEnum.ADMIN_ROLE_NO_CHANGE);
        }
        if (userService.updateUserEnable(userForm)) {
            return ResponseUtils.success();
        }
        return ResponseUtils.fail("用户状态更新失败");
    }

    /**
     * 更新用户角色
     *
     * @param userId  用户Id
     * @param roleIds 角色Id
     * @return {@link ResponseEnum#getMessage()}
     */
    @PutMapping("/role")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "更新用户角色接口", notes = "更新用户角色接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "userId", value = "用户id", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "roleIds", value = "角色的id数组", dataTypeClass = Integer.class)
    })
    public ResponseVo<String> updateUserRole(@RequestParam("userId") Integer userId,
                                             @RequestParam(value = "roleData", required = false) Integer[] roleIds) {
        Boolean checkAdminRole = userRoleService.checkAdminRole(userId);
        if (roleIds == null) {
            if (checkAdminRole) {
                log.error("【updateUserRole 更新用户角色】msg={}", ResponseEnum.ADMIN_ROLE_NO_CHANGE.getMessage());
                return ResponseUtils.fail(ResponseEnum.ADMIN_ROLE_NO_CHANGE);
            }
            userService.updateUserRole(userId, Collections.emptyList());
        } else {
            Role role = roleService.selectByName(RoleTypeEnum.ADMIN.getName());
            int index = Arrays.binarySearch(roleIds, role.getId());
            if (index < 0 && checkAdminRole) {
                log.error("【updateUserRole 更新用户角色】msg={}", ResponseEnum.ADMIN_ROLE_NO_CHANGE.getMessage());
                return ResponseUtils.fail(ResponseEnum.ADMIN_ROLE_NO_CHANGE);
            }
            userService.updateUserRole(userId, Lists.newArrayList(roleIds));
        }
        log.info("【updateUserRole 更新用户角色】 用户id={}, 角色id={}", userId, roleIds);
        // 删除Redis中的Token
        redisServce.removeObject(CACHE_VALUE_TOKEN + userId);
        return ResponseUtils.success("用户分配角色成功");
    }

}

