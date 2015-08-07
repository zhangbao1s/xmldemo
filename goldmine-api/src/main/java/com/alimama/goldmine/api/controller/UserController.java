package com.alimama.goldmine.api.controller;

import com.adchina.api.bean.Response;
import com.adchina.api.security.IgnoreSecurity;
import com.alimama.goldmine.api.bean.UserBean;
import com.alimama.goldmine.api.param.LoginParam;
import com.alimama.goldmine.api.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 *
 * @author huangyong
 * @since 1.0.0
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     */
    @IgnoreSecurity
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(
        @Valid LoginParam param
    ) {
        String username = param.getUsername();
        String password = param.getPassword();
        boolean result = userService.login(username, password);
        if (result) {
            UserBean userBean = new UserBean();
            userBean.setUsername(username);
            return new Response().success(userBean);
        } else {
            return new Response().failure("login_failure");
        }
    }
}
