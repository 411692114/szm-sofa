package com.sinszm.sofa.service;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.KeyMatcher;

import java.util.*;

/**
 * 方法抽象
 *
 * @author admin
 */
public abstract class AbstractQuartzMethod {

    @Deprecated
    protected void addJobListener(JobKey jobKey, Class<? extends Job> clazz, Scheduler scheduler) throws
            ClassNotFoundException, IllegalAccessException, InstantiationException, SchedulerException {
        String tempPath = clazz.getName() + "Listener";
        String listenerFilePath = buildEntirePath(tempPath);
        Object jobListener = Class.forName(listenerFilePath).newInstance();
        KeyMatcher<JobKey> keyMatcher = KeyMatcher.keyEquals(jobKey);
        scheduler.getListenerManager().addJobListener((JobListener) jobListener, keyMatcher);
    }

    @Deprecated
    protected void addTriggerListened(Trigger trigger, Class<? extends Job> clazz, Scheduler scheduler)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, SchedulerException {
        String tempPath = clazz.getName() + "TListener";
        String listenerFilePath = buildEntirePath(tempPath);
        TriggerListener triggerListener = (TriggerListener) Class.forName(listenerFilePath).newInstance();
        KeyMatcher<TriggerKey> keyMatcher = KeyMatcher.keyEquals(trigger.getKey());
        scheduler.getListenerManager().addTriggerListener(triggerListener, keyMatcher);
    }

    @Deprecated
    protected String buildEntirePath(String tempPath) {
        String[] packageNames = tempPath.split("\\.");
        List<String> list = new ArrayList<>(Arrays.asList(packageNames));
        list.add(list.size() - 1, "listener");
        return StringUtils.join(list, ".");
    }

    @SneakyThrows
    protected Date nextDate(
            Scheduler scheduler,
            JobDetail jobDetail,
            Trigger trigger,
            Class<? extends Job> clazz,
            Map<String, String> jobValue,
            boolean isJobListened,
            boolean isTriggerListened) {
        //处理参数
        if (jobValue != null) {
            for (Map.Entry<String, String> entry : jobValue.entrySet()) {
                jobDetail.getJobDataMap().put(entry.getKey(), entry.getValue());
            }
        }
        if (isJobListened) {
            addJobListener(jobDetail.getKey(), clazz, scheduler);
        }
        if (isTriggerListened) {
            addTriggerListened(trigger, clazz, scheduler);
        }
        //将作业添加到调度器
        return scheduler.scheduleJob(jobDetail, trigger);
    }

}
