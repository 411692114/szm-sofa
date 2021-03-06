package com.sinszm.sofa.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.sinszm.sofa.exception.ApiException;
import com.sinszm.sofa.job.DefaultSimpleJob;
import com.sinszm.sofa.vo.JobParam;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.Trigger;

import java.util.Date;
import java.util.Map;

/**
 * quartz服务
 *
 * @author admin
 */
public interface IQuartzService {

    /**
     * 调度器
     *
     * @return {Scheduler}
     */
    Scheduler scheduler();

    /**
     * 添加工作
     *
     * @param name              任务名称
     * @param group             任务分组
     * @param clazz             任务实现类
     * @param cronExpression    cron表达式
     * @param jobValue          任务参数
     * @return                  时间
     */
    Date addJob(
            String name,
            String group,
            Class<? extends Job> clazz,
            String cronExpression,
            Map<String, Object> jobValue);

    /**
     * 添加工作
     *
     * @param name              任务名称
     * @param group             任务分组
     * @param cronExpression    cron表达式
     * @param param             任务参数
     * @return                  时间
     */
    default Date addJob(
            String name,
            String group,
            String cronExpression,
            JobParam param) {
        Assert.notNull(param, () -> new ApiException("202", "任务参数不能为空"));
        return addJob(name, group, DefaultSimpleJob.class, cronExpression, BeanUtil.beanToMap(param));
    }

    /**
     * 添加工作
     *
     * @param name              任务名称
     * @param group             任务分组
     * @param clazz             任务实现类
     * @param trigger           触发器
     * @param jobValue          任务参数
     * @return                  时间
     */
    Date addJob(
            String name,
            String group,
            Class<? extends Job> clazz,
            Trigger trigger,
            Map<String, Object> jobValue);

    /**
     * 添加工作
     *
     * @param name              任务名称
     * @param group             任务分组
     * @param trigger           触发器
     * @param param             任务参数
     * @return                  时间
     */
    default Date addJob(
            String name,
            String group,
            Trigger trigger,
            JobParam param) {
        Assert.notNull(param, () -> new ApiException("202", "任务参数不能为空"));
        return addJob(name, group, DefaultSimpleJob.class, trigger, BeanUtil.beanToMap(param));
    }

    /**
     * 指定时间执行工作，开始时间为空表示立即执行
     *
     * @param name      任务名称
     * @param group     任务分组
     * @param clazz     任务实现类
     * @param jobValue  任务参数
     * @param startTime 开始时间
     */
    void executeJob(
            String name,
            String group,
            Class<? extends Job> clazz,
            Date startTime,
            Map<String, Object> jobValue);

    /**
     * 指定时间执行工作，开始时间为空表示立即执行
     *
     * @param name      任务名称
     * @param group     任务分组
     * @param param     任务参数
     * @param startTime 开始时间
     */
    default void executeJob(
            String name,
            String group,
            JobParam param,
            Date startTime) {
        Assert.notNull(param, () -> new ApiException("202", "任务参数不能为空"));
        executeJob(name, group, DefaultSimpleJob.class, startTime, BeanUtil.beanToMap(param));
    }

    /**
     * 删除工作
     *
     * @param name              任务名称
     * @param group             任务分组
     */
    void removeJob(String name, String group);

    /**
     * 暂停定时任务
     *
     * @param name              任务名称
     * @param group             任务分组
     */
    void pauseJob(String name, String group);

    /**
     * 重新开始定时任务
     *
     * @param name              任务名称
     * @param group             任务分组
     */
    void resumeJob(String name, String group);

    /**
     * 修改定时任务时间
     *
     * @param name           任务名称
     * @param group          任务分组
     * @param cronExpression cron表达式
     * @return               时间
     */
    Date modifyTime(String name, String group, String cronExpression);

    /**
     * 修改触发器
     *
     * @param name           任务名称
     * @param group          任务分组
     * @param trigger        触发器
     * @return               时间
     */
    Date modifyTrigger(String name, String group, Trigger trigger);

    /**
     * 检查是否存在
     *
     * @param name              任务名称
     * @param group             任务分组
     * @return                  是否存在
     */
    boolean checkExists(String name, String group);

}
