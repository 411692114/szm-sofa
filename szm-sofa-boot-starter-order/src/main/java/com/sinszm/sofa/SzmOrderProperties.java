package com.sinszm.sofa;

import com.sinszm.sofa.util.BaseUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.sinszm.sofa.support.Constant.DEFAULT_DATASOURCE_DIALECT;

/**
 * 基础配置
 *
 * @author admin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConfigurationProperties("order")
public class SzmOrderProperties {

    /**
     * 已经存在的数据源名称（指定了数据源之后只要数据源符合条件就不再使用内置数据源）
     */
    private String datasource;

    /**
     * 内置数据库的账号
     */
    private String username;

    /**
     * 内置数据库的密码
     */
    private String password;

    /**
     * 方言
     */
    private String hibernateDialect;

    /**
     * 是否有设置数据源
     *
     * @return boolean
     */
    public boolean hasDataSource() {
        return !BaseUtil.isEmpty(this.datasource) && !BaseUtil.isEmpty(this.hibernateDialect);
    }

    /**
     * 得到方言
     *
     * @return {String}
     */
    public String getDialect() {
        if (!hasDataSource()) {
            return DEFAULT_DATASOURCE_DIALECT;
        }
        return BaseUtil.trim(this.hibernateDialect);
    }

}
