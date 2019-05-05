package com.zeke.cd.images;

import java.util.Arrays;

/**
 * Bing图片Api对应的JavaBean数据结构
 */
public class BingImageBean {
    public Image[] images;
    public ToolTips tooltips;

    public class Image {
        public String startdate = "";
        public String fullstartdate = "";
        public String enddate = "";
        public String url = "";
        public String urlbase = "";
        public String copyright = "";
        public String copyrightlink = "";
        public String title = "";
        public String quiz = "";
        public boolean wp = false;
        public String hsh = "";
        public String drk = "";
        public String top = "";
        public String bot = "";

        @Override
        public String toString() {
            return "Image{" +
                    "startdate='" + startdate + '\'' +
                    ", fullstartdate='" + fullstartdate + '\'' +
                    ", enddate='" + enddate + '\'' +
                    ", url='" + url + '\'' +
                    ", urlbase='" + urlbase + '\'' +
                    ", copyright='" + copyright + '\'' +
                    ", copyrightlink='" + copyrightlink + '\'' +
                    ", title='" + title + '\'' +
                    ", quiz='" + quiz + '\'' +
                    ", hsh='" + hsh + '\'' +
                    ", drk='" + drk + '\'' +
                    ", top='" + top + '\'' +
                    ", bot='" + bot + '\'' +
                    '}';
        }
    }

    public class ToolTips {
        public String loading = "";
        public String previous = "";
        public String next = "";
        public String walle = "";
        public String walls = "";

        @Override
        public String toString() {
            return "ToolTips{" +
                    "loading='" + loading + '\'' +
                    ", previous='" + previous + '\'' +
                    ", next='" + next + '\'' +
                    ", walle='" + walle + '\'' +
                    ", walls='" + walls + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BingImageBean{" +
                "images=" + Arrays.toString(images) +
                ", tooltips=" + tooltips +
                '}';
    }
}
