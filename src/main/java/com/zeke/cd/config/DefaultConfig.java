package com.zeke.cd.config;

import java.util.concurrent.TimeUnit;

/**
 * 默认配置接口
 *
 * @author King.Z
 * @version 1.0
 * @since 2019-04-28
 */
public interface DefaultConfig {

    /** 默认提醒方式 */
    Integer REMIND_TYPE = ConfigState.RemindTypeEnum.INDIRECT.index;

    ///**
    // * 默认提醒图片的绝对路径
    // */
    //String REMIND_IMAGE_URL = ImageManager.getInstance().getImageUrl().toString();

    /** 默认提醒间隔时间 */
    Integer PERIOD_MINUTES = (int) TimeUnit.HOURS.toMinutes(1);

    /** 默认通知文案的标题 */
    String NOTIFY_TITLE = "NeckProtector";

    /** 默认通知文案的内容 */
    String NOTIFY_CONTENT = "亲～该休息休息啦！小心得颈椎病哦~";

    /** 默认通知文案的按钮 */
    String NOTIFY_ACTION = "放松一下";
}
