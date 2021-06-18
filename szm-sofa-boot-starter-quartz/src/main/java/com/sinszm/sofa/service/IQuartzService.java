package com.sinszm.sofa.service;

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
            Map<String, String> jobValue);

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
            Map<String, String> jobValue);

    /**
     * 执行工作
     * 立即执行工作
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
            Map<String, String> jobValue);

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
     * 修改定时任务的时间和参数
     *
     * @param name              任务名称
     * @param group             任务分组
     * @param clazz             任务实现类
     * @param cronExpression    cron表达式
     * @param jobValue          任务参数
     * @return                  时间
     */
    Date modifyJobValueAndTime(
            String name,
            String group,
            Class<? extends Job> clazz,
            String cronExpression,
            Map<String, String> jobValue);

    /**
     * 检查是否存在
     *
     * @param name              任务名称
     * @param group             任务分组
     * @return                  是否存在
     */
    boolean checkExists(String name, String group);

}
