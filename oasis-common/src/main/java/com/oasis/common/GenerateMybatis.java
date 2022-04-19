package com.oasis.common;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;
//
///**
// * @ClassName GenerateMybatis
// * @Description TODO
// * @Author zhushaobin
// * @Date 2022/4/8 10:08
// */
public class GenerateMybatis {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/oasis_gateway", "root", "root")
                .globalConfig(builder -> {
                    builder.author("Neal") // 设置作者
                            .outputDir("D://"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.oasis.common") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.mapper, "D://")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("gateway_api_plugin","gateway_api_router","gateway_group","gateway_service"); // 设置需要生成的表名
                    builder.mapperBuilder().enableBaseResultMap();
                })// 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
