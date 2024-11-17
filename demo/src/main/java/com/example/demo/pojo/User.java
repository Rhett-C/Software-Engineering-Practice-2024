package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class User {

    // 用户ID
    @Min(value = 1)
    @NotNull
    private Integer id;

    // 用户名
    @Pattern(regexp = "^\\S{5,16}", message = "用户名长度应在 5-16 位之间，且不能包含空白字符")
    private String username;

    // 密码
    @NotEmpty
    @JsonIgnore // 转换成 json 时会略此 field
    private String password;

    // Email
    @Email
    @NotEmpty
    private String email;

    // Phone
    @Pattern(regexp = "^1[3-9]\\d{9}") // 11 位手机号，以 1 开头，第二位是 3-9（即中国大陆的手机号）
    private String phone;

}
