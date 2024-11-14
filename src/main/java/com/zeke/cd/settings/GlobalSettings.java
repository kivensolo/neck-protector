package com.zeke.cd.settings;

/**
 * @author King.Z <br>
 * @since  2019/4/28 21:13 <br>
 * description: 全局静态配置类 <br>
 */
public class GlobalSettings {
    /**
     * 插件标识
     *
     * <p>与 {@code /META-INF/plugin.xml} 中 {@code <id>} 内容一致</p>
     */
    public static final String PLUGIN_ID = "com.zeke.wong.neck-protect";
    /**
     * 插件名称
     */
    public static final String PLUGIN_NAME = "NeckProtector";
    /**
     * 插件打包后的 jar 包名称
     */
    public static final String JAR_NAME = "neck-protector-plugin.jar";

    /**
     * 插件的消息通知分组id，由扩展点定义
     */
    public static final String NOTIFICATION_GROUP_ID = "NeckProtector_NOTIFY";
}
