package com.ssnn.dujiaok.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 类FrontViewDO.java的实现描述：前台显示信息DO
 * 
 * @author ib 2012-2-12 上午04:27:17
 */
public class FrontViewDO implements Serializable{

    private static final long serialVersionUID = -2499047129674938343L;
    private int    id;
    /**
     * 信息ID
     */
    private String frontViewId;
    /**
     * 模块key
     */
    private String moduleKey;
    /**
     * 图片
     */
    private String photoURL;
    /**
     * 链接
     */
    private String link;
    /**
     * 类目
     */
    private String gategory;
    /**
     * 标题
     */
    private String title;
    /**
     * 详情
     */
    private String detail;
    /**
     * 现价
     */
    private String presentPrice;
    /**
     * 原价
     */
    private String originalPrice;
    /**
     * 排序关键字
     */
    private long   orderKey;
    /**
     * 创建时间
     */
    private Date   gmtCreate;
    /**
     * 修改时间
     */
    private Date   gmtModified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrontViewId() {
        return frontViewId;
    }

    public void setFrontViewId(String frontViewId) {
        this.frontViewId = frontViewId;
    }

    public String getModuleKey() {
        return moduleKey;
    }

    public void setModuleKey(String moduleKey) {
        this.moduleKey = moduleKey;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGategory() {
        return gategory;
    }

    public void setGategory(String gategory) {
        this.gategory = gategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPresentPrice() {
        return presentPrice;
    }

    public void setPresentPrice(String presentPrice) {
        this.presentPrice = presentPrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public long getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(long orderKey) {
        this.orderKey = orderKey;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

}
