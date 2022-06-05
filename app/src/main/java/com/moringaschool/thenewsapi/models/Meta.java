
package com.moringaschool.thenewsapi.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
@Generated("jsonschema2pojo")
public class Meta {

    @SerializedName("found")
    @Expose
    private Integer found;
    @SerializedName("returned")
    @Expose
    private Integer returned;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("page")
    @Expose
    private Integer page;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Meta() {
    }

    /**
     * 
     * @param found
     * @param limit
     * @param page
     * @param returned
     */
    public Meta(Integer found, Integer returned, Integer limit, Integer page) {
        super();
        this.found = found;
        this.returned = returned;
        this.limit = limit;
        this.page = page;
    }

    public Integer getFound() {
        return found;
    }

    public void setFound(Integer found) {
        this.found = found;
    }

    public Integer getReturned() {
        return returned;
    }

    public void setReturned(Integer returned) {
        this.returned = returned;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

}
