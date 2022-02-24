package com.macro.mall.security.util;

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
    private static final String token = "fafasdfqrqwerqew";
    public static String genSignature(Map<String, Object> params) {
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        return "";
    }

    public static boolean verifySignature(Map<String, Object> params) throws UnsupportedEncodingException {
        String signRemote = params.remove("sign").toString();
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        String str = token;
        for (String key : keys) {
            String value = params.get(key).toString();
            str += key + value;
        }
        str += token;
        byte[] bytes = str.getBytes("UTF-8");
        String signLocal = DigestUtils.md5DigestAsHex(bytes);
        return signRemote == signLocal;
    }
}
