package com.zeke.cd.lang;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

/**
 * 多语言服务
 *
 * <p>依赖基于 {@link com.intellij.AbstractBundle} 实现</p>
 */
public class LanguageBundle extends DynamicBundle {
    private static final LanguageBundle INSTANCE = new LanguageBundle();
    private static final String LANG_PATH = "lang.SettingsBundle";

    private LanguageBundle() {
        super(LANG_PATH);
    }


    /**
     * 获取国际化值
     *
     * @param id     {@code template.properties} 中的键
     * @param params {@code template.properties} 中的值占位符
     * @return 国际化值
     * @see ID
     */
    public static String message(@PropertyKey(resourceBundle = LANG_PATH) @NotNull LanguageBundle.ID id,
            @NotNull  Object... params) {
        return INSTANCE.getMessage(id.value, params);
    }

    public enum ID {
        // 插件
        PLUGIN_NAME("plugin.name"),

        // 配置界面 --- 图片设置
        SETTING_MAIN_TITLE_OF_IMAGE("setting.main_title.image"),
        SETTING_IMAGE_SOURCE("setting.image.source"),
        SETTING_IMAGE_DISPLAY_VALUE_BING("setting.image.display.value.bing"),
        SETTING_IMAGE_DISPLAY_VALUE_BUILD_IN("setting.image.display.value.build_in"),
        SETTING_IMAGE_DISPLAY_VALUE_LOCAL("setting.image.display.value.local"),

        // 配置界面 --- 通知设置
        SETTING_MAIN_TITLE_OF_NOTIFICATION("setting.main_title.notification"),
        SETTING_LABEL_NOTIFY_CONTENT("setting.label.notify.content"),
        SETTING_LABEL_NOTIFY_LINK_TEXT("setting.label.notify.link.text"),
        SETTING_LABEL_NOTIFY_TITLE("setting.label.notify.title"),
        SETTING_LABEL_NOTIFY_REMINDER_INTERVAL("setting.label.notify.reminder.interval"),
        // 提醒方式
        SETTING_LABEL_NOTIFY_MODE("setting.label.notify.mode"),
        SETTING_NOTIFY_MODE_TIPS("setting.notify.mode.tips"),
        SETTING_NOTIFY_MODE_DIRECTLY("setting.notify.mode.directly"),

        NOTIFY_MESSAGE_TITLE("notify.message.tltle"),
        NOTIFY_MESSAGE_CONTENT("nofity.message.content"),
        NOTIFY_MESSAGE_ACTION_TEXT("notify.message.action_link");

        final String value;

        ID(String value) {
            this.value = value;
        }
    }

}
