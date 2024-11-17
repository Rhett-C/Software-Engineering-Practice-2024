package com.example.demo.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.exception.EmptyPasswordException;
import com.example.demo.exception.IncorrectOldPasswordException;
import com.example.demo.exception.NewPasswordNotEqualConfirmPasswordException;
import com.example.demo.mapper.AdminUserMapper;
import com.example.demo.mapper.ClientUserMapper;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.ThreadLocalUtil;
import com.example.demo.utils.Md5Util;

@Service
public class UserServiceImpl implements UserService {

    private final AdminUserMapper adminUserMapper;
    private final ClientUserMapper clientUserMapper;

    public UserServiceImpl(AdminUserMapper adminUserMapper, ClientUserMapper clientUserMapper) {
        this.adminUserMapper = adminUserMapper;
        this.clientUserMapper = clientUserMapper;
    }

    @Override
    public User findUserByUserId(Integer userid, boolean isAdmin) {

        if (isAdmin) {
            return adminUserMapper.findUserByUserId(userid);
        } else {
            return clientUserMapper.findUserByUserId(userid);
        }

    }

    @Override
    public User findUserByUserName(String username, boolean isAdmin) {
        if (isAdmin) {
            return adminUserMapper.findUserByUserName(username);
        } else {
            return clientUserMapper.findUserByUserName(username);
        }
    }

    @Override
    public void register(String username, String password, boolean isAdmin) {
        if (isAdmin) {
            adminUserMapper.addUser(username, Md5Util.getMD5String(password), 1);
        } else {
            clientUserMapper.addUser(username, Md5Util.getMD5String(password));
        }
    }

    @Override
    public void updateUserInfoById(User userParam, boolean isAdmin) {
        if (isAdmin) {
            adminUserMapper.updateUserInfoById(userParam);
        } else {
            clientUserMapper.updateUserInfoById(userParam);
        }
    }

    @Override
    public void updatePwd(Map<String, String> pwd, boolean isAdmin) {
        // Map<String, Object> userinfo = ThreadLocalUtil.get();
        // Integer id = (Integer) userinfo.get("id");!
        // String oldPwd = pwd.get("old_pwd");
        // String newPwd = pwd.get("new_pwd");
        // String rePwd = pwd.get("re_pwd");
        // if (StringUtils.hasLength(oldPwd) && StringUtils.hasLength(newPwd) &&
        // StringUtils.hasLength(rePwd)) {
        // if (newPwd.equals(rePwd)) {
        // // 检查原密码是否正确
        // User user = userMapper.findUserByUserId(id);
        // if (Md5Util.checkPassword(oldPwd, user.getPassword())) {
        // userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
        // } else {
        // throw new IncorrectOldPasswordException();
        // }
        // } else {
        // throw new NewPasswordNotEqualConfirmPasswordException();
        // }
        // } else {
        // throw new EmptyPasswordException();
        // }

        Map<String, Object> userinfo = ThreadLocalUtil.get();
        Integer id = (Integer) userinfo.get("id");
        String oldPwd = pwd.get("old_pwd"); // old_pwd, 旧密码
        String newPwd = pwd.get("new_pwd"); // new_pwd, 新密码
        String rePwd = pwd.get("re_pwd"); // re_pwd, 确认密码
        if (StringUtils.hasLength(oldPwd) && StringUtils.hasLength(newPwd) &&
                StringUtils.hasLength(rePwd)) {
            if (newPwd.equals(rePwd)) {
                // 检查原密码是否正确
                if (isAdmin) {
                    User user = adminUserMapper.findUserByUserId(id);
                    if (Md5Util.checkPassword(oldPwd, user.getPassword())) {
                        adminUserMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
                    } else {
                        throw new IncorrectOldPasswordException();
                    }
                } else {
                    User user = clientUserMapper.findUserByUserId(id);
                    if (Md5Util.checkPassword(oldPwd, user.getPassword())) {
                        clientUserMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
                    } else {
                        throw new IncorrectOldPasswordException();
                    }
                }
            } else {
                throw new NewPasswordNotEqualConfirmPasswordException();
            }
        } else {
            throw new EmptyPasswordException();
        }
    }

}
