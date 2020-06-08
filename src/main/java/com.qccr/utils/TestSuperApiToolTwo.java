package com.qccr.utils;


import org.springframework.util.LinkedMultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hecs
 * @date 2020-06-04 17:23
 */
public class TestSuperApiToolTwo {
    public static final String TEST_SOURCE = "1";
    public static final String TEST_VERSION = "2.0";
    public static final String TEST_APPKEY = "6458783";

    public static void setupParams(Map<String, String> vars, String data, LinkedMultiValueMap<String, String> headers) {
        Map paramsMap = new HashMap();
        String secrect = "04083118-538f-410e-9104-044f88687122";

        //请求头信息
        paramsMap.put("user-agent", "QiCheChaoRen/3.7.0(iOS/10.0.1; iPhone 6/7/8; 375*667; WeChat/7.0.4; SDKVersion/2.10.3)");
        paramsMap.put("area-id", "22");
        paramsMap.put("imei", "wx-90990060-5cf1-4f62-9f5e-474d84cecf6d");
        paramsMap.put("session-id","wx-90990060-5cf1-4f62-9f5e-474d84cecf6d");
        paramsMap.put("u-session-id", "cc70d83705174768b5b91b8a24aaa913");

        paramsMap.put("source", "1");
        paramsMap.put("version", "2.0");
        paramsMap.put("appkey", "6458783");
        String timestamp = String.valueOf(System.currentTimeMillis());
        paramsMap.put("timestamp", timestamp);
        paramsMap.put("data", data);
        String sign = SignUtils.signParamsSHA1(paramsMap, secrect);

        headers.add("Cookie", "u-session-id=cc70d83705174768b5b91b8a24aaa913;session-id=wx-90990060-5cf1-4f62-9f5e-474d84cecf6d");
        headers.add("area-id", "22");
        headers.add("imei","wx-90990060-5cf1-4f62-9f5e-474d84cecf6d");
//        headers.add("content-type", "application/x-www-form-urlencoded");

        vars.put("source", "1");
        vars.put("version", String.valueOf(2.0D));
        vars.put("appkey", "6458783");
        vars.put("timestamp", timestamp);
        vars.put("sign", sign);
        vars.put("data", data);
        vars.put("source", "1");
        vars.put("user-agent", "QiCheChaoRen/3.7.0(iOS/10.0.1; iPhone 6/7/8; 375*667; WeChat/7.0.4; SDKVersion/2.10.3)");
//        return vars;
    }
}

