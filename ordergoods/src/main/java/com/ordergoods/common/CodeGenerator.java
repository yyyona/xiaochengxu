package com.ordergoods.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);//创建Scanner对象，读取控制台输入的内容
        StringBuilder help = new StringBuilder();//创建StringBuilder对象，构建提示信息
        help.append("请输入" + tip + "：");//将提示信息添加到StringBuilder对象中
        System.out.println(help.toString());
        if (scanner.hasNext()) {//判断控制台是否有输入内容
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {//使用Apache Commons Lang库中的StringUtils工具类判断输入内容是否为空或空白字符串
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");/// 如果输入内容为空或空白字符串，则抛出自定义异常MybatisPlusException
    }

    public static void main(String[] args) {
        // 构建一个代码自动生成器对象
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");// 设置生成文件的输出目录
        gc.setAuthor("yan");
        gc.setOpen(false);//是否打开输出目录（不设置则默认为true）
        gc.setBaseResultMap(true);//是否开启实体类字段到数据库列名的映射
        gc.setBaseColumnList(true);// 是否在XML中开启基础列
        gc.setFileOverride(true);//是否覆盖已有文件
        gc.setIdType(IdType.AUTO);//设置主键生成策略
        gc.setServiceName("%sService");//设置Service接口的命名方式,生成的Service不带I
        gc.setServiceImplName("%sServiceImpl");//设置ServiceImpl实现类的命名方式
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/ordergoods0221?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.ordergoods");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";


        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();//MyBatis-Plus 中提供的文件输出配置类，来自定义生成代码的路径和文件名
        // 自定义配置会被优先输出
        String finalProjectPath = projectPath;
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果 Entity 设置了前后缀、 xml 的名称会跟着发生变化
                return finalProjectPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });// outputFile方法根据用户传入的参数 tableInfo 获取到当前正在遍历的数据库表的 Entity 名称，拼接为一个完整的文件路径和文件名，最终返回

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);//将用户自定义的输出配置对象和 AutoGenerator 内置的默认配置进行合并

        // 配置模板,指定生成的代码中哪些文件需要生成，哪些不需要生成，以及需要生成的文件使用哪种模板等信息
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置,指定生成的代码中哪些表需要生成，哪些不需要生成，以及表名和字段名等的命名策略等信息，能写直接映射的表名 strategy.setInclude
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);//下划线转驼峰
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);//实体类 Lombok 模型
        strategy.setRestControllerStyle(true);

        // 公共父类
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("tb_");//表前缀
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
