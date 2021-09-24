package com.macro.mall.common.exception;

import com.macro.mall.common.api.ErrorCode;
import net.bytebuddy.implementation.bytecode.Throw;

/**
 * @version 1.0
 * @Author lj
 * @date 2021/9/18 2:56 下午
 * @desc
 */
public class Asserts {

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(ErrorCode errorCode) {
        throw new ApiException(errorCode);
    }

}
