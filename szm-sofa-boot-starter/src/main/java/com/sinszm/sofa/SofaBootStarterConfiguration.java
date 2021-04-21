package com.sinszm.sofa;


import cn.hutool.extra.spring.EnableSpringUtil;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.PropertySource;

/**
 * 配置加载中心
 * @author sinszm
 */
@EnableSpringUtil
@ComponentScans({@ComponentScan("com.sinszm.*")})
@PropertySource("classpath:settings.properties")
public class SofaBootStarterConfiguration {

}
