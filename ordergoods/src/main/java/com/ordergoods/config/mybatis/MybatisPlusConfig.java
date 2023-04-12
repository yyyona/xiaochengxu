package com.ordergoods.config.mybatis;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//自动分页
@EnableTransactionManagement//开启事务管理器，处理并发访问数据库时出现的问题
@MapperScan({"com.ordergoods.mapper"})//扫描mapper文件夹
@Configuration
public class MybatisPlusConfig {

    //分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {//自动生成分页 SQL 语句,注入到 MyBatis 的拦截器链
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));//进行 SQL语句的优化，提高查询性能
        return paginationInterceptor;
    }
}