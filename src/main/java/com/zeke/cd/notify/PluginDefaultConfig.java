package com.zeke.cd.notify;

import com.zeke.cd.images.managers.DefaultImageManager;
import com.zeke.cd.lang.LanguageBundle;
import com.zeke.cd.service.ConfigState;

import java.util.concurrent.TimeUnit;

/**
 * 默认配置接口
 *
 * @author King.Z
 * @version 1.0
 * @since 2019-04-28
 */
public class PluginDefaultConfig {

    /** 沙箱调试模式 --- Debug模式下请开启 */
    public static boolean SANDBOX_MODE = false;

    /** 默认图片源 --- Jar包内置 */
    public static Integer IAMGE_SRC = ConfigState.ImageSrcTypeEnum.DEFAULT.index;

    /** 默认提醒方式 --- 间接打开图片 */
    public static Integer NOTIFY_TYPE = ConfigState.RemindTypeEnum.INDIRECT.index;

    /** 默认提醒图片的绝对路径 */
    public static String IMAGE_URL = DefaultImageManager.getDefaultUrl().toString();

    /** 默认提醒间隔时间 */
    public static Integer PERIOD_MINUTES = (int) TimeUnit.HOURS.toMinutes(1);

    /** 默认通知文案的标题 */
    public static String NOTIFY_TITLE = LanguageBundle.message(LanguageBundle.ID.NOTIFY_MESSAGE_TITLE);

    /** 默认通知文案的内容 */
    public static  String NOTIFY_CONTENT = LanguageBundle.message(LanguageBundle.ID.NOTIFY_MESSAGE_CONTENT);

    /** 默认通知文案的按钮 */
    public static  String NOTIFY_ACTION = LanguageBundle.message(LanguageBundle.ID.NOTIFY_MESSAGE_ACTION_TEXT);
}
