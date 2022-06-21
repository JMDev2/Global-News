
package com.moringaschool.thenewsapi.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
@Generated("jsonschema2pojo")
public class Datum {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("keywords")
    @Expose
    private String keywords;
    @SerializedName("snippet")
    @Expose
    private String snippet;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("published_at")
    @Expose
    private String publishedAt;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("categories")
    @Expose
    private List<String> categories = null;
    private String pushId;
    String index;
//    @SerializedName("relevance_score")
//    @Expose
//    private Object relevanceScore;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param snippet
     * @param keywords
     * @param relevanceScore
     * @param publishedAt
     * @param imageUrl
     * @param description
     * @param language
     * @param source
     * @param categories
     * @param title
     * @param uuid
     * @param url
     */
    public Datum(String uuid, String title, String description, String keywords, String snippet, String url, String imageUrl, String language, String publishedAt, String source, List<String> categories, Object relevanceScore) {
        super();
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.keywords = keywords;
        this.snippet = snippet;
        this.url = url;
        this.imageUrl = imageUrl;
        this.language = language;
        this.publishedAt = publishedAt;
        this.source = source;
        this.categories = categories;
        this.index = "not_specified";
//        this.relevanceScore = relevanceScore;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

//    public Datum getCategories() {
//        return categories;
//    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

//    public Object getRelevanceScore() {
//        return relevanceScore;
//    }

//    public void setRelevanceScore(Object relevanceScore) {
//        this.relevanceScore = relevanceScore;
//    }

}
