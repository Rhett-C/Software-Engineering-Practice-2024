package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.pojo.User;

@Mapper
public interface ClientUserMapper {

    @Select("select * from emoji_project.client_user where id=#{userid}")
    User findUserByUserId(Integer userid);

    @Select("select * from emoji_project.client_user where username=#{username}")
    User findUserByUserName(String username);

    @Insert("insert into emoji_project.client_user(username, password) values(#{username},#{md5Password})")
    void addUser(String username, String md5Password);

    @Update("update emoji_project.client_user set username=#{username},email=#{email},phone=#{phone} where id=#{id}")
    void updateUserInfoById(User user);

    @Update("update emoji_project.client_user set password=#{newPwd} where id=#{id}")
    void updatePwd(String newPwd, Integer id);
}
