package com.zeke.cd.images;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;
import com.zeke.cd.settings.GlobalSettings;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
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
        //return mImageUrl;
        try {
            return URI.create("file:/F:/github/kviensolo/neck-protect/out/production/resources/images/iron_man.jpg").toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return mImageUrl;
    }

    /**
     * 从插件 jar 中获取默认图片
     *
     * <p>默认图片地址是 "jar:file://{@code ${pluginPath}}/neck-protector.jar!/images/iron_man.jpg"</p>
     */
    private URL getDefaultUrl() {
        PluginId pluginId = PluginId.getId(GlobalSettings.PLUGIN_ID);
        IdeaPluginDescriptor plugin = PluginManager.getPlugin(pluginId);
        if (plugin == null) {
            LOG.error("fail to get plugin \"" + GlobalSettings.PLUGIN_ID + "\"");
            throw new NullPointerException("fail to get plugin \"" + GlobalSettings.PLUGIN_ID + "\"");
        }
        File pluginPath = plugin.getPath();
        try {
            return new URL("jar:" + pluginPath.toURI().toURL().toString() + "!/images/iron_man.jpg");
        } catch (MalformedURLException e) {
            LOG.error("fail to get the default imageUrl", e);
            throw new RuntimeException("fail to get the default imageUrl", e);
        }
    }
}
