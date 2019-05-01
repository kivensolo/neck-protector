package com.zeke.cd.settings;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.zeke.cd.notify.NotifyConfig;
import com.zeke.cd.service.ConfigState;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * 插件设置页面
 *
 * @see PluginSettingPage#createComponent()
 */
public class PluginSettingsPanelView {
    private JPanel pluginSettingPanel;

    private JComboBox<String> remindTypeOptionsView;
    private TextFieldWithBrowseButton imageUrlChoiceView;
    private JButton defaultImageBtn;
    private JTextField periodMinutesView;
    private JTextField notifyTitleView;
    private JTextField notifyContentView;
    private JTextField notifyActionView;

    public PluginSettingsPanelView() {
    }

    private void createUIComponents() {
        initRemindTypeView();
        initPicChoiceView();
        initDefaultBtnView();
    }

    private void initDefaultBtnView() {
        defaultImageBtn = new JButton();
        defaultImageBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageUrlChoiceView.setText(NotifyConfig.DISPLAY_IMAGE_URL);
            }
        });
    }

    private void initPicChoiceView() {
        imageUrlChoiceView = new TextFieldWithBrowseButton();
        imageUrlChoiceView.addActionListener(PluginSettingConfig.newBrowseFolderActionListener(imageUrlChoiceView));
    }

    private void initRemindTypeView() {
        remindTypeOptionsView = new ComboBox<>();
        for (ConfigState.RemindTypeEnum remindType : ConfigState.RemindTypeEnum.values()) {
            //初始化下拉框可选项
            remindTypeOptionsView.addItem(remindType.description);
        }
    }

    /**
     * 获取提醒方式
     *
     * @return {@link com.zeke.cd.service.ConfigState.RemindTypeEnum}
     */
    public int getRemindTypeOption() {
        return remindTypeOptionsView.getSelectedIndex();
    }

    /**
     * 设置提醒方式
     *
     * <p>optionIndex 参数值：</p>
     * <ul>
     * <li>0. 消息通知 -> 打开图片</li>
     * <li>1. 打开图片</li>
     * </ul>
     *
     * @param optionIndex 0 或 1
     */
    public void setRemindTypeOption(int optionIndex) {
        int idnex;
        idnex = Math.max(optionIndex, 0);
        remindTypeOptionsView.setSelectedIndex(Math.min(idnex, 1));
    }

    /**
     * 获取提醒图片的绝对路径
     */
    public String getImageUrl() {
        return imageUrlChoiceView.getText();
    }

    /**
     * 设置提醒图片的绝对路径
     */
    public void setImageUrl(String imageUrl) {
        imageUrlChoiceView.setText(imageUrl);
    }

    /**
     * 获取提醒间隔时间，单位分钟
     */
    public int getPeriodMinutes() {
        try {
            return Integer.parseInt(periodMinutesView.getText());
        } catch (NumberFormatException e) {
            return NotifyConfig.PERIOD_MINUTES;
        }
    }

    /**
     * 设置提醒间隔时间，单位分钟
     */
    public void setPeriodMinutes(int periodMinutes) {
        periodMinutesView.setText(String.valueOf(periodMinutes));
    }

    /**
     * 获取通知文案的标题
     */
    public String getNotifyTitle() {
        return notifyTitleView.getText();
    }

    /**
     * 设置通知文案的标题
     */
    public void setNotifyTitle(String notifyTitle) {
        notifyTitleView.setText(notifyTitle);
    }

    /**
     * 获取通知文案的内容
     */
    public String getNotifyContent() {
        return notifyContentView.getText();
    }

    /**
     * 设置通知文案的内容
     */
    public void setNotifyContent(String notifyContent) {
        notifyContentView.setText(notifyContent);
    }

    /**
     * 获取通知文案的按钮
     */
    public String getNotifyAction() {
        return notifyActionView.getText();
    }

    /**
     * 设置通知文案的按钮
     */
    public void setNotifyAction(String notifyAction) {
        notifyActionView.setText(notifyAction);
    }

    public JPanel getPluginSettingPanel() {
        return pluginSettingPanel;
    }
}
