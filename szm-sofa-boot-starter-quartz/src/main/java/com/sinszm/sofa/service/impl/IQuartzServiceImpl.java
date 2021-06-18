package com.sinszm.sofa.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.sinszm.sofa.exception.ApiException;
import com.sinszm.sofa.service.AbstractQuartzMethod;
import com.sinszm.sofa.service.IQuartzService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * quartz服务实现
 *
 * @author admin
 */
@Slf4j
@Service
public class IQuartzServiceImpl extends AbstractQuartzMethod implements IQuartzService {

    private final SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    public IQuartzServiceImpl(SchedulerFactoryBean schedulerFactoryBean) {
        this.schedulerFactoryBean = schedulerFactoryBean;
    }

    /**
     * 调度器
     *
     * @return {Scheduler}
     */
    @Override
    public Scheduler scheduler() {
        return schedulerFactoryBean.getScheduler();
    }

    @SneakyThrows
    @Override
    public Date addJob(String name, String group, Class<? extends Job> clazz, String cronExpression, Map<String, String> jobValue) {
        if (checkExists(name, group)) {
            throw new ApiException("202", "任务已存在，请先删除重试");
        }
        //调度器
        Scheduler scheduler = scheduler();
        //构造任务
        JobDetail jobDetail = newJob(clazz)
                .withIdentity(name, group)
                .storeDurably(true)
                .build();
        //构造任务触发器
        Trigger trigger = newTrigger()
                .withIdentity(name, group)
                .withSchedule(
                        cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing()
                ).build();
        Date nextDate = nextDate(scheduler,jobDetail, trigger, clazz, jobValue,false, false);
        log.info("新增作业=> [作业名称：" + name + " 作业组：" + group + "] ");
        return nextDate;
    }

    @SneakyThrows
    @Override
    public Date addJob(String name, String group, Class<? extends Job> clazz, Trigger trigger, Map<String, String> jobValue) {
        if (checkExists(name, group)) {
            throw new ApiException("202", "任务已存在，请先删除重试");
        }
        //调度器
        Scheduler scheduler = scheduler();
        //构造任务
        JobDetail jobDetail = newJob(clazz)
                .withIdentity(name, group)
                .storeDurably(true)
                .build();
        //处理参数
        if (jobValue != null) {
            for (Map.Entry<String, String> entry : jobValue.entrySet()) {
                jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
            }
        }
        Date nextDate = nextDate(scheduler,jobDetail, trigger, clazz, jobValue,false, false);
        log.info("新增作业=> [作业名称：" + name + " 作业组：" + group + "] ");
        return nextDate;
    }

    @SneakyThrows
    @Override
    public void executeJob(String name, String group, Class<? extends Job> clazz, Date startTime, Map<String, String> jobValue) {
        if (checkExists(name, group)) {
            throw new ApiException("202", "任务已存在，请先删除重试");
        }
        //调度器
        Scheduler scheduler = scheduler();
        //构造任务
        JobDetail jobDetail = newJob(clazz)
                .withIdentity(name, group)
                .storeDurably(true)
                .build();
        //处理参数
        if (ObjectUtil.isNotEmpty(jobValue)) {
            for (Map.Entry<String, String> entry : jobValue.entrySet()) {
                jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
            }
        }
        //触发器
        Trigger trigger = newTrigger()
                .withIdentity(name, group)
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow()
                ).startAt(startTime == null || startTime.before(new Date()) ? new Date() : startTime).build();
        scheduler.scheduleJob(jobDetail, trigger);
        //立即触发
        scheduler.triggerJob(jobDetail.getKey());
    }

    @SneakyThrows
    @Override
    public void removeJob(String name, String group) {
        //调度器
        Scheduler scheduler = scheduler();
        //触发器
        TriggerKey tk = TriggerKey.triggerKey(name, group);
        //停止触发器
        scheduler.pauseTrigger(tk);
        //移除触发器
        scheduler.unscheduleJob(tk);
        //作业
        JobKey jobKey = JobKey.jobKey(name, group);
        //删除作业
        scheduler.deleteJob(jobKey);
        log.info("删除作业=> [作业名称：" + name + " 作业组：" + group + "] ");
    }

    @SneakyThrows
    @Override
    public void pauseJob(String name, String group) {
        //调度器
        Scheduler scheduler = scheduler();
        //作业
        JobKey jobKey = JobKey.jobKey(name, group);
        //暂停作业
        scheduler.pauseJob(jobKey);
        log.info("暂停作业=> [作业名称：" + name + " 作业组：" + group + "] ");
    }

    @SneakyThrows
    @Override
    public void resumeJob(String name, String group) {
        //调度器
        Scheduler scheduler = scheduler();
        //作业
        JobKey jobKey = JobKey.jobKey(name, group);
        //恢复作业
        scheduler.resumeJob(jobKey);
        log.info("恢复作业=> [作业名称：" + name + " 作业组：" + group + "] ");
    }

    @SneakyThrows
    @Override
    public Date modifyTime(String name, String group, String cronExpression) {
        //调度器
        Scheduler scheduler = scheduler();
        //触发器
        TriggerKey tk = TriggerKey.triggerKey(name, group);
        //构造任务触发器
        Trigger trg = newTrigger().withIdentity(name, group)
                .forJob(name, group).withSchedule(cronSchedule(cronExpression)
                        .withMisfireHandlingInstructionDoNothing()).build();
        Date nextFireTime = scheduler.rescheduleJob(tk, trg);
        log.info("修改作业触发时间=> [作业名称：" + name + " 作业组：" + group + "] ");
        return nextFireTime;
    }

    @SneakyThrows
    @Override
    public Date modifyJobValueAndTime(String name, String group, Class<? extends Job> clazz, String cronExpression, Map<String, String> jobValue) {
        //调度器
        Scheduler scheduler = scheduler();
        //触发器
        TriggerKey oldTk = TriggerKey.triggerKey(name, group);
        //停止触发器
        scheduler.pauseTrigger(oldTk);
        //移除触发器
        scheduler.unscheduleJob(oldTk);
        //作业
        JobKey jobKey = JobKey.jobKey(name, group);
        //删除作业
        scheduler.deleteJob(jobKey);
        //构造任务
        JobDetail job = newJob(clazz).withIdentity(name, group).storeDurably(true).build();
        if (jobValue != null) {
            for (Map.Entry<String, String> entry : jobValue.entrySet()) {
                job.getJobDataMap().put(entry.getKey(), entry.getValue());
            }
        }
        //构造任务触发器
        Trigger trg = newTrigger().withIdentity(name, group)
                .forJob(job).withSchedule(cronSchedule(cronExpression)
                        .withMisfireHandlingInstructionDoNothing()).build();
        Date nextFireTime = scheduler.scheduleJob(job, trg);
        log.info("修改作业触发时间和参数=> [触发器名称：" + name + " 作业组：" + group + "] ");
        return nextFireTime;
    }

    @SneakyThrows
    @Override
    public boolean checkExists(String name, String group) {
        Scheduler scheduler = scheduler();
        JobKey jobKey = JobKey.jobKey(name, group);
        return scheduler.checkExists(jobKey);
    }
}
