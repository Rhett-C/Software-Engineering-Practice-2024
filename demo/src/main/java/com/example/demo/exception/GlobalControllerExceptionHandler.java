package com.example.demo.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.pojo.Result;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(IncorrectOldPasswordException.class)
    public Result<String> incorrectOldPasswordExceptionHandler(IncorrectOldPasswordException e) {
        e.printStackTrace();
        return Result.error("原密码错误");
    }

    @ExceptionHandler(NewPasswordNotEqualConfirmPasswordException.class)
    public Result<String> newPasswordNotEqualConfirmPasswordExceptionHandler(
            NewPasswordNotEqualConfirmPasswordException e) {
        e.printStackTrace();
        return Result.error("新密码与确认密码不一致");
    }

    @ExceptionHandler(EmptyPasswordException.class)
    public Result<String> emptyPasswordExceptionHandler(EmptyPasswordException e) {
        e.printStackTrace();
        return Result.error("密码不能为空");
    }

    @ExceptionHandler(Exception.class)
    public Result<String> userControllerExceptionHander(Exception e) {
        e.printStackTrace();
        return Result.error(e.getMessage() + "操作失败");
    }

}
