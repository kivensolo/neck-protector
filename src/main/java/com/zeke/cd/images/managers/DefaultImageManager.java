package com.zeke.cd.images.managers;

import javax.annotation.Nonnull;
import java.net.URL;

public class DefaultImageManager extends BaseImageManager {
    private static DefaultImageManager instance;

    private DefaultImageManager() { super(); }

    public static DefaultImageManager getInstance() {
        return instance != null ? instance : (instance = new DefaultImageManager());
    }

    @Nonnull
    @Override
    public URL getImageUrl() {
        mImageUrl = getDefaultUrl();
        return mImageUrl;
    }
}
