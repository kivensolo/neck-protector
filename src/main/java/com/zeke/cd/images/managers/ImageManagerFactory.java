package com.zeke.cd.images.managers;

import com.zeke.cd.service.ConfigState;

public class ImageManagerFactory {
    public BaseImageManager create(ConfigState.ImageSrcTypeEnum imageType) {
        switch (imageType) {
            case DEFAULT:
                return DefaultImageManager.getInstance();
            case BING:
                return BingImageManager.getInstance();
            case CUSTOM:
                return CustomImageManager.getInstance();
            default:
                throw new IllegalArgumentException("create failed! Current type is undefined!");
        }
    }
}
