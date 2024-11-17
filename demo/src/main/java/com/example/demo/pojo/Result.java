package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//统一响应结果
@NoArgsConstructor
@AllArgsConstructor
@Data
/**
 * @author rlyslata
 * @since 2024-1-30
 * @NoArgsConstructor，AllArgsConstructor 指示 Lombok 在编译时生成构造函数
 * @Data 指示Lombok在编译时生成getters，setters
 */
public class Result<T> {
    /**
     * @apiNote 快速返回操作成功响应结果(带响应数据)
     * @param <E>  被封装类型
     * @param data 被封装对象
     * @return Result<E>
     */
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "操作成功", data);
    }

    /**
     * @apiNote 快速返回操作成功响应结果
     * @return Result<Object>
     */
    public static Result<String> success() {
        return new Result<>(0, "操作成功", null);
    }

    /**
     * @apiNote 操作失败
     * @param message 操作消息
     * @return Result<Object>
     */
    public static Result<String> error(String message) {
        return new Result<>(1, message, null);
    }

    private Integer code;// 业务状态码 0-成功 1-失败

    private String message;// 提示信息

    private T data;// 响应数据
}
