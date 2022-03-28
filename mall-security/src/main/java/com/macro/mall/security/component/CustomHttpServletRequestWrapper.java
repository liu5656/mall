package com.macro.mall.security.component;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.net.url.UrlQuery;
import cn.hutool.http.HttpUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @version 1.0
 * @Author lj
 * @date 2022/2/24 下午4:35
 * @desc
 */
public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public String body = null;

    public CustomHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = retrieveBody(request);
    }

    private static String retrieveBody(HttpServletRequest request) throws IOException {
        String method = request.getMethod();
        String bodyStr = "";
        if (method.equals("GET")) {
            bodyStr = request.getQueryString();
        }else if (method.equals("POST")) {
            BufferedReader reader = request.getReader();
            String line = reader.readLine();
            while(line != null) {
                bodyStr += line;
                line = reader.readLine();
            }
            bodyStr = bodyStr.replaceAll("\\s", "").replaceAll("\n", "");
        }
        return bodyStr;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
