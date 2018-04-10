package com.shch.lz.ssm.util;

import org.apache.commons.lang.ObjectUtils;
import org.apache.velocity.VelocityContext;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Link at 13:04 on 4/8/18.
 */
    public class MybatisGeneratorUtil {
    private static String generatorConfig_vm = "/template/generatorConfig.vm";
    private static String service_vm = "/template/Service.vm";
    private static String serviceMock_vm = "/template/ServiceMock.vm";
    private static String serviceImpl_vm = "/template/ServiceImpl.vm";
    private static String DATE_FORMAT_PATTERN = "M/d/yy";
    private static String TIME_FORMAT_PATTERN = "HH:mm";
    private static String TARGET_CLASSES_DIRECTORY = "/target/classes/";
    private static String JAVA_SOURCE_DIRECTORY = "/src/main/java/";
    private static String RESOURCE_SOURCE_DIRECTORY = "/src/main/resources/";
    private static String RPC_SERVICE_SUFFIX = "-rpc-service";
    private static String RPC_SERVICE_IMPL_SUFFIX = "-rpc-service-impl";
    private static String RPC_API_SUFFIX = "-rpc-api";
    private static String DAO_SUFFIX = "-dao";
    private static String MODEL_PACKAGE_SUBFFIX = ".dao.model";
    private static String MAPPER_PACKAGE_SUBFFIX = ".dao.mapper";
    private static String SERVICE_JAVA_SUBFFIX = "Service.java";
    private static String SERVICE_MOCK_JAVA_SUBFFIX = "ServiceMock.java";
    private static String SERVICE_IMPL_JAVA_SUBFFIX = "ServiceImpl.java";
    private static String GENERATOR_CONFIG_XML_NAME = "generatorConfig.xml";

    public static void main(String[] args) throws Exception {
        String createDate = new SimpleDateFormat(DATE_FORMAT_PATTERN).format(new Date());
        String createTime = new SimpleDateFormat(TIME_FORMAT_PATTERN).format(new Date());
        System.out.println(createDate);
        System.out.println(createTime);
//        MybatisGeneratorUtil.generator("jdbcDriver", "jdbcUrl", "jdbcUsername", "jdbcPassword", "module", "database", "tablePrefix", "packageName", null);
    }

    public static void generator(String jdbcDriver, String jdbcUrl, String jdbcUsername, String jdbcPassword, String module, String database, String tablePrefix, String packageName, Map<String, String> lastInsertIdTables) throws Exception {
        MybatisGeneratorUtil.osHandler();
        String targetProject = module + "/" + module + DAO_SUFFIX;
        // todo basePath exists bugs
        String basePath = MybatisGeneratorUtil.class.getResource("/").getPath().replace(TARGET_CLASSES_DIRECTORY, "").replace(targetProject, "").replaceFirst("/", "");
        String generatorConfigXmlPath = MybatisGeneratorUtil.class.getResource("/").getPath().replace
                (TARGET_CLASSES_DIRECTORY, "") + RESOURCE_SOURCE_DIRECTORY + GENERATOR_CONFIG_XML_NAME;
        targetProject = basePath + "/" + targetProject;
//        System.out.println(generatorConfigXmlPath);
        List<Map<String, Object>> tables = MybatisGeneratorUtil.generatorConfigXml(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword, module, database, tablePrefix, packageName, lastInsertIdTables, basePath, targetProject, generatorConfigXmlPath);
        MybatisGeneratorUtil.generatorMybatis(generatorConfigXmlPath);
        MybatisGeneratorUtil.generateServices(basePath, module, packageName, tables);
    }

    private static void osHandler() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            // Windows Operation Systems
            generatorConfig_vm = MybatisGeneratorUtil.class.getResource(generatorConfig_vm).getPath().replaceFirst("/", "");
            service_vm = MybatisGeneratorUtil.class.getResource(service_vm).getPath().replaceFirst("/", "");
            serviceMock_vm = MybatisGeneratorUtil.class.getResource(serviceMock_vm).getPath().replaceFirst("/", "");
            serviceImpl_vm = MybatisGeneratorUtil.class.getResource(serviceImpl_vm).getPath().replaceFirst("/", "");
        } else {
            // Other Operation Systems
            generatorConfig_vm = MybatisGeneratorUtil.class.getResource(generatorConfig_vm).getPath();
            service_vm = MybatisGeneratorUtil.class.getResource(service_vm).getPath();
            serviceMock_vm = MybatisGeneratorUtil.class.getResource(serviceMock_vm).getPath();
            serviceImpl_vm = MybatisGeneratorUtil.class.getResource(serviceImpl_vm).getPath();
        }
    }

    private static List<Map<String, Object>> generatorConfigXml(String jdbcDriver, String jdbcUrl, String jdbcUsername, String jdbcPassword, String module, String database, String tablePrefix, String packageName, Map<String, String> lastInsertIdTables, String basePath, String targetProject, String generatorConfigXmlPath)
            throws Exception {
        System.out.println("====== Start Generating File: generatorConfig.xml ======");
        // get table names from database
        String sql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema =  '" + database + "' " + "AND table_name LIKE '" + tablePrefix + "_%';";
        List<Map<String, Object>> tables = new ArrayList<>();
        try {
            VelocityContext velocityContext = new VelocityContext();
            Map<String, Object> table;
            JdbcUtil jdbcUtil = new JdbcUtil(jdbcDriver, jdbcUrl, jdbcUsername, AesUtil.aesDecode(jdbcPassword));
            List<Map> result = jdbcUtil.selectByParams(sql, null);
            for (Map map : result) {
                System.out.println(map.get("TABLE_NAME"));
                table = new HashMap<>(2);
                table.put("table_name", map.get("TABLE_NAME"));
                table.put("model_name", StringUtil.underlineToHump(ObjectUtils.toString(map.get("TABLE_NAME"))));
                tables.add(table);
            }
            jdbcUtil.release();
            String targetProjectSqlMap = basePath + "/" + module + "/" + module + RPC_SERVICE_SUFFIX;
            velocityContext.put("tables", tables);
            velocityContext.put("generator_javaModelGenerator_targetPackage", packageName + MODEL_PACKAGE_SUBFFIX);
            velocityContext.put("generator_sqlMapGenerator_targetPackage", packageName + MAPPER_PACKAGE_SUBFFIX);
            velocityContext.put("generator_javaClientGenerator_targetPackage", packageName + MAPPER_PACKAGE_SUBFFIX);
            velocityContext.put("targetProject", targetProject);
            velocityContext.put("targetProject_sqlMap", targetProjectSqlMap);
            velocityContext.put("generator_jdbc_password", AesUtil.aesDecode(jdbcPassword));
            velocityContext.put("last_insert_id_tables", lastInsertIdTables);
            VelocityUtil.generate(generatorConfig_vm, generatorConfigXmlPath, velocityContext);
            deleteDir(new File(targetProject + JAVA_SOURCE_DIRECTORY + packageName.replaceAll("\\.", "/") + MODEL_PACKAGE_SUBFFIX.replaceAll("\\.", "/")));
            deleteDir(new File(targetProject + JAVA_SOURCE_DIRECTORY + packageName.replaceAll("\\.", "/") + MAPPER_PACKAGE_SUBFFIX.replaceAll("\\.", "/")));
            deleteDir(new File(targetProjectSqlMap + JAVA_SOURCE_DIRECTORY + packageName.replaceAll("\\.", "/") + MAPPER_PACKAGE_SUBFFIX.replaceAll("\\.", "/")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("====== Generate File Complete: generatorConfig.xml ======");
        return tables;
    }

    private static void generatorMybatis(String generatorConfigXmlPath) throws Exception {
        System.out.println("====== Start Running Mybatis Generator ======");
        List<String> warnings = new ArrayList<>();
        File configFile = new File(generatorConfigXmlPath);
        ConfigurationParser configurationParser = new ConfigurationParser(warnings);
        Configuration configuration = configurationParser.parseConfiguration(configFile);
        DefaultShellCallback defaultShellCallback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, defaultShellCallback, warnings);
        myBatisGenerator.generate(null);
        for (String warning : warnings) {
            System.out.println(warning);
        }
        System.out.println("====== Run Mybatis Generator Complete ======");
    }

    private static void generateServices(String basePath, String module, String packageName, List<Map<String, Object>> tables) {
        System.out.println("====== Start Generating Services ======");
        String createDate = new SimpleDateFormat(DATE_FORMAT_PATTERN).format(new Date());
        String createTime = new SimpleDateFormat(TIME_FORMAT_PATTERN).format(new Date());
        String apiPath = basePath + "/" + module + "/" + module + RPC_API_SUFFIX + JAVA_SOURCE_DIRECTORY + packageName.replaceAll("\\.", "/") + RPC_API_SUFFIX.replaceAll("-", "/");
        String serviceImplPath = basePath + "/" + module + "/" + module + RPC_SERVICE_SUFFIX + JAVA_SOURCE_DIRECTORY + packageName.replaceAll("\\.", "/") + RPC_SERVICE_IMPL_SUFFIX.replaceAll("-", "/");
        if (null != tables && tables.size() > 0) {
            for (int i = 0; i < tables.size(); i++) {
                String model = StringUtil.underlineToHump(ObjectUtils.toString(tables.get(i).get("table_name")));
                // service
                MybatisGeneratorUtil.generateService(apiPath, SERVICE_JAVA_SUBFFIX, service_vm, packageName, model, createDate, createTime);
                // service mock
                MybatisGeneratorUtil.generateService(apiPath, SERVICE_MOCK_JAVA_SUBFFIX, serviceMock_vm, packageName, model, createDate, createTime);
                // service impl
                MybatisGeneratorUtil.generateService(serviceImplPath, SERVICE_IMPL_JAVA_SUBFFIX, serviceImpl_vm, packageName, model, createDate, createTime);
            }
        }
        System.out.println("====== Generate Services Complete ======");
    }

    private static void generateService(String path, String subfix, String serviceVM, String packageName, String model, String createDate, String createTime) {
        String service = path + "/" + model + subfix;
        File file = new File(service);
        if (!file.exists()) {
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("package_name", packageName);
            velocityContext.put("model", model);
            if (serviceImpl_vm.equals(serviceVM)) {
                velocityContext.put("mapper", StringUtil.toLowerCaseInitial(model));
            }
            velocityContext.put("create_date", createDate);
            velocityContext.put("create_time", createTime);
            VelocityUtil.generate(serviceVM, service, velocityContext);
            System.out.println(service);
        }
    }

    public static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteDir(files[i]);
            }
        }
        dir.delete();
    }
}
