package com.zeke.cd.notify;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;

/**
 * 定时提醒组件接口
 *
 * <p>依赖于 Intellij Platform 组件实现</p>
 *
 * @author King.Z
 * @version 1.0
 * @since 2019-04-28 21:35
 */
public class BaseNotifyComponent implements INotifyComponent {
    public BaseNotifyComponent() {
        ApplicationManager.getApplication().invokeLater(() -> {
            Logger LOG = Logger.getInstance(BaseNotifyComponent.class);
            LOG.info("=== Start init Plugin Neck-Protector ===");
            NotifyTask.init();
            LOG.info("init sucessful.");
        });
    }
}
