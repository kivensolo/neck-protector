package com.zeke.cd.actions;

import com.intellij.ide.DataManager;
import com.intellij.notification.Notification;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.AsyncResult;
import com.intellij.util.Consumer;
import com.zeke.cd.images.OpenImageConsumer;
import com.zeke.cd.utils.Utils;
import org.jetbrains.annotations.NotNull;

/**
 * 从消息提示框打开图片的行为事件
 *
 * @author king.Z
 * @version 1.0
 * @since 2019-04-29
 */
public class OpenImageAction extends AnAction {

    private static final Logger LOG = Logger.getInstance(OpenImageAction.class);
    private final Notification notification;

    /**
     * 构造方法
     *
     * @param text         通知中可点击的按钮的文案
     * @param notification 通知对象，在点击按钮之后需要调用 {@link Notification#expire()}
     */
    public OpenImageAction(String text, Notification notification) {
        super(text);
        this.notification = notification;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        AsyncResult<DataContext> dataContextAsyncResult = DataManager.getInstance()
                .getDataContextFromFocus()
                .doWhenDone((Consumer<DataContext>) (dataContext -> new OpenImageConsumer().accept(dataContext)));

        String fullVersion = ApplicationInfo.getInstance().getFullVersion();
        LOG.info("obtainNotification, currenIDEAVersion=" + fullVersion);
        boolean before202317 = Utils.isVersionLessOrEqu(fullVersion, "2023.1.7");
        if(before202317){
            dataContextAsyncResult.doWhenRejected((Consumer<String>) LOG::error);
        }else{
            dataContextAsyncResult.doWhenRejected(LOG::error);
        }

        // 使打开图片按钮失效，避免重复点击
        notification.expire();
        LOG.info("notification action has been expired");
    }
}
