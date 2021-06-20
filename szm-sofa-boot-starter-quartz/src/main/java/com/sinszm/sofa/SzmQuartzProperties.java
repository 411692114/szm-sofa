package com.sinszm.sofa;

import cn.hutool.core.lang.Assert;
import com.sinszm.sofa.exception.ApiException;
import com.sinszm.sofa.util.BaseUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceInitializationMode;

import static org.springframework.boot.jdbc.DataSourceInitializationMode.ALWAYS;

/**
 * 基础配置
 *
 * @author admin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConfigurationProperties("quartz")
public class SzmQuartzProperties {

    /**
     * 指定数据源实例名称
     */
    private String dataSource;

    /**
     * 模式
     */
    private DataSourceInitializationMode mode;

    /**
     * 任务实例名
     */
    private String instanceName;

    /**
     * 检查所有
     *
     * @return {SzmQuartzProperties}
     */
    public SzmQuartzProperties checkAll() {
        Assert.notEmpty(BaseUtil.trim(this.dataSource), () -> new ApiException("202", "请先指定数据源实例名称"));
        this.dataSource = BaseUtil.trim(this.dataSource);
        this.mode = mode == null ? ALWAYS : this.mode;
        this.instanceName = BaseUtil.isEmpty(this.instanceName) ? "quartzScheduler" : this.instanceName;
        return this;
    }

}
