package com.li.function;

/**
 * 增加函数是接口
 * @author lym
 * @param <T>
 */
@FunctionalInterface
public interface CacheSelector<T> {
    /**
     * 函数是接口返回值
     * @return
     * @throws Exception
     */
    T select() throws Exception;
}
