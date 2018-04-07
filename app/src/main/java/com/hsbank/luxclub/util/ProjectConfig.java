package com.hsbank.luxclub.util;

import com.hsbank.util.project.util.BaseProjectConfig;

/**
 * 工程配置Bean
 * <p>
 * 单例类
 * @author Arthur.Xie
 * 2009-10-25
 */
public class ProjectConfig extends BaseProjectConfig {
    /**单例*/
    private static ProjectConfig instance = null;

    /**私有构造函数*/
    private ProjectConfig() {
    }

    /**
     * 得到单例
     * @return 单例
     */
    public static synchronized ProjectConfig getInstance() {
        return instance == null ? instance = new ProjectConfig() : instance;
    }
}
