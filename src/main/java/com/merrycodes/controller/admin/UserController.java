package com.merrycodes.controller.admin;

import com.merrycodes.model.vo.ResponseVo;
import com.merrycodes.service.intf.UserService;
import com.merrycodes.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户
 *
 * @author MerryCodes
 * @date 2020/5/8 10:35
 */
@Slf4j
@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {

    private final UserService userService;

    @GetMapping("/role")
    public ResponseVo<String[]> getUserRole() {
        String[] roles = userService.selectUserRole();
        return ResponseUtils.success(roles);
    }

}

