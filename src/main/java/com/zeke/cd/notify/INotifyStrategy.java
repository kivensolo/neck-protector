package com.zeke.cd.notify;

import com.intellij.ide.DataManager;
import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.AsyncResult;
import com.intellij.util.Consumer;
import com.zeke.cd.actions.OpenImageAction;
import com.zeke.cd.images.OpenImageConsumer;
import com.zeke.cd.service.ConfigState;
import com.zeke.cd.service.IConfigService;
import com.zeke.cd.settings.GlobalSettings;
import com.zeke.cd.utils.Utils;
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
            AsyncResult<DataContext> dataContextAsyncResult = DataManager.getInstance().getDataContextFromFocus()
                    .doWhenDone((Consumer<DataContext>) (dataContext -> new OpenImageConsumer().accept(dataContext)));
            String fullVersion = ApplicationInfo.getInstance().getFullVersion();
            LOG.info("obtainNotification, currenIDEAVersion=" + fullVersion);
            boolean before202317 = Utils.isVersionLessOrEqu(fullVersion, "2023.1.7");
            if(before202317){
                dataContextAsyncResult.doWhenRejected((Consumer<String>) LOG::error);
            }else{
                dataContextAsyncResult.doWhenRejected(LOG::error);
            }
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
            String displayId = "toast_" + GlobalSettings.PLUGIN_NAME;
            NotificationDisplayType displayType = NotificationDisplayType.STICKY_BALLOON;
            //TODO  scheduled for removal API
            notificationGroup = new NotificationGroup(displayId, displayType, true);
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

            // 如何注册displayId？
            //NotificationGroup notificationGroupNew = NotificationGroupManager.getInstance().getNotificationGroup(displayId);
            //Notification notification = obtainNotification(notificationGroupNew,
            //        cs.getNotifyTitle(),
            //        cs.getNotifyContent(),
            //        NotificationType.INFORMATION);
            //notification.setDisplayId(displayId);

            OpenImageAction openImageAction = new OpenImageAction(cs.getNotifyAction(), notification);
            notification.addAction(openImageAction);
            Notifications.Bus.notify(notification);
            LOG.info("notify an info message");
        }

        private Notification obtainNotification(NotificationGroup notifiGroup,
                                                String notifyTitle,
                                                String notifyContent,
                                                NotificationType type) {
            return notifiGroup.createNotification(notifyTitle, notifyContent, type, null);
        }
    }
}
