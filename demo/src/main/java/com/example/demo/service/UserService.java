package com.example.demo.service;

import java.util.Map;

import com.example.demo.pojo.User;

public interface UserService {

    User findUserByUserId(Integer userid, boolean isAdmin);

    User findUserByUserName(String username, boolean isAdmin);

    void register(String username, String password, boolean isAdmin);

    void updateUserInfoById(User userParam, boolean isAdmin);

    void updatePwd(Map<String, String> pwd, boolean isAdmin);

}
