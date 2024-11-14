package com.zeke.cd.notify;

import com.intellij.ide.DataManager;
import com.intellij.notification.*;
import com.intellij.openapi.diagnostic.Logger;
import com.zeke.cd.actions.OpenImageAction;
import com.zeke.cd.images.OpenImageConsumer;
import com.zeke.cd.service.ConfigState;
import com.zeke.cd.service.IConfigService;

//import org.jetbrains.annotations.NotNull;

/**
 * 打开图片的策略模式
 *
 * @author King.Z
 * @version 1.0
 * @since 2019-04-28
 */
@FunctionalInterface
public interface INotifyStrategy {
    Logger LOG = Logger.getInstance(INotifyStrategy.class);
    INotifyStrategy INSTANCE_REMIND_DIRECT = new RemindDirect();
    INotifyStrategy INSTANCE_REMIND_INDIRECT = new RemindIndirect();

    /**
     * 策略模式的工厂方法
     *
     * @see ConfigState.RemindTypeEnum
     */
//    @NotNull
    static INotifyStrategy getRemindStrategy(ConfigState.RemindTypeEnum type) {
        INotifyStrategy NotifyStrategy;
        //noinspection EnhancedSwitchMigration
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
     * 消息通知回调方法
     */
    void msgNotify();

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
        public void msgNotify() {
            DataManager.getInstance().getDataContextFromFocusAsync()
                            .onSuccess(dataContext -> new OpenImageConsumer().accept(dataContext))
                            .onError(LOG::error);
        }
    }

    /**
     * 间接打开图片
     *
     * @see ConfigState.RemindTypeEnum#INDIRECT
     */
    class RemindIndirect implements INotifyStrategy {
        private static Logger LOG = Logger.getInstance(RemindIndirect.class);
        private NotificationGroup  notificationGroup;
        public RemindIndirect() {
            NotificationGroupManager manager = NotificationGroupManager.getInstance();
            if (manager == null) {
                return;
            }
            //group-id由扩展点定义
            notificationGroup = manager.getNotificationGroup("NeckProtector_NOTIFY");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void msgNotify() {
            ConfigState cs = IConfigService.getInstance().getState();
            Notification notification = obtainNotification(notificationGroup,
                                                           cs.getNotifyTitle(),
                                                           cs.getNotifyContent(),
                                                           NotificationType.INFORMATION);
            OpenImageAction openImageAction = new OpenImageAction(cs.getNotifyAction(), notification);
            notification.addAction(openImageAction);
            Notifications.Bus.notify(notification);
            LOG.info("notify an info message");
        }

        private Notification obtainNotification(NotificationGroup notifiGroup,
                                                String notifyTitle,
                                                String notifyContent,
                                                NotificationType type) {
            return notifiGroup.createNotification(notifyTitle, notifyContent, type);
        }
    }
}
