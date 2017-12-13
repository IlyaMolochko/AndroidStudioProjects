package com.example.ilya.rssreader;

/**
 * Created by ilya on 1.12.17.
 */

public class RSSItem {

    private String mTitle;
    private String mLink;
    private String mDescription;
    private String mPubdate;
    private String mGuid;

    public RSSItem() {

    }

    public RSSItem(String title, String link, String description, String pubdate, String guid) {
        this.mTitle = title;
        this.mLink = link;
        this.mDescription = description;
        this.mPubdate = pubdate;
        this.mGuid = guid;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setLink(String link) {
        this.mLink = link;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public void setPubdate(String pubDate) {
        this.mPubdate = pubDate;
    }


    public void setGuid(String guid) {
        this.mGuid = guid;
    }


    public String getTitle() {
        return this.mTitle;
    }

    public String getLink() {
        return this.mLink;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getPubdate() {
        return this.mPubdate;
    }

    public String getGuid() {
        return this.mGuid;
    }
}