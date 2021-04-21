package com.sinszm.sofa;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Import;

/**
 * 配置加载中心
 * @author sinszm
 */
@ComponentScans({
        @ComponentScan("com.sinszm.*"),
        @ComponentScan("cn.hutool.extra.spring")
})
@Import(SpringHelper.class)
public class SofaBootStarterConfiguration {

}
