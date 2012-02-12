package com.ssnn.dujiaok.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 类FrontConfig.java的实现描述：前台展示配置DO
 * 
 * @author ib 2012-2-12 上午04:16:23
 */
public class FrontConfigDO implements Serializable {

    private static final long serialVersionUID = 6434283752601044523L;
    public static final String TRUE             = "T";
    public static final String FALSE            = "F";

    private int                id;
    /**
     * 频道key
     */
    private String             channelKey;
    /**
     * 频道名称
     */
    private String             channelName;
    /**
     * 模块key
     */
    private String             moduleKey;
    /**
     * 模块名称
     */
    private String             moduleName;
    /**
     * 显示数量
     */
    private int                dispalyNum;
    /**
     * 是否有图片
     */
    private String             hasPhoto;
    /**
     * 是否有链接
     */
    private String             hasLink;
    /**
     * 是否有类目
     */
    private String             hasCategory;
    /**
     * 是否有标题
     */
    private String             hasTitle;
    /**
     * 是否有详情
     */
    private String             hasDetail;
    /**
     * 是否有现价
     */
    private String             hasPresentPrice;
    /**
     * 是否有原价
     */
    private String             hasOriginalPrice;
    /**
     * 是否有图片
     */
    private boolean            isHasPhoto;
    /**
     * 是否有链接
     */
    private boolean            isHasLink;
    /**
     * 是否有类目
     */
    private boolean            isHasCategory;
    /**
     * 是否有标题
     */
    private boolean            isHasTitle;
    /**
     * 是否有详情
     */
    private boolean            isHasDetail;
    /**
     * 是否有现价
     */
    private boolean            isHasPresentPrice;
    /**
     * 是否有原价
     */
    private boolean            isHasOriginalPrice;
    /**
     * 创建时间
     */
    private Date               gmtCreate;
    /**
     * 修改时间
     */
    private Date               gmtModified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannelKey() {
        return channelKey;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getModuleKey() {
        return moduleKey;
    }

    public void setModuleKey(String moduleKey) {
        this.moduleKey = moduleKey;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getDispalyNum() {
        return dispalyNum;
    }

    public void setDispalyNum(int dispalyNum) {
        this.dispalyNum = dispalyNum;
    }

    public String getHasPhoto() {
        return hasPhoto;
    }

    public void setHasPhoto(String hasPhoto) {
        this.hasPhoto = hasPhoto;
        this.isHasPhoto = StringUtils.equals(hasPhoto, TRUE);
    }

    public String getHasLink() {
        return hasLink;
    }

    public void setHasLink(String hasLink) {
        this.hasLink = hasLink;
        this.isHasLink = StringUtils.equals(hasLink, TRUE);
    }

    public String getHasCategory() {
        return hasCategory;
    }

    public void setHasCategory(String hasCategory) {
        this.hasCategory = hasCategory;
        this.isHasCategory = StringUtils.equals(hasCategory, TRUE);
    }

    public String getHasTitle() {
        return hasTitle;
    }

    public void setHasTitle(String hasTitle) {
        this.hasTitle = hasTitle;
        this.isHasTitle = StringUtils.equals(hasTitle, TRUE);
    }

    public String getHasDetail() {
        return hasDetail;
    }

    public void setHasDetail(String hasDetail) {
        this.hasDetail = hasDetail;
        this.isHasDetail = StringUtils.equals(hasDetail, TRUE);
    }

    public String getHasPresentPrice() {
        return hasPresentPrice;
    }

    public void setHasPresentPrice(String hasPresentPrice) {
        this.hasPresentPrice = hasPresentPrice;
        this.isHasPresentPrice = StringUtils.equals(hasPresentPrice, TRUE);
    }

    public String getHasOriginalPrice() {
        return hasOriginalPrice;
    }

    public void setHasOriginalPrice(String hasOriginalPrice) {
        this.hasOriginalPrice = hasOriginalPrice;
        this.isHasOriginalPrice = StringUtils.equals(hasOriginalPrice, TRUE);
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

    public boolean isHasPhoto() {
        return isHasPhoto;
    }

    public boolean isHasLink() {
        return isHasLink;
    }

    public boolean isHasCategory() {
        return isHasCategory;
    }

    public boolean isHasTitle() {
        return isHasTitle;
    }

    public boolean isHasDetail() {
        return isHasDetail;
    }

    public boolean isHasPresentPrice() {
        return isHasPresentPrice;
    }

    public boolean isHasOriginalPrice() {
        return isHasOriginalPrice;
    }

}
