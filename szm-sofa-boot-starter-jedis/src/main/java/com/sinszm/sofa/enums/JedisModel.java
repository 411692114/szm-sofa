package com.sinszm.sofa.enums;

/**
 * Jedis缓存模式
 * @author fh411
 */
public enum JedisModel {

    /**
     * 单节点模式
     */
    STANDALONE,

    /**
     * 集群模式
     */
    CLUSTER,

    /**
     * 哨兵模式
     */
    SENTINEL

}
