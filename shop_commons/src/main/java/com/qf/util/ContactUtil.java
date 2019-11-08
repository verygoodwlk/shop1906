package com.qf.util;

/**
 * 常量接口
 */
public interface ContactUtil {

    /**
     * redis中秒杀有序集合的key
     */
    String REDIS_MIAOSHA_SORT_SET = "miaosha_sort";

    /**
     * redis中秒杀开始的集合的key
     */
    String REDIS_MIAOSHA_START_SET = "miaosha_start";

    /**
     * 当前秒杀开始的时间后缀
     */
    String REDIS_MIAOSHA_TIME_PROFIX = "time_profix";

    /**
     * 秒杀商品的库存
     */
    String REDIS_MIAOSHA_SAVE = "miaosha_save";
}
