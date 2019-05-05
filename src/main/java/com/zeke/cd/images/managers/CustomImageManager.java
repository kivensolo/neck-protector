package com.zeke.cd.images.managers;

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

    public void setImageUrl(String url){
        try {
            mImageUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
