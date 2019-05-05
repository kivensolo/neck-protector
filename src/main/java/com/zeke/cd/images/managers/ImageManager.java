package com.zeke.cd.images.managers;

import java.net.URL;

/**
 *  @author King.Z
 *  @version 1.0
 *  @since 2019-04-29
 */
public interface ImageManager {

    /**
     * 获取即将用于展示的图片
     * 支持 file/jar 协议
     */
    URL getImageUrl();
}
