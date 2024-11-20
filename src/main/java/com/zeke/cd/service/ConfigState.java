package com.zeke.cd.service;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.xmlb.annotations.OptionTag;
import com.zeke.cd.images.managers.BaseImageManager;
import com.zeke.cd.images.managers.CustomImageManager;
import com.zeke.cd.lang.LanguageBundle;
import com.zeke.cd.notify.PluginDefaultConfig;
import com.zeke.cd.settings.SettingsPanelView;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
    private static final Logger LOG = Logger.getInstance(ConfigState.class);
    @OptionTag
    private Integer imageSrcType;
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
     * 图片来源枚举类
     */
    public enum ImageSrcTypeEnum {

        /**
         * 使用插件自带默认图
         */
        DEFAULT(0, LanguageBundle.message(LanguageBundle.ID.SETTING_IMAGE_DISPLAY_VALUE_BUILD_IN)),

        /**
         * 使用Bing搜索的图片
         */
        BING(1, LanguageBundle.message(LanguageBundle.ID.SETTING_IMAGE_DISPLAY_VALUE_BING)),

        /**
         * 自定义本地图片
         */
        CUSTOM(2, LanguageBundle.message(LanguageBundle.ID.SETTING_IMAGE_DISPLAY_VALUE_LOCAL));

        private static Map<String, ImageSrcTypeEnum> stringToEnum = new HashMap<>();

        static {
            for (ImageSrcTypeEnum act : values()) {
                stringToEnum.put(act.toString(), act);
            }
        }

        public final int index;
        public final String description;

        ImageSrcTypeEnum(int index, String description) {
            this.index = index;
            this.description = description;
        }

        public static ImageSrcTypeEnum valueOf(int index) {
            return Stream.of(ImageSrcTypeEnum.values())
                    .filter(imageSrcType -> index == imageSrcType.index)
                    .findFirst()
                    .orElseThrow(NullPointerException::new);
        }

        @Override
        public String toString() {
            return description;
        }

        public static ImageSrcTypeEnum fromString(String type) {
            return stringToEnum.get(type);
        }
    }

    /**
     * 提醒方式枚举类
     */
    public enum RemindTypeEnum {
        /**
         * 直接打开图片
         */
        DIRECT(0, LanguageBundle.message(LanguageBundle.ID.SETTING_NOTIFY_MODE_DIRECTLY)),
        /**
         * 间接打开图片
         */
        INDIRECT(1, LanguageBundle.message(LanguageBundle.ID.SETTING_NOTIFY_MODE_TIPS));

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
     * <p>在 {@link IConfigService#updateState(ConfigState)} 时，新配置对象会与默认配置对象作比较，
     * IDEA 会保存有差异的字段至 {@link ConfigServiceImpl} 指定的
     * {@code neckProtector.xml} 配置文件中</p>
     *
     * @see IConfigService#getState()
     * @see IConfigService#updateState(ConfigState)
     */
    public ConfigState() {
        // 第一次开启插件时，应该使用默认配置
        this.imageSrcType = PluginDefaultConfig.IAMGE_SRC;
        this.remindType = PluginDefaultConfig.NOTIFY_TYPE;
        this.remindImageUrl = PluginDefaultConfig.IMAGE_URL;
        this.periodMinutes = PluginDefaultConfig.PERIOD_MINUTES;
        this.notifyTitle = PluginDefaultConfig.NOTIFY_TITLE;
        this.notifyContent = PluginDefaultConfig.NOTIFY_CONTENT;
        this.notifyAction = PluginDefaultConfig.NOTIFY_ACTION;
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
    public Integer getImageSrcType() {
        return imageSrcType;
    }

    public void setImageSrcType(Integer type) {
        this.imageSrcType = type;
    }

    public void setImageSrcType(SettingsPanelView settingView) {
        setImageSrcType(settingView.getImageSrcTypeOption());

        ImageSrcTypeEnum typeEnum = ImageSrcTypeEnum.valueOf(imageSrcType);
        BaseImageManager imageManager = BaseImageManager.getImageManagerFactory().create(typeEnum);
        URL imageUrl = imageManager.getImageUrl();
        LOG.info("setImageSrcType to ===> " + typeEnum.description.toUpperCase());
        if(imageManager instanceof CustomImageManager){
            String newImageUrl = settingView.getImageUrlFromBrowseButton();
            if(imageUrl!= null && !imageUrl.toString().equals(newImageUrl)){
                LOG.info("Picture changed! update custom picture:" + newImageUrl);
                ((CustomImageManager)imageManager).setImageUrl(newImageUrl);
                imageUrl = imageManager.getImageUrl();
            }
        }
        LOG.info("imageUrl="+imageUrl);
        if(imageUrl != null){
            setRemindImageUrl(imageUrl.toString());
        }
    }

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
