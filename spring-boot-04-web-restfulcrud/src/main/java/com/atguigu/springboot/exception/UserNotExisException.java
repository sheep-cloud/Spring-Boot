package com.atguigu.springboot.exception;

/**
 * @author: colg
 */
public class UserNotExisException extends RuntimeException {

    public UserNotExisException() {
        super("用户不存在");
    }

}
