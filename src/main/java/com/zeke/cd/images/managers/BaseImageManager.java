package com.zeke.cd.images.managers;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;
import com.zeke.cd.notify.PluginDefaultConfig;
import com.zeke.cd.settings.GlobalSettings;
import com.zeke.cd.utils.Utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * {@link ImageManager} 实现类
 *
 * @author King.Z
 * @version 1.0
 * @since 2019-04-29
 */
public abstract class BaseImageManager implements ImageManager {
    private static final Logger LOG = Logger.getInstance(BaseImageManager.class);

    URL mImageUrl;
    protected static ImageManagerFactory imageManagerFactory;

    protected BaseImageManager() {}

    public static ImageManagerFactory getImageManagerFactory(){
        if(imageManagerFactory == null){
            imageManagerFactory = new ImageManagerFactory();
        }
        return imageManagerFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract URL getImageUrl();

    public static URL getHexComparisonPicUrl() {
        File pluginPath = getPluginFilePath();
        return getPluginJarFileURL(pluginPath, "!/images/opacity-comparison-table/hex.png");
    }

    /**
     * 从插件 jar 中获取默认图片
     *
     * <p>默认图片地址是 "jar:file://{@code ${pluginPath}}/neck-protector.jar!/images/shaking_head.jpg"</p>
     *
     */
    public static URL getDefaultUrl() {
        File pluginPath = getPluginFilePath();
        if(PluginDefaultConfig.SANDBOX_MODE){
            //沙箱模式下插件jar包路径: $APPLICATION_PLUGINS_DIR$\neck-protect\lib\neck-protect.jar
            pluginPath = new File(Utils.join(File.separator,new String[]{pluginPath.getAbsolutePath(),"lib","neck-protect.jar"}));
        }
        LOG.info("getDefaultUrl  getPlugin path ==== " + pluginPath.getAbsolutePath());
        return getPluginJarFileURL(pluginPath, "!/images/shaking_head.jpg");
    }

    /**
     * 获取插件路径
     * 应用插件根目录(APPLICATION_PLUGINS_DIR)：<br>
     * --- 正式环境<br>
     * {@code APPLICATION_PLUGINS_DIR: $USER_HOME$\.IntelliJIdea201X.X\config\plugins\} <br>
     * --- 沙箱环境<br>
     * {@code APPLICATION_PLUGINS_DIR: $PROJECT_ROOT$\build\idea-sandbox\plugins\} <br>
     *
     * @return 当前插件目录 <br>
     *      --- 正式环境:<br>
     *      $APPLICATION_PLUGINS_DIR$/neck-protector.jar  <br>
     *      --- 沙箱环境:<br>
     *      $APPLICATION_PLUGINS_DIR$/neck-protect  <br>
     */
    public static File getPluginFilePath() {
        PluginId pluginId = PluginId.getId(GlobalSettings.PLUGIN_ID);
        IdeaPluginDescriptor plugin = PluginManager.getPlugin(pluginId);
        if (plugin == null) {
            LOG.error("fail to get plugin \"" + GlobalSettings.PLUGIN_ID + "\"");
            throw new NullPointerException("fail to get plugin \"" + GlobalSettings.PLUGIN_ID + "\"");
        }
        return plugin.getPath();
    }

    public static URL getPluginJarFileURL(File pluginFilePath, String subPath) {
        try {
            return new URL("jar:" + pluginFilePath.toURI().toURL().toString() + subPath);
        } catch (MalformedURLException e) {
            LOG.error("fail to get the file Url", e);
            throw new RuntimeException("fail to get file Url:", e);
        }
    }
}
