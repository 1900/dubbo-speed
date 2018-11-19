package com.funtime.dubbo.tools.generator;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;

public class Generator {

    // 根据命名规范，只修改此常量值即可
    private static String MODULE = "dubbo";
    private static String TABLE_PREFIX = "sys_";
    private static String PARENT_PACKAGE_NAME = "com.funtime.dubbo";

    private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String JDBC_URL = "jdbc:mysql://mysql:3306/dev?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull";
    private static String JDBC_USERNAME = "root";
    private static String JDBC_PASSWORD = "123456";

    private static String OUTPUT_API_DIR = "api/src/main/java";
    private static String OUTPUT_SERVICE_DIR = "provider/src/main/java";
    private static String OUTPUT_CONTROLLER_DIR = "consumer/src/main/java";

    /**
     * 自动代码生成
     * @param args
     */
    public static void main(String[] args) {

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName(JDBC_DRIVER);
        dsc.setUsername(JDBC_USERNAME);
        dsc.setPassword(JDBC_PASSWORD);
        dsc.setUrl(JDBC_URL);

        PackageConfig pc = new PackageConfig();
        pc.setParent(PARENT_PACKAGE_NAME);
        pc.setModuleName(MODULE);

        DefaultGenerator defaultGenerator = new DefaultGenerator();
        defaultGenerator.setDataSource(dsc);
        defaultGenerator.setPackageInfo(pc);
        defaultGenerator.setIncludeTables(TABLE_PREFIX,"sns_operation_log");

        // 生成API
        defaultGenerator.setOutputDir(OUTPUT_API_DIR);
        defaultGenerator.createClass(
                "entity"
                ,"service"
        );
        // 生成服务
        defaultGenerator.setOutputDir(OUTPUT_SERVICE_DIR);
        defaultGenerator.createClass(
                "Mapper"
                , "mapper"
                , "service.impl"
        );
        // 生成Controller
        defaultGenerator.setOutputDir(OUTPUT_CONTROLLER_DIR);
        defaultGenerator.createClass("controller");
    }

}