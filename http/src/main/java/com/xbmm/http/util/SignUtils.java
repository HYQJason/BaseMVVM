package com.xbmm.http.util;

import android.text.TextUtils;

import java.util.Map;
import java.util.TreeMap;

public class SignUtils {


    /**
     * 签名
     *
     * @param params 参数
     * @return md5
     */
    public static String AppSign(Map<String, Object> params) {

        StringBuilder paramValues = new StringBuilder();

        for (Map.Entry<String, Object> entry : params.entrySet()) {

            if (null == entry || null == entry.getValue() || TextUtils.isEmpty(String.valueOf(entry.getValue()))) {
                continue;
            }

            paramValues.append(entry.getValue());
        }

        if (paramValues.length() < 1) {
            paramValues.append("hxmall@empty");
        }


        return MD5Utils.getMD5Code(paramValues.toString());
    }
}
