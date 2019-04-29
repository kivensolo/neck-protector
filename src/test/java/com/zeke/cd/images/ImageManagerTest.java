package com.zeke.cd.images;

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * ImageManagerTest
 */
public class ImageManagerTest extends LightPlatformCodeInsightFixtureTestCase {

    public void testGetInstance() {
        URL url = BaseImageManager.getInstance().getImageUrl();
        assertNotNull(url);
        System.out.println(url.toString());
    }

    public void testPathToUrl() throws MalformedURLException {
        URL imageUrl = BaseImageManager.getInstance().getImageUrl();
        String imagePath = imageUrl.getPath();
        System.out.println(imagePath);
        URL newImageUrl = new URL(imagePath);
        assertEquals(imageUrl, newImageUrl);
    }

    public void testStringToUrl() throws MalformedURLException {
        URL imageUrl = BaseImageManager.getInstance().getImageUrl();
        String imageUrlStr = imageUrl.toString();
        System.out.println(imageUrlStr);
        URL newImageUrl = new URL(imageUrlStr);
        assertEquals(imageUrl, newImageUrl);
    }

}