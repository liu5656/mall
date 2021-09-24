package com.macro.mall.common.api;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/3 3:57 下午
 * @desc
 */
public class CommonResult<T> {

    private long code;
    private String message;
    private T data;

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    protected CommonResult() {
    }

    public void setCode(long code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    protected CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T>  CommonResult<T> success(T data) {
        CommonResult res = new CommonResult<T>(com.macro.mall.common.api.ResultCode.SUCCESS.getCode(), null, data);
        return  res;
    }
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(com.macro.mall.common.api.ResultCode.SUCCESS.getCode(), message, data);
    }
    public static <T> CommonResult<T> failed(ErrorCode err) {
        return new CommonResult<>(err.getCode(), err.getMessage(), null);
    }
    public static <T> CommonResult<T> failed(String mes) {
        return new CommonResult<>(ResultCode.FAILED.getCode(), mes, null);
    }
    public static <T> CommonResult<T> failed() {
        return failed(ResultCode.FAILED);
    }

    public static <T> CommonResult<T> validateFailed(String mes) {
        return new CommonResult<>(ResultCode.FAILED.getCode(), mes, null);
    }

    public static <T> CommonResult<T> unauthorized() {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), null);
    }
    public static <T> CommonResult<T> unauthorized(String message) {
        return new CommonResult<>(ResultCode.UNAUTHORIZED.getCode(), message, null);
    }

    public static <T> CommonResult<T> forbidden(String message) {
        return new CommonResult<>(ResultCode.FORBIDDEN.getCode(), message, null);
    }
}
