package com.pyq;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * @author: peng
 * @date: 2018-12-6 006 15:01:10
 */

@Slf4j
public class GeneratorServiceEntity {

    @Test
    public void test(){
        log.info("test111");
    }

    @Test
    public void generateCode() {
        String packageName = "com.pyq";
        boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
        String[] str={"resources","role","role_resources","user","user_role"};
        generateByTables(serviceNameStartWithI, packageName, str);
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        String dbUrl = "jdbc:mysql://localhost:3306/sys_shiro";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("123456")
                .setDriverName("com.mysql.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                //.setSuperEntityClass("com.pyq.myspringboot.entity.BaseEntity")
                //.setSuperEntityColumns("id")
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        config.setActiveRecord(false)
                .setAuthor("pengyq")
                .setOutputDir(projectPath+"/src/main/java")
                .setBaseResultMap(true)
                .setFileOverride(true);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);

        new AutoGenerator().setGlobalConfig(config)
                .setCfg(cfg)
                .setTemplate(new TemplateConfig().setXml(null))
                .setDataSource(dataSourceConfig)
                .setTemplateEngine(new FreemarkerTemplateEngine())
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("entity")
                ).setStrategy(strategyConfig).execute();
    }

    private void generateByTables(String packageName, String... tableNames) {
        generateByTables(true, packageName, tableNames);
    }

}
