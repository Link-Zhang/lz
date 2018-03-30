package com.shch.lz.ssm.plugin;

import com.shch.lz.ssm.util.AesUtil;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * Created by Link at 10:22 on 3/30/18.
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private String[] propertyNames = {"master.jdbc.password", "slave.jdbc.password", "generator.jdbc.password", "master.redis.password"};

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        for (String p : propertyNames) {
            if (p.equalsIgnoreCase(propertyName)) {
                return AesUtil.aesDecode(propertyValue);
            }
        }
        return super.convertProperty(propertyName, propertyValue);
    }
}
