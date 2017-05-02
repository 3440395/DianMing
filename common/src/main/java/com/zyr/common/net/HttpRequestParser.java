package com.zyr.common.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 转码
 * Created by xuekai on 2017/5/2.
 */

public class HttpRequestParser {
    public static Map<String, String> parse(HttpRequest request) throws IOException {
        return parse(request,false);
    }

    public static Map<String, String> parse(HttpRequest request, boolean lowerCaseNames) throws IOException {
        String content = getContent(request);
        return splitHttpParams(content, lowerCaseNames);
    }

    /**
     * Split http params.
     *
     * @param content        target content.
     * @param lowerCaseNames key to lowercase.
     * @return parameter key-value pairs.
     */
    public static Map<String, String> splitHttpParams(String content, boolean lowerCaseNames) throws UnsupportedEncodingException {
         content = URLDecoder.decode(content, "utf-8");
        Map<String, String> paramMap = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(content, "&");
        while (tokenizer.hasMoreElements()) {
            String keyValue = tokenizer.nextToken();
            int index = keyValue.indexOf("=");
            if (index > 0) {
                String key = keyValue.substring(0, index);
                if (lowerCaseNames)
                    key = key.toLowerCase(Locale.ENGLISH);
                paramMap.put(key, keyValue.substring(index + 1));
            }
        }
        return paramMap;
    }
    /**
     * The access request body.
     *
     * @param request {@link HttpRequest}.
     * @return string raw.
     * @throws IOException if it is a POST request IO exception might occur when get the data.
     */
    public static String getContent(HttpRequest request) throws IOException {
        HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
        String s = EntityUtils.toString(entity, "utf-8");
        return s;
    }
}
