package com.oasis.gateway.filter.factory;

import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName OasisAbstractGatewayFilterFactory
 * @Description 所有插件 Plugin(Filter) 抽象类
 * @Author zhushaobin
 * @Date 2022/4/8 20:48
 */
public abstract class OasisAbstractGatewayFilterFactory extends AbstractGatewayFilterFactory<OasisAbstractGatewayFilterFactory.Config> {

    public static final String CONFIG_KEY = "configuration";

    public OasisAbstractGatewayFilterFactory() {
        super(OasisAbstractGatewayFilterFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(CONFIG_KEY);
    }

    //对应 插件中的 plugin_configuration字段
    public static class Config {

        private String configuration;

        public String getConfiguration() {
            return configuration;
        }

        public void setConfiguration(String configuration) {
            this.configuration = configuration;
        }
    }

    @Override
    public String name() {
        return super.name();
    }
}
