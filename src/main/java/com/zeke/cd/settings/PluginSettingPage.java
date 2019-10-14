package com.zeke.cd.settings;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.ui.Messages;
import com.zeke.cd.notify.NotifyTask;
import com.zeke.cd.service.ConfigState;
import com.zeke.cd.service.IConfigService;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * 可搜索的插件配置设置页面
 *
 * @author kingz.Z
 * @version 1.0
 * @see <a href="http://corochann.com/intellij-plugin-development-introduction-applicationconfigurable-projectconfigurable-873.html">一篇很不错的关于开发 Intellij Plugin 配置页面的博客</a>
 * @since 2019-04-29
 */
public class PluginSettingPage implements SearchableConfigurable {
    private static final Logger LOG = Logger.getInstance(PluginSettingPage.class);
    /**
     * 插件设置页面对象
     */
    private SettingsPanelView settingsView;

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String getId() {
        return GlobalSettings.PLUGIN_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return GlobalSettings.PLUGIN_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    /**
     * IDEA 初始化设置页面时，会调用此方法
     *
     * @return 由 {@code UI Designer} 生成的 {@link SettingsPanelView} 页面
     */
    @Nullable
    @Override
    public JComponent createComponent() {
        if (this.settingsView == null) {
            this.settingsView = new SettingsPanelView();
        }
        return this.settingsView.getPluginSettingPanel();
    }

    /**
     * IDEA 初始化设置页面时，判断 "Apply" 按钮是否为可用
     *
     * @return true 是；false 否
     */
    @Override
    public boolean isModified() {
        return this.settingsView != null;
    }

    /**
     * 用户点击 "Apply" 按钮或 "OK" 按钮之后，会调用此方法
     */
    @Override
    public void apply() {
        if (this.settingsView == null) return;
        int periodMinutes = settingsView.getPeriodMinutes();
        if (!isPeriodValid(periodMinutes)) {
            return;
        }
        ConfigState configState = IConfigService.getInstance().getState();
        configState.setPeriodMinutes(periodMinutes);
        configState.setImageSrcType(settingsView);
        configState.setRemindType(settingsView.getRemindTypeOption());
        configState.setNotifyTitle(settingsView.getNotifyTitle());
        configState.setNotifyContent(settingsView.getNotifyContent());
        configState.setNotifyAction(settingsView.getNotifyAction());
        IConfigService.getInstance().updateState(configState);
        LOG.info("apply and save user setting");

        NotifyTask.restart();
        LOG.info("restart scheduled remind task");
    }

    private boolean isPeriodValid(int periodMinutes) {
        if (periodMinutes <= 0) {
            Messages.showMessageDialog("Greater than zero is a valid paramete ~~",
                    "Caveat",
                    Messages.getInformationIcon());
            return false;
        }
        return true;
    }

    /**
     * IDEA 初始化设置页面或者用户点击 "Reset" 按钮之后，会调用此方法
     */
    @Override
    public void reset() {
        if (settingsView == null) return;
        settingsView.reset();
        LOG.info("reset user setting");
    }

    /**
     * IDEA 销毁设置页面后，会调用此方法
     */
    @Override
    public void disposeUIResources() {
        settingsView = null;
    }
}
