package com.example.ilya.rssreader;

/**
 * Created by ilya on 1.12.17.
 */

public class WebSite {
    private Integer mId;
    private String mTitle;
    private String mLink;
    private String mRssLink;
    private String mDescription;

    public WebSite(){

    }

    public WebSite(String title, String link, String rsslink, String description){
        this.mTitle = title;
        this.mLink = link;
        this.mRssLink = rsslink;
        this.mDescription = description;
    }


    public void setId(Integer id){
        this.mId = id;
    }

    public void setTitle(String title){
        this.mTitle = title;
    }

    public void setLink(String link){
        this.mLink = link;
    }

    public void setRSSLink(String rss_link){
        this.mRssLink = rss_link;
    }

    public void setDescription(String description){
        this.mDescription = description;
    }


    public Integer getId(){
        return this.mId;
    }

    public String getTitle(){
        return this.mTitle;
    }

    public String getLink(){
        return this.mLink;
    }

    public String getRSSLink(){
        return this.mRssLink;
    }

    public String getDescription(){
        return this.mDescription;
    }
}