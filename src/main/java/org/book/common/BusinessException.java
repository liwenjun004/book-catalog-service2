package org.book.common;

import lombok.Getter;

/**
 * 业务异常类
 * <p>用于表示业务逻辑中的可预期异常，如参数校验失败、资源不存在等</p>
 * <p>该异常会被 {@link GlobalExceptionHandler} 捕获并转换为统一的响应格式</p>
 */
@Getter
public class BusinessException extends RuntimeException {

    /** 错误状态码 */
    private final int code;

    /**
     * 构造业务异常（默认500状态码）
     *
     * @param message 异常信息
     */
    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    /**
     * 构造业务异常（自定义状态码）
     *
     * @param code    错误状态码
     * @param message 异常信息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }
}
