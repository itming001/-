package com.li.utils;

import com.li.function.CacheSelector;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * 通用工具类
 * @author lym
 */
@Slf4j
public class Utils {
    /**
     * 缓存查询模板
     * 此模板可以防止获取缓存出错，导报错，摘自febs-vue（前后端分离项目）
     * @param cacheSelector    查询缓存的方法
     * @param databaseSelector 数据库查询方法
     * @return T
     */
    public static <T> T selectCacheByTemplate(CacheSelector<T> cacheSelector, Supplier<T> databaseSelector) {
        try {
            log.debug("query data from redis ······");
            // 先查 Redis缓存
            T t = cacheSelector.select();
            if (t == null) {
                // 没有记录再查询数据库
                return databaseSelector.get();
            } else {
                return t;
            }
        } catch (Exception e) {
            // 缓存查询出错，则去数据库查询
            log.error("redis error：", e);
            log.debug("query data from database ······");
            return databaseSelector.get();
        }
    }
}
