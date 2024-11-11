package com.zeke.cd.images.managers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class CustomImageManager extends BaseImageManager {
    private static CustomImageManager instance;

    private CustomImageManager() { super(); }

    public static CustomImageManager getInstance() {
        return instance != null ? instance : (instance = new CustomImageManager());
    }

    @Override
    public URL getImageUrl() {
        if(null == mImageUrl){
            mImageUrl = getDefaultUrl();
        }
        return mImageUrl;
    }

    /**
     * 设置自定义图片的图片地址
     * @param url 图片绝对地址
     */
    public void setImageUrl(String url){
        try {
            File file = new File(url);
            mImageUrl = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
