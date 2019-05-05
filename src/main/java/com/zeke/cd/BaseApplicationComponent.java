package com.zeke.cd;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.zeke.cd.images.managers.BingImageManager;
import com.zeke.cd.notify.NotifyTask;

/**
 * Application组件实现类
 *
 * <p>依赖于 Intellij Platform 组件实现</p>
 *
 * @author King.Z
 * @version 1.0
 * @since 2019-04-28 21:35
 */
public class BaseApplicationComponent implements IApplicationComponent {
    public BaseApplicationComponent() {
        ApplicationManager.getApplication().invokeLater(() -> {
            Logger LOG = Logger.getInstance(BaseApplicationComponent.class);
            LOG.info("=== Start init Plugin Neck-Protector ===");
            NotifyTask.init();
            BingImageManager.getInstance().init();
            LOG.info("init sucessful.");
        });
    }
}
