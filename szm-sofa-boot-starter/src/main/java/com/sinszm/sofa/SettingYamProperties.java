package com.sinszm.sofa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 默认配置
 * @author sinszm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@PropertySource("classpath:settings.yml")
public class SettingYamProperties {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${logging.path}")
    private String path;

}
