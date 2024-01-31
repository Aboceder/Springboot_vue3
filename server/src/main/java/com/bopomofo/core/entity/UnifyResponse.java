package com.bopomofo.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回实体
 */
@Data
@Schema(description = "统一返回实体")
public class UnifyResponse<T> implements Serializable {
    private static final long serialVersionUID = -938386465205350121L;
    /**
     * 此属性可用于将用户重定向到指定的URL。
     */
    @Schema(description = "重定向url")
    private String targetUrl;
    /**
     * 指示结果的成功状态。
     * 如果此值为false，则设置<see ref=“Error”/>。
     */
    @Schema(description = "结果的成功状态")
    private boolean success;
    /**
     * 错误详细（必须且仅当<see ref=“Success”/>为false时设置）
     */
    @Schema(description = "错误详细")
    private ErrorInfo error;
    /**
     * 返回结果
     */
    @Schema(description = "返回结果")
    private T result;
    /**
     * 响应的特殊签名。它在客户机中用于检测这是否是由bopomofo包装的响应。
     */
    private boolean __bopomofo = true;

    public UnifyResponse() {
        this.success = true;
    }


    public UnifyResponse(T result) {
        this.success = true;
        this.result = result;
    }

    public UnifyResponse(ErrorInfo error, boolean success) {
        this.success = success;
        this.error = error;
    }

    public static UnifyResponse<Object> success() {
        return new UnifyResponse<>();
    }
}

