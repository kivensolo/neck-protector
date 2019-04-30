package com.zeke.cd.images;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;
import com.zeke.cd.settings.GlobalSettings;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * {@link ImageManager} 实现类
 *
 * @author King.Z
 * @version 1.0
 * @since 2019-04-29
 */
public class BaseImageManager implements ImageManager {
    private static final Logger LOG = Logger.getInstance(BaseImageManager.class);

    private URL mImageUrl;

    /**
     * 可配置的图片列表
     *
     * <p>TODO 支持图片的可配置功能</p>
     */
    private List<URL> imageUrlList;

    private static BaseImageManager instance;

    private BaseImageManager() {
        this.mImageUrl = this.getDefaultUrl();
    }

    /**
     * 单例模式
     *
     * @return {@link BaseImageManager}
     */
    public static BaseImageManager getInstance() {
        return instance != null ? instance : (instance = new BaseImageManager());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nonnull
    public URL getImageUrl() {
        //try {
        //    return URI.create("file:/F:/github/kviensolo/neck-protect/out/production/resources/images/iron_man.jpg").toURL();
        //} catch (MalformedURLException e) {
        //    e.printStackTrace();
        //}
        return mImageUrl;
    }

    /**
     * 从插件 jar 中获取默认图片
     *
     * <p>默认图片地址是 "jar:file://{@code ${pluginPath}}/neck-protector.jar!/images/iron_man.jpg"</p>
     */
    private URL getDefaultUrl() {
        File pluginPath = getPluginFilePath();
        return getPluginJarFileURL(pluginPath, "!/images/iron_man.jpg");
    }

    public URL getHexComparisonPicUrl() {
        File pluginPath = getPluginFilePath();
        return getPluginJarFileURL(pluginPath, "!/images/opacity-comparison-table/hex.png");
    }

    private File getPluginFilePath() {
        PluginId pluginId = PluginId.getId(GlobalSettings.PLUGIN_ID);
        IdeaPluginDescriptor plugin = PluginManager.getPlugin(pluginId);
        if (plugin == null) {
            LOG.error("fail to get plugin \"" + GlobalSettings.PLUGIN_ID + "\"");
            throw new NullPointerException("fail to get plugin \"" + GlobalSettings.PLUGIN_ID + "\"");
        }
        return plugin.getPath();
    }

    private URL getPluginJarFileURL(File pluginFilePath, String subPath) {
        try {
            return new URL("jar:" + pluginFilePath.toURI().toURL().toString() + subPath);
        } catch (MalformedURLException e) {
            LOG.error("fail to get the file Url", e);
            throw new RuntimeException("fail to get file Url:", e);
        }
    }
}
