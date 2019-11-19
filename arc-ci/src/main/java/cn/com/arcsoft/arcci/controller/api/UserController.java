package cn.com.arcsoft.arcci.controller.api;

import cn.com.arcsoft.arcci.entity.User;
import cn.com.arcsoft.arcci.entity.base.RestResult;
import cn.com.arcsoft.arcci.entity.base.ResultCode;
import cn.com.arcsoft.arcci.services.IUserService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fururur
 * @create 2019-11-15-9:34
 */
@Slf4j
@RestController
@RequestMapping("api/user")
public class UserController {

    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Object login(@RequestBody User u) {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhcmMtY2kiLCJuYW1lIjoiYWRtaW4iLCJpYXQiOjE1NzQxNDQyNDN9.ncVuYOyZH0RXFvA7iiuB6LeOCQ4Da7iyL0_1BfstdDI";
        if (userService.checkUser(u))
            return new RestResult(ResultCode.SUCCESS.getCode(), new JSONObject() {{
                put("token", token);
            }});
        else
            return new RestResult(ResultCode.LOGIN_FAILED.getCode(), null);
    }

    @RequestMapping(value = "info")
    public Object info(String token) {
        log.info(token);
        User model = userService.getById(1L);
        return new RestResult(ResultCode.SUCCESS.getCode(), model);
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public Object logout(String token) {
        log.info(token);
        return new RestResult(null);
    }
}
