package com.zeke.cd.images;

import com.google.gson.Gson;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;
import com.zeke.cd.images.managers.DefaultImageManager;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * ImageManagerTest
 */
@SuppressWarnings("ALL")
public class ImageManagerTest extends LightPlatformCodeInsightFixtureTestCase {

    public void testGetDefaultInstance() {
        URL url = DefaultImageManager.getInstance().getImageUrl();
        assertNotNull(url);
        System.out.println(url.toString());
    }

    public void testDefaultPathToUrl() throws MalformedURLException {
        URL imageUrl = DefaultImageManager.getInstance().getImageUrl();
        String imagePath = imageUrl.getPath();
        System.out.println(imagePath);
        URL newImageUrl = new URL(imagePath);
        assertEquals(imageUrl, newImageUrl);
    }

    public void testDefaultStringToUrl() throws MalformedURLException {
        URL imageUrl = DefaultImageManager.getInstance().getImageUrl();
        String imageUrlStr = imageUrl.toString();
        System.out.println(imageUrlStr);
        URL newImageUrl = new URL(imageUrlStr);
        assertEquals(imageUrl, newImageUrl);
    }

    public void testGetBingPicUrl() {
        HttpUtils.sendHttpRequest("http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1",
                //HttpUtils.sendHttpRequest("http://img.redocn.com/sheji/20140628/fangdichanchuangyihaibao_2678001.jpg",
                HttpUtils.GET, new HttpUtils.CallBack() {
                    @Override
                    public void onFinish(String response) {
                        System.out.println(response);
                        try {
                            BingImageBean imageBean = new Gson().fromJson(response, BingImageBean.class);
                            if (imageBean.images.length > 0) {
                                String url = imageBean.images[0].url;
                                System.out.println("Image / 图片 URL = " + url);
                            }else {
                                System.out.println("Abnormal data / 数据异常");
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
}
