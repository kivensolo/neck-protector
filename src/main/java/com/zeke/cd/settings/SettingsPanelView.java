package com.zeke.cd.settings;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.ui.components.JBLabel;
import com.zeke.cd.lang.LanguageBundle;
import com.zeke.cd.notify.PluginDefaultConfig;
import com.zeke.cd.service.ConfigState;
import com.zeke.cd.service.IConfigService;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * 插件设置页面
 *
 * @see PluginSettingPage#createComponent()
 */
public class SettingsPanelView {
    private JPanel pluginSettingPanel;

    private JComboBox<String> imageSrcTypeBox;
    private JComboBox<String> remindTypeOptionsView;
    private TextFieldWithBrowseButton imageUrlChoiceView;

// <editor-fold defaultstate="collapsed" desc="Form key widgets">
    private JLabel pictureSettingTitleLabel;
    private JLabel pictureSourceLabel;
    private JLabel notifySettingTitleLabel;
    private JLabel remindModeLabel;
    private JLabel durationInMinutesLabel;
    private JLabel notifyTitleLabel;
    private JLabel notifyContentLabel;
    private JLabel notifyActionLabel;
// </editor-fold>


// <editor-fold defaultstate="collapsed" desc="Form value widgets">
    private JTextField periodMinutesView;
    private JTextField notifyTitleView;
    private JTextField notifyContentView;
    private JTextField notifyActionView;
// </editor-fold>

    public SettingsPanelView() {
    }

    private void createUIComponents() {
        initRemindTypeView();
        initImageSrcTypeView();
        initPicChoiceView();
        initOtherMultiLanguageUI();
    }

    private void initOtherMultiLanguageUI(){
        //==== Picture Setting ====
        String picSettingTitle = LanguageBundle.message(LanguageBundle.ID.SETTING_MAIN_TITLE_OF_IMAGE);
        this.pictureSettingTitleLabel = new JLabel(picSettingTitle);
        String picSourceTitle = LanguageBundle.message(LanguageBundle.ID.SETTING_IMAGE_SOURCE);
        this.pictureSourceLabel = new JLabel(picSourceTitle);

        //==== Notification Setting ====
        String notifySettingTitle = LanguageBundle.message(LanguageBundle.ID.SETTING_MAIN_TITLE_OF_NOTIFICATION);
        notifySettingTitleLabel = new JBLabel(notifySettingTitle);
        String remindModeTitle = LanguageBundle.message(LanguageBundle.ID.SETTING_LABEL_NOTIFY_MODE);
        remindModeLabel = new JBLabel(remindModeTitle);

        String intervalTitle = LanguageBundle.message(LanguageBundle.ID.SETTING_LABEL_NOTIFY_REMINDER_INTERVAL);
        durationInMinutesLabel = new JBLabel(intervalTitle);

        String notifyTitle = LanguageBundle.message(LanguageBundle.ID.SETTING_LABEL_NOTIFY_TITLE);
        notifyTitleLabel = new JBLabel(notifyTitle);
        String notifyContent = LanguageBundle.message(LanguageBundle.ID.SETTING_LABEL_NOTIFY_CONTENT);
        notifyContentLabel = new JBLabel(notifyContent);
        String notifyAction = LanguageBundle.message(LanguageBundle.ID.SETTING_LABEL_NOTIFY_LINK_TEXT);
        notifyActionLabel = new JBLabel(notifyAction);
    }

    /**
     * 初始化图片自定义选择view
     */
    private void initPicChoiceView() {
        imageUrlChoiceView = new TextFieldWithBrowseButton();
        imageUrlChoiceView.addActionListener(LocalImageSelector.newImageActionListener(imageUrlChoiceView));
        imageUrlChoiceView.setVisible(false);
    }

    /**
     * 初始化图片源方式下拉框可选项
     */
    private void initImageSrcTypeView() {
        ConfigState.ImageSrcTypeEnum[] values = ConfigState.ImageSrcTypeEnum.values();
        imageSrcTypeBox = new ComboBox<>();
        for (ConfigState.ImageSrcTypeEnum imageSrcType : values) {
            imageSrcTypeBox.addItem(imageSrcType.description);
        }
        imageSrcTypeBox.addItemListener(new ImageSrcChageAction());
    }

    /**
     * 初始化消息提醒方式下拉框可选项
     */
    private void initRemindTypeView() {
        remindTypeOptionsView = new ComboBox<>();
        for (ConfigState.RemindTypeEnum remindType : ConfigState.RemindTypeEnum.values()) {
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
     * 获取图片源方式
     */
    public int getImageSrcTypeOption(){
        return imageSrcTypeBox.getSelectedIndex();
    }

    /**
     * 设置图片源方式
     * <p>optionIndex 参数值：</p>
     * <ul>
     * <li>0. 内置默认图</li>
     * <li>1. Bing</li>
     * <li>2. 自定义图</li>
     * </ul>
     */
    public void setImageSrcTypeOption(int optionIndex) {
        int index;
        index = Math.max(optionIndex, ConfigState.ImageSrcTypeEnum.DEFAULT.index);
        imageSrcTypeBox.setSelectedIndex(Math.min(index, ConfigState.ImageSrcTypeEnum.CUSTOM.index));
    }

    /**
     * 获取提醒图片的绝对路径
     */
    public String getImageUrlFromBrowseButton() {
        return imageUrlChoiceView.getText();
    }

    /**
     * 设置提醒图片的绝对路径
     */
    public void setImageUrlToFromButton(String imageUrl) {
        imageUrlChoiceView.setText(imageUrl);
    }

    /**
     * 获取提醒间隔时间，单位分钟
     */
    public int getPeriodMinutes() {
        try {
            return Integer.parseInt(periodMinutesView.getText());
        } catch (NumberFormatException e) {
            return PluginDefaultConfig.PERIOD_MINUTES;
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


    /**
     * 重置配置参数为上次配置的数据，而非插件初始安装的默认值。
     */
    public void reset() {
        ConfigState configState = IConfigService.getInstance().getState();
        setImageSrcTypeOption(configState.getImageSrcType());
        setRemindTypeOption(configState.getRemindType());
        setImageUrlToFromButton(configState.getRemindImageUrl());

        setPeriodMinutes(configState.getPeriodMinutes());
        setNotifyTitle(configState.getNotifyTitle());
        setNotifyContent(configState.getNotifyContent());
        setNotifyAction(configState.getNotifyAction());
    }


    /**
     * 图片源下拉框选项改变的监听类
     */
    class ImageSrcChageAction implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            String desc = e.getItem().toString();
            ConfigState.ImageSrcTypeEnum type = ConfigState.ImageSrcTypeEnum.fromString(desc);
            if (type == ConfigState.ImageSrcTypeEnum.BING) {
                imageUrlChoiceView.setVisible(false);
            } else if (type == ConfigState.ImageSrcTypeEnum.DEFAULT) {
                imageUrlChoiceView.setVisible(false);
                //imageUrlChoiceView.setText(PluginDefaultConfig.IMAGE_URL);
            } else if (type == ConfigState.ImageSrcTypeEnum.CUSTOM) {
                imageUrlChoiceView.setVisible(true);
            }

        }
    }
}
