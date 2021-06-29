package com.sinszm.sofa.lock;

import com.sinszm.sofa.exception.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * 分布式锁调用工具
 *
 * @author admin
 */
@Slf4j
public final class LockUtil {

    /**
     * 执行锁定
     *
     * @param key      关键
     * @param supplier 供应商
     * @return {@link T}
     */
    public static <T> T executeLock(String key, Supplier<T> supplier) {
        try(Lock lock = new Lock(key)) {
            if (lock.lock()) {
                return supplier.get();
            } else {
                log.info("任务已锁定");
            }
        } catch (Exception e) {
            log.error("任务锁执行业务异常", e);
        }
        throw new ApiException("110", "任务锁执行业务异常");
    }

    /**
     * 执行锁定
     *
     * @param key      关键
     * @param value    键值
     * @param supplier 供应商
     * @return {@link T}
     */
    public static <T> T executeLock(String key, String value, Supplier<T> supplier) {
        try(Lock lock = new Lock(key, value)) {
            if (lock.lock()) {
                return supplier.get();
            } else {
                log.info("任务已锁定");
            }
        } catch (Exception e) {
            log.error("任务锁执行业务异常", e);
        }
        throw new ApiException("110", "任务锁执行业务异常");
    }

    /**
     * 执行锁定
     *
     * @param key         关键
     * @param value       键值
     * @param expiresTime 到期时间，精度秒
     * @param supplier    供应商
     * @return {@link T}
     */
    public static <T> T executeLock(String key, String value, int expiresTime, Supplier<T> supplier) {
        try(Lock lock = new Lock(key, value, expiresTime)) {
            if (lock.lock()) {
                return supplier.get();
            } else {
                log.info("任务已锁定");
            }
        } catch (Exception e) {
            log.error("任务锁执行业务异常", e);
        }
        throw new ApiException("110", "任务锁执行业务异常");
    }
    
}
