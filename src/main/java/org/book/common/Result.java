package org.book.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一API响应结果封装
 * <p>所有接口返回的数据均使用此类进行包装，保证响应格式统一</p>
 *
 * @param <T> 响应数据的类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /** 响应状态码，200表示成功，其他表示失败 */
    private int code;

    /** 响应提示信息 */
    private String message;

    /** 响应数据 */
    private T data;

    /**
     * 返回成功结果（携带数据）
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return 成功的Result对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    /**
     * 返回成功结果（无数据）
     *
     * @param <T> 数据类型
     * @return 成功的Result对象
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }

    /**
     * 返回失败结果（自定义状态码和消息）
     *
     * @param code    错误状态码
     * @param message 错误信息
     * @param <T>     数据类型
     * @return 失败的Result对象
     */
    public static <T> Result<T> fail(int code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 返回失败结果（默认500状态码）
     *
     * @param message 错误信息
     * @param <T>     数据类型
     * @return 失败的Result对象
     */
    public static <T> Result<T> fail(String message) {
        return new Result<>(500, message, null);
    }
}
