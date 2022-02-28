package com.macro.mall.security.util;

import com.macro.mall.common.exception.SignException;
import com.macro.mall.security.component.filter.JwtAuthenticationTokenFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;

/**
 * @version 1.0
 * @Author lj
 * @date 2022/2/24 上午9:40
 * @desc
 */
public class SignatureUtil {

    private static Logger log = LoggerFactory.getLogger(SignatureUtil.class);

    private static final String token = "fafasdfqrqwerqew";

    public static String genSignature(Map<String, Object> params) {
        return primitive(params);
    }

    public static boolean verifySignature(Map<String, Object> params) throws UnsupportedEncodingException, SignException {
        String signRemote = params.remove("sign").toString();
        String str = primitive(params);
        byte[] bytes = str.getBytes("UTF-8");
        String signLocal = DigestUtils.md5DigestAsHex(bytes).toUpperCase();
        if (signRemote.equals(signLocal) == false) {
            log.info("\n签名错误：\n\t原始值：" + str + "\n\t服务端签名：" + signLocal + "\n\t客户端签名：" + signRemote);
            throw  new SignException("签名错误");
        }
        return true;
    }

    private static String primitive(Map<String, Object> params) {
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        String str = token;
        for (String key : keys) {
            String value = params.get(key).toString();
            str += key + value;
        }
        str += token;
        return str;
    }
}
