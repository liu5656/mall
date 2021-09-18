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
        CommonResult res = new CommonResult<T>(com.macro.mall.tiny.common.ResultCode.SUCCESS.getCode(), null, data);
        return  res;
    }
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(com.macro.mall.tiny.common.ResultCode.SUCCESS.getCode(), message, data);
    }
    public static <T> CommonResult<T> failed(ErrorCode err) {
        return new CommonResult<>(err.getCode(), err.getMessage(), null);
    }
}
