package com.example.demo.pojo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false) // AdminUser 与 ClientUser 不共用 ID
public class AdminUser extends User {

    // 权限等级
    @Min(value = 1)
    @NotNull
    private Integer authorityLevel;

}
