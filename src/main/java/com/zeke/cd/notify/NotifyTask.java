package com.zeke.cd.notify;

import com.intellij.concurrency.JobScheduler;
import com.intellij.openapi.application.ApplicationManager;
import com.zeke.cd.config.ConfigState;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 定时通知任务
 * @author King.Z <br>
 * @since    2019/4/28 20:59 <br>
 * @version  1.0 <br>
 */
public class NotifyTask {
    private static final ThreadLocal<ScheduledFuture> SCHEDULED_FUTURE_CONTEXT = new ThreadLocal<>();

    static void init(){
        restart();
    }

     /**
     * 重新开启定时提醒任务
     */
    public static void restart() {
        destroy();
        //TODO 获取配置时间数据
        ScheduledFuture scheduledFuture = JobScheduler.getScheduler().scheduleWithFixedDelay(new ToastRunnable(),
                1, 1, TimeUnit.MINUTES);
        SCHEDULED_FUTURE_CONTEXT.set(scheduledFuture);
    }

    /**
     * 注销定时提醒任务
     */
    private static void destroy() {
        ScheduledFuture existScheduledFuture = SCHEDULED_FUTURE_CONTEXT.get();
        if (existScheduledFuture != null) {
            existScheduledFuture.cancel(true);
            SCHEDULED_FUTURE_CONTEXT.remove();
        }
    }

    /**
     * 消息通知线程
     */
    static class ToastRunnable implements Runnable{
        @Override
        public void run() {
            //ConfigState configState = IConfigService.getInstance().getState();
            ConfigState.RemindTypeEnum remindType = ConfigState.RemindTypeEnum.valueOf(1);
            INotifyStrategy remindStrategy = INotifyStrategy.getRemindStrategy(remindType);
            ApplicationManager.getApplication().invokeLater(remindStrategy::remind);
        }
    }
}
