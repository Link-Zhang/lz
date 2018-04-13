package com.shch.lz.ssm.util;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.util.Properties;

/**
 * Created by Link at 12:15 on 4/9/18.
 */
public class VelocityUtil {
    public static void generate(String inputVmFilePath, String outputFilePath, VelocityContext velocityContext) throws Exception {
        try {
            Properties properties = new Properties();
            properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, getPath(inputVmFilePath));
            Velocity.init(properties);
            Template template = Velocity.getTemplate(getFileName(inputVmFilePath), "utf-8");
            File outputFile = new File(outputFilePath);
            FileWriterWithEncoding writerWithEncoding = new FileWriterWithEncoding(outputFile, "utf-8");
            template.merge(velocityContext, writerWithEncoding);
            writerWithEncoding.close();
        } catch (Exception e) {
            throw e;
        }
    }

    public static String getPath(String filePath) {
        String path = "";
        if (StringUtils.isNotBlank(filePath)) {
            path = filePath.substring(0, filePath.lastIndexOf("/") + 1);
        }
        return path;
    }

    public static String getFileName(String filePath) {
        String file = "";
        if (StringUtils.isNotBlank(filePath)) {
            file = filePath.substring(filePath.lastIndexOf("/") + 1);
        }

        return file;
    }
}

