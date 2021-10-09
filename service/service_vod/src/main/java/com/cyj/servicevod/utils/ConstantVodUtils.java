package com.cyj.servicevod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantVodUtils implements InitializingBean {

    @Value("LTAI5tAfR8NwcS9MEupkpBjW")
    private String keyid;

    @Value("cAimkGO6b85TUzcPjPcW8fl54tC9wB")
    private String keysecret;

    public static String ACCESS_KEY_SECRET;
    public static String ACCESS_KEY_ID;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
    }
}
