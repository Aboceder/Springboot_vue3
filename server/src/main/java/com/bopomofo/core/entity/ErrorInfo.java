package com.bopomofo.core.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ErrorInfo implements Serializable {
    private static final long serialVersionUID = 3267025941101774294L;
    // 状态码
    private int status;
    // 错误信息
    private String message;
    // 错误详情
    private String details;

    public ErrorInfo(int status, String message, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
    }

    public static ErrorInfo failure(int status, String message) {
        return new ErrorInfo(status, message, null);
    }
}
