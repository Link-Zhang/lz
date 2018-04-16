package com.shch.lz.ssm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * Created by Link at 11:11 on 4/16/18.
 */
public class LZAdminUtil implements InitializingBean, ServletContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(LZAdminUtil.class);

    // todo make sure
    private String CLIENT = "lz-admin-client";
    private String VERSION = "lz.admin.version";
    private String PATH = "/WEB-INF/lib/lz-admin-";
    private String JAR_SUBFIX = ".jar";
    private String RESOURCE = "/resources/lz-admin";

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        LOGGER.info("====== Start Unpacking lz-admin  ======");
        String version = PropertiesFileUtil.getInstance(CLIENT).get(VERSION);
        LOGGER.info("lz-admin.jar version:{}", version);
        String jarPath = servletContext.getRealPath(PATH + version + JAR_SUBFIX);
        LOGGER.info("lz-admin.jar path:{}", jarPath);
        String resources = servletContext.getRealPath("/") + RESOURCE;
        LOGGER.info("lz-admin.jar resources:{}", resources);
        JarUtil.decompress(jarPath, resources);
        LOGGER.info("====== Unpack lz-admin Complete ======");
    }
}
