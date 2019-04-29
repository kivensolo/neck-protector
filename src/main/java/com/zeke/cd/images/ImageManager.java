package com.zeke.cd.images;

import java.net.URL;

/**
 *  @author King.Z
 *  @version 1.0
 *  @since 2019-04-29
 */
public interface ImageManager {

    /**
     * 获取即将用于展示的图片
     * 支持“file” 和 “jar” 及 “http” 协议
     */
    URL getImageUrl();
}
