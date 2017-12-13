package com.example.ilya.rssreader;

import java.util.List;

/**
 * Created by ilya on 1.12.17.
 */

public class RSSFeed {
    private String mTitle;
    private String mDescription;
    private String mLink;
    private String mRssLink;
    private String mLanguage;
    private List<RSSItem> mItems;

    public RSSFeed(String title, String description, String link,
                   String rss_link, String language) {
        this.mTitle = title;
        this.mDescription = description;
        this.mLink = link;
        this.mRssLink = rss_link;
        this.mLanguage = language;
    }

    public void setItems(List<RSSItem> items) {
        this.mItems = items;
    }


    public List<RSSItem> getItems() {
        return this.mItems;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getLink() {
        return this.mLink;
    }

    public String getRSSLink() {
        return this.mRssLink;
    }

    public String getLanguage() {
        return this.mLanguage;
    }
}