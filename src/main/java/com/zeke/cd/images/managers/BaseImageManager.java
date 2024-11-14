package com.zeke.cd.images.managers;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;
import com.zeke.cd.notify.PluginDefaultConfig;
import com.zeke.cd.settings.GlobalSettings;
import com.zeke.cd.utils.Utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

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
        return getInnerImageURL(
                getNeckProtectJarFilePath(),
                "!/images/opacity-comparison-table/hex.png"
        );
    }

    /**
     * 从插件 jar 中获取默认图片
     *
     * <p>默认图片地址是 "jar:file://{@code ${pluginPath}}/neck-protector.jar!/images/shaking_head.jpg"</p>
     *
     */
    public static URL getDefaultUrl() {
        return getInnerImageURL(
                getNeckProtectJarFilePath(),
                "!/images/coastline.jpg"
        );
    }

    private static File getNeckProtectJarFilePath() {
        File pluginJarPath = getPluginFilePath();
        if(PluginDefaultConfig.SANDBOX_MODE){
            String path = pluginJarPath.getAbsolutePath();
            //沙箱模式下插件jar包路径: $APPLICATION_PLUGINS_DIR$\neck-protect\lib\neck-protect.jar
            pluginJarPath = new File(Utils.join(File.separator,new String[]{path,"lib","neck-protect.jar"}));
            if(!pluginJarPath.exists()){
                LOG.info("neck-protect.jar is not exist in idea-sandbox/plugins director.!");
                //插件jar包不存在，进行兼容性查找
                pluginJarPath = new File(Utils.join(File.separator,new String[]{path,"lib","instrumented-neck-protect.jar"}));
            }
        }
        return pluginJarPath;
    }

    /**
     * 获取插件路径
     * 应用插件根目录(APPLICATION_PLUGINS_DIR)：<br>
     * --- 正式环境<br>
     * {@code APPLICATION_PLUGINS_DIR: $USER_HOME$\.IntelliJIdea201X.X\config\plugins\} <br>
     * --- 沙箱环境<br>
     * {@code APPLICATION_PLUGINS_DIR: $PROJECT_ROOT$\build\idea-sandbox\plugins\} <br>
     *
     * @return 当前插件目录(Current plugin directory) <br>
     *      --- 正式环境(Release Evn):<br>
     *      $APPLICATION_PLUGINS_DIR$/neck-protector.jar  <br>
     *      --- 沙箱环境(SandBox Evn):<br>
     *      $APPLICATION_PLUGINS_DIR$/neck-protect  <br>
     */
    public static File getPluginFilePath() {
        PluginId pluginId = PluginId.getId(GlobalSettings.PLUGIN_ID);
        IdeaPluginDescriptor plugin = PluginManagerCore.getPlugin(pluginId);
        if (plugin == null) {
            LOG.error("fail to get plugin \"" + GlobalSettings.PLUGIN_ID + "\"");
            throw new NullPointerException("fail to get plugin \"" + GlobalSettings.PLUGIN_ID + "\"");
        }
        Path path = plugin.getPluginPath();
        return path == null ? null : path.toFile();
    }

    private static URL getInnerImageURL(File prefixPath, String subPath) {
        try {
            LOG.info("getInnerImageURL：" + prefixPath.getAbsolutePath());
            return new URL("jar:" + prefixPath.toURI().toURL() + subPath);
        } catch (MalformedURLException e) {
            LOG.error("fail to get the file Url", e);
            throw new RuntimeException("fail to get file Url:", e);
        }
    }
}
