package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.pojo.User;

@Mapper
public interface AdminUserMapper {

    @Select("select * from emoji_project.admin_user where id=#{userid}")
    User findUserByUserId(Integer userid);

    @Select("select * from emoji_project.admin_user where username=#{username}")
    User findUserByUserName(String username);

    @Insert("insert into emoji_project.admin_user(username, password, authorityLevel) values(#{username},#{md5Password},#{authorityLevel})")
    void addUser(String username, String md5Password, Integer authorityLevel);

    @Update("update emoji_project.admin_user set username=#{username},email=#{email},phone=#{phone} where id=#{id}")
    void updateUserInfoById(User user);

    @Update("update emoji_project.admin_user set password=#{newPwd} where id=#{id}")
    void updatePwd(String newPwd, Integer id);
}
