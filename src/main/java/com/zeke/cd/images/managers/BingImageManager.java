package com.zeke.cd.images.managers;

import com.google.gson.Gson;
import com.intellij.openapi.diagnostic.Logger;
import com.zeke.cd.images.BingImageBean;
import com.zeke.cd.images.HttpUtils;
import com.zeke.cd.notify.PluginDefaultConfig;
import com.zeke.cd.service.ConfigState;
import com.zeke.cd.service.IConfigService;
import com.zeke.cd.utils.Utils;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Bing图片的管理类
 * @author king.Z
 * @version 1.0
 * @since 2019-05-03 14:34
 */
public class BingImageManager extends BaseImageManager {
    private static final Logger LOG = Logger.getInstance(BingImageManager.class);

    private static BingImageManager instance;
    private static final String FILE_PROTOCOL = "file:";
    private static final String BING_HOST = "http://www.bing.com";
    private static final String BING_REQEST = "http://cn.bing.com/HPImageArchive.aspx?format=js&idx=%s&n=1";
    private File cacheBingPath;
    private File cacheImageFilePath;
    private BingImageManager() {
        super();
        initCachePath();
    }

    private void initCachePath() {
        File pluginFilePath = getPluginFilePath();
        String path = pluginFilePath.getAbsolutePath();
        String cacheFilePath;
        if(PluginDefaultConfig.SANDBOX_MODE){
           cacheFilePath = Utils.join(File.separator, new String[]{path, "neck_protect_cache","bing"});
        }else{
           cacheFilePath = Utils.join(File.separator, new String[]{pluginFilePath.getParent(), "neck_protect_cache","bing"});
        }
        cacheBingPath = new File(cacheFilePath);
        if (!cacheBingPath.exists()) {
            cacheBingPath.mkdirs();
        }
    }

    public static BingImageManager getInstance() {
        return instance != null ? instance : (instance = new BingImageManager());
    }

    @Override
    public URL getImageUrl() {
        if(mImageUrl != null){
            Pattern winPattern = getWinPattern();
            Pattern unixPattern = getUnixPattern();
            if (winPattern.matcher(mImageUrl.toString()).matches() ||
                    unixPattern.matcher(mImageUrl.toString()).matches()) {
                return mImageUrl;
            }
        }

        //内存缓存非Bing图片数据时,根据缓存文件更新图片URL对象
        if (cacheImageFilePath != null && cacheImageFilePath.exists()) {
            try {
                mImageUrl = new URL(FILE_PROTOCOL + cacheImageFilePath.getAbsolutePath());
                return mImageUrl;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        LOG.warn("Get Bing image file url failed. Use default image.");
        return DefaultImageManager.getDefaultUrl();
    }

    /**
     * 初始化Bing图片模块
     */
    public void init() {
        preFeatchImage();
    }

    /**
     * 预拉取一次当日bing图片
     */
    private void preFeatchImage() {
        //TODO V1.1 增加请求页数处理
        String url = String.format(BING_REQEST, "0");
        HttpUtils.sendHttpRequest(url, HttpUtils.GET, new HttpUtils.CallBack() {
            @Override
            public void onFinish(String response) {
                try {
                    BingImageBean imageBean = new Gson().fromJson(response, BingImageBean.class);
                    if (imageBean.images.length > 0) {
                        LOG.info("Bing图片数据信息正常,下载");
                        downloadImageByUrl(imageBean);
                    } else {
                        LOG.info("数据异常");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void downloadImageByUrl(BingImageBean imageBean) {
        String url = imageBean.images[0].url;
        LOG.info("图片URL = " + url);
        //String urlbase = imageBean.images[0].urlbase;
        String cacheFileName = buildCacheFileName(url);

        String imageFullUrl = BING_HOST.concat(url);
        String bingPath = cacheBingPath.getAbsolutePath();
        String imageFilePath = Utils.join(File.separator, new String[]{bingPath,cacheFileName});
        cacheImageFilePath = new File(imageFilePath);
        if(!cacheImageFilePath.exists()){
            cleanCacheDir(cacheBingPath);
            HttpUtils.downLoadFile(imageFullUrl, cacheImageFilePath);
            try {
                mImageUrl = new URL(FILE_PROTOCOL + cacheImageFilePath.getAbsolutePath());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                mImageUrl = DefaultImageManager.getDefaultUrl();
            }
            updateConfigDataImageUrl(mImageUrl);
        }else{
            LOG.info("图片已存在:" + cacheFileName);
        }
    }

    private void updateConfigDataImageUrl(URL url){
        ConfigState configState = IConfigService.getInstance().getState();
        configState.setRemindImageUrl(url.toString());
        IConfigService.getInstance().updateState(configState);
    }

    private String buildCacheFileName(String url) {
        String md5 = Utils.calMD5(url);
        String suffix = ".png";
        Pattern pattern = getSuffixPattern();
        Matcher matcher = pattern.matcher(url);
        if(matcher.matches()){
            suffix = matcher.group(1);
        }
        return md5.concat(suffix);
    }

    /**
     * 清理缓存图片目录下之前所有的图片文件
     * @param fileDir Bing图片缓存目录
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void cleanCacheDir(File fileDir){
        fileDir.listFiles(new CacheImageCleanerFilter());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    class CacheImageCleanerFilter implements FileFilter{
        @Override
        public boolean accept(File file) {
            file.delete();
            return false;
        }
    }

    private static Pattern getWinPattern() {
		return Pattern.compile("^.+neck_protect_cache\\/bing\\/.+$");
	}

    private static Pattern getUnixPattern() {
		return Pattern.compile("^.+neck_protect_cache\\\\bing\\\\.+$");
	}

    private static Pattern getSuffixPattern() {
		return Pattern.compile("^.*(\\.jpg|\\.jpeg|\\.png).+$");
	}
}