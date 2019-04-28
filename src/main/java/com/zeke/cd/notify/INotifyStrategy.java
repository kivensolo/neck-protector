package com.zeke.cd.notify;

import com.intellij.notification.*;
import com.intellij.openapi.diagnostic.Logger;
import com.zeke.cd.config.ConfigState;
import com.zeke.cd.config.GlobalSettings;
import com.zeke.cd.config.IConfigService;
import org.jetbrains.annotations.NotNull;

/**
 * 打开图片的策略模式
 *
 * @author King.Z
 * @version 1.0
 * @since 2019-04-28
 */
@FunctionalInterface
public interface INotifyStrategy {
    INotifyStrategy INSTANCE_REMIND_DIRECT = new RemindDirect();
    INotifyStrategy INSTANCE_REMIND_INDIRECT = new RemindIndirect();

    /**
     * 策略模式的工厂方法
     *
     * @see ConfigState.RemindTypeEnum
     */
    @NotNull
    static INotifyStrategy getRemindStrategy(ConfigState.RemindTypeEnum type) {
        INotifyStrategy NotifyStrategy;
        switch (type) {
            case DIRECT:
                NotifyStrategy = INSTANCE_REMIND_DIRECT;
                break;
            case INDIRECT:
                NotifyStrategy = INSTANCE_REMIND_INDIRECT;
                break;
            default:
                NotifyStrategy = null;
        }
        return NotifyStrategy;
    }

    /**
     * 使用不同策略打开图片
     */
    void remind();

    /**
     * 直接打开图片
     *
     * @see ConfigState.RemindTypeEnum#DIRECT
     */
    class RemindDirect implements INotifyStrategy {

        /**
         * {@inheritDoc}
         */
        @Override
        public void remind() {
            //DataManager.getInstance().getDataContextFromFocus()
            //        .doWhenDone((Consumer<DataContext>) (dataContext -> new OpenImageConsumer().accept(dataContext)))
            //        .doWhenRejected((Consumer<String>) LOG::error);
        }
    }

    /**
     * 间接打开图片
     *
     * @see ConfigState.RemindTypeEnum#INDIRECT
     */
    class RemindIndirect implements INotifyStrategy {
        private static Logger LOG = Logger.getInstance(RemindIndirect.class);
        /**
         * {@inheritDoc}
         */
        @Override
        public void remind() {
            String displayId = "Plugins " + GlobalSettings.PLUGIN_NAME;
            NotificationDisplayType displayType = NotificationDisplayType.STICKY_BALLOON;
            NotificationGroup notificationGroup = new NotificationGroup(displayId, displayType, true);

            ConfigState cs = IConfigService.getInstance().getState();
            Notification notification = obtainNotification(notificationGroup, cs.getNotifyTitle(),
                    cs.getNotifyContent(),NotificationType.INFORMATION, null);
            //OpenImageAction openImageAction = new OpenImageAction(configState.getNotifyAction(), notification);
            //notification.addAction(openImageAction);
            Notifications.Bus.notify(notification);
            LOG.info("notify an info message");
        }

        private Notification obtainNotification(NotificationGroup notifiGroup,
                                                String notifyTitle,
                                                String notifyContent,
                                                NotificationType type,
                                                NotificationListener listener) {
            return notifiGroup.createNotification(notifyTitle, notifyContent,
                                                type, listener);
        }
    }
}
