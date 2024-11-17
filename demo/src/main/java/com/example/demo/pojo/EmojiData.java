package com.example.demo.pojo;

import java.util.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class EmojiData {

    // 序号
    @Min(value = 1)
    @NotNull
    private Integer serialNumber;

    // 关联的用户ID
    @NotNull
    private Integer userId;

    // emoji的类型
    @NotNull
    private Integer emojiType;

    // 发送时间 (datetime)
    @NotNull
    private Date sendTime;

}
