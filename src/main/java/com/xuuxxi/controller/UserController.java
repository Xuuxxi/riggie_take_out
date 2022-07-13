package com.xuuxxi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuuxxi.common.R;
import com.xuuxxi.entity.User;
import com.xuuxxi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author: Xuuxxi
 * @Date: 2022/5/14
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info("user login,map = {}",map.toString());

        String phone = map.get("phone").toString();

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone,phone);

        User userInfo = userService.getOne(wrapper);

        if(userInfo == null){
            userInfo = new User();
            userInfo.setPhone(phone);
            userInfo.setStatus(1);
            userService.save(userInfo);
        }

        session.setAttribute("user",userInfo.getId());
        return R.success(userInfo);
    }

    @PostMapping("/loginout")
    public R<String> logout(HttpServletRequest request){
        log.info("log out ...");
        request.getSession().removeAttribute("user");
        return R.success("注销成功");
    }
}
