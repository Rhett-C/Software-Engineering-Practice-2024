package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.Result;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.Md5Util;
import com.example.demo.utils.ThreadLocalUtil;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

// for client to access
@RestController
@Validated
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService us;
    private final StringRedisTemplate sRedisTemplate;
    private static final String TOKEN_KEY_ID = "id";
    private static final String TOKEN_KEY_USERNAME = "username";

    /**
     * @apiNote 用 token 承载的 username 查找用户
     * @param token 请求头参数 Authorization
     * @return user
     */

    @PostMapping("/register")
    public Result<String> register(@Pattern(regexp = "\\S{5,16}") String username,
            @Pattern(regexp = "\\S{5,16}") String password) {
        // 查询 username 是否已存在
        User user = us.findUserByUserName(username, false);
        if (null != user) {
            return Result.error(username + "已存在");
        }

        // 执行注册
        us.register(username, password, false);

        return Result.success();
    }

    /**
     * 
     * @param userparam 要使用@RequestBody，请求体 Content-Type 需为 application/json, 或
     *                  text/xml，只要属性名一致 Spring 会自动转化为对象
     * @return Result 响应体封装对象
     */
    @PostMapping("/registerjson")
    public Result<String> registerjson(@RequestBody User userparam) {

        // 查询 username 是否已存在
        User user = us.findUserByUserName(userparam.getUsername(), false);
        if (null != user) {
            return Result.error(userparam.getUsername() + "已存在");
        }

        // 执行注册
        us.register(userparam.getUsername(), userparam.getPassword(), false);

        return Result.success();
    }

    @PostMapping("/login")
    public Result<String> login(String username, String password) {
        // 查询 username 是否已注册
        User user = us.findUserByUserName(username, false);
        if (null == user) {
            return Result.error("用户 " + username + " 不存在");
        }
        // 校验密码
        if (Md5Util.checkPassword(password, user.getPassword())) {
            // 正确，返回Token, Jwt 的 token 只有签名部分进行了加密，防止篡改，claims 并未加密，只是用 base64 进行了重编码
            // 因此勿要传入保密信息
            HashMap<String, Object> claims = new HashMap<>();
            claims.put(TOKEN_KEY_ID, user.getId());
            claims.put(TOKEN_KEY_USERNAME, user.getUsername());
            String token = JwtUtil.genToken(claims);

            // 存入Redis
            ValueOperations<String, String> op = sRedisTemplate.opsForValue();
            op.set(username, token);
            return Result.success(token);
        }
        return Result.error("用户 " + username + " 密码错误");
    }

    @PutMapping("/logout")
    public Result<String> logout() {
        // 丢弃 token
        Map<String, Object> userInfo = ThreadLocalUtil.get();
        ValueOperations<String, String> op = sRedisTemplate.opsForValue();
        op.getOperations().delete((String) userInfo.get(TOKEN_KEY_USERNAME));
        return Result.success();
    }

    @GetMapping("/userinfo")
    public Result<User> getUserInfo() {
        Map<String, Object> userinfo = ThreadLocalUtil.get();
        Integer id = (Integer) userinfo.get(TOKEN_KEY_ID);
        return Result.success(us.findUserByUserId(id, false));
    }

    @PutMapping("/update")
    public Result<String> updateUserInfo(@RequestBody @Valid User userParam,
            @RequestHeader("Authorization") String token) {
        // 由于 updateUserInfoById 根据 id 来更新 user，若登录后利用 token 修改此 API id 参数为其它用户 id，
        // 将会导致修改其他用户的信息，因此需检验 userParam.id 是否与 token 一致，防止恶意篡改id。
        Map<String, Object> userinfo = ThreadLocalUtil.get();
        Integer id = (Integer) userinfo.get(TOKEN_KEY_ID);
        String username = (String) userinfo.get(TOKEN_KEY_USERNAME);
        // 修改用户信息
        userParam.setId(id);
        userParam.setUsername(username);
        us.updateUserInfoById(userParam, false);
        return Result.success();
    }

    // @PatchMapping("/updateAvatar")
    // public Result<String> updateAvatar(@RequestParam("avatarUrl") @URL String
    // avatarUrl) {
    // us.updateAvatar(avatarUrl);
    // return Result.success();
    // }

    /**
     * @param pwd like {
     *            "old_pwd":"123456",
     *            "new_pwd":"234567",
     *            "re_pwd":"234567"
     *            }
     * @return Result<String>
     */
    @PatchMapping("/updatePwd")
    public Result<String> updatePwd(@RequestBody Map<String, String> pwd) {
        // UserService 校验参数，抛出自定义异常，由全局异常处理器处理
        us.updatePwd(pwd, false);
        ValueOperations<String, String> op = sRedisTemplate.opsForValue();
        Map<String, Object> userinfo = ThreadLocalUtil.get();
        // 消除 Redis 中的 token 副本，修改了密码，需重新获取 token
        op.getOperations().delete((String) userinfo.get(TOKEN_KEY_USERNAME));
        return Result.success();
    }
}
