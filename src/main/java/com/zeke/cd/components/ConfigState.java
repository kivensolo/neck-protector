package com.zeke.cd.components;

import com.intellij.util.xmlb.annotations.OptionTag;
import com.zeke.cd.notify.NotifyConfig;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 配置状态类
 *
 * @author King.Z
 * @version 1.0
 * @see <a href="https://www.jetbrains.org/intellij/sdk/docs/basics/persisting_state_of_components.html">SDK DevGuide</a>
 * @since 2019-04-28
 */
public class ConfigState {
    @OptionTag
    private Integer remindType;
    @OptionTag
    private String remindImageUrl;
    @OptionTag
    private Integer periodMinutes;
    @OptionTag
    private String notifyTitle;
    @OptionTag
    private String notifyContent;
    @OptionTag
    private String notifyAction;

    /**
     * 提醒方式枚举类
     *
     * @see
     */
    public enum RemindTypeEnum {
        /** 直接打开图片 */
        DIRECT(0, "直接显示图片"),
        /** 间接打开图片 */
        INDIRECT(1, "消息通知 -> 打开图片");

        public final int index;
        public final String description;

        RemindTypeEnum(int index, String description) {
            this.index = index;
            this.description = description;
        }

        public static RemindTypeEnum valueOf(int index) {
            return Stream.of(RemindTypeEnum.values())
                    .filter(remindType -> index == remindType.index)
                    .findFirst()
                    .orElseThrow(NullPointerException::new);
        }
    }

    /**
     * 默认配置对象
     *
     * <p>在 {@link IConfigComponent#updateState(ConfigState)} 时，新配置对象会与默认配置对象作比较，
     * IDEA 会保存有差异的字段至 {@link ConfigComponentImpl} 指定的 {@code neckProtector.xml} 配置文件中</p>
     *
     * @see IConfigComponent#getState()
     * @see IConfigComponent#updateState(ConfigState)
     */
    public ConfigState() {
        // 第一次开启插件时，应该使用默认配置
        this.remindType = NotifyConfig.REMIND_TYPE;
        this.remindImageUrl = NotifyConfig.DISPLAY_IMAGE_URL;
        this.periodMinutes = NotifyConfig.PERIOD_MINUTES;
        this.notifyTitle = NotifyConfig.NOTIFY_TITLE;
        this.notifyContent = NotifyConfig.NOTIFY_CONTENT;
        this.notifyAction = NotifyConfig.NOTIFY_ACTION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigState that = (ConfigState) o;
        return Objects.equals(remindType, that.remindType) &&
                Objects.equals(remindImageUrl, that.remindImageUrl) &&
                Objects.equals(periodMinutes, that.periodMinutes) &&
                Objects.equals(notifyTitle, that.notifyTitle) &&
                Objects.equals(notifyContent, that.notifyContent) &&
                Objects.equals(notifyAction, that.notifyAction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remindType, remindImageUrl, periodMinutes, notifyTitle, notifyContent, notifyAction);
    }

    // getter and setter

    public Integer getRemindType() {
        return remindType;
    }

    public void setRemindType(Integer remindType) {
        this.remindType = remindType;
    }

    public String getRemindImageUrl() {
        return remindImageUrl;
    }

    public void setRemindImageUrl(String remindImageUrl) {
        this.remindImageUrl = remindImageUrl;
    }

    public Integer getPeriodMinutes() {
        return periodMinutes;
    }

    public void setPeriodMinutes(Integer periodMinutes) {
        this.periodMinutes = periodMinutes;
    }

    public String getNotifyTitle() {
        return notifyTitle;
    }

    public void setNotifyTitle(String notifyTitle) {
        this.notifyTitle = notifyTitle;
    }

    public String getNotifyContent() {
        return notifyContent;
    }

    public void setNotifyContent(String notifyContent) {
        this.notifyContent = notifyContent;
    }

    public String getNotifyAction() {
        return notifyAction;
    }

    public void setNotifyAction(String notifyAction) {
        this.notifyAction = notifyAction;
    }
}
