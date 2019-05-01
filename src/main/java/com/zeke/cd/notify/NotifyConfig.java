package com.zeke.cd.notify;

import com.zeke.cd.components.ConfigState;
import com.zeke.cd.images.BaseImageManager;

import java.util.concurrent.TimeUnit;

/**
 * 默认配置接口
 *
 * @author King.Z
 * @version 1.0
 * @since 2019-04-28
 */
public class NotifyConfig {

    /** 默认提醒方式 */
    public static Integer REMIND_TYPE = ConfigState.RemindTypeEnum.INDIRECT.index;

    /** 默认提醒图片的绝对路径 */
    public static String DISPLAY_IMAGE_URL = BaseImageManager.getInstance().getImageUrl().toString();

    /** 默认提醒间隔时间 */
    public static Integer PERIOD_MINUTES = (int) TimeUnit.HOURS.toMinutes(1);

    /** 默认通知文案的标题 */
    public static String NOTIFY_TITLE = "NeckProtector";

    /** 默认通知文案的内容 */
    public static  String NOTIFY_CONTENT = "亲～该休息休息啦！小心得颈椎病哦~";

    /** 默认通知文案的按钮 */
    public static  String NOTIFY_ACTION = "看图放松一下";
}
