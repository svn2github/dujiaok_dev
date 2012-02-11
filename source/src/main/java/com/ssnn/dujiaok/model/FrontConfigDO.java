package com.ssnn.dujiaok.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 类FrontConfig.java的实现描述：前台展示配置DO
 * 
 * @author ib 2012-2-12 上午04:16:23
 */
public class FrontConfigDO implements Serializable{

    private static final long serialVersionUID = 1224414905213012373L;
    private int    id;
    /**
     * 频道key
     */
    private String channelKey;
    /**
     * 频道名称
     */
    private String channelName;
    /**
     * 模块key
     */
    private String moduleKey;
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 显示数量
     */
    private int    dispalyNum;
    /**
     * 是否有图片
     */
    private String hasPhoto;
    /**
     * 是否有链接
     */
    private String hasLink;
    /**
     * 是否有类目
     */
    private String hasCategory;
    /**
     * 是否有标题
     */
    private String hasTitle;
    /**
     * 是否有详情
     */
    private String hasDetail;
    /**
     * 是否有现价
     */
    private String hasPresentPrice;
    /**
     * 是否有原价
     */
    private String hasOriginalPrice;
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
    }

    public String getHasLink() {
        return hasLink;
    }

    public void setHasLink(String hasLink) {
        this.hasLink = hasLink;
    }

    public String getHasCategory() {
        return hasCategory;
    }

    public void setHasCategory(String hasCategory) {
        this.hasCategory = hasCategory;
    }

    public String getHasTitle() {
        return hasTitle;
    }

    public void setHasTitle(String hasTitle) {
        this.hasTitle = hasTitle;
    }

    public String getHasDetail() {
        return hasDetail;
    }

    public void setHasDetail(String hasDetail) {
        this.hasDetail = hasDetail;
    }

    public String getHasPresentPrice() {
        return hasPresentPrice;
    }

    public void setHasPresentPrice(String hasPresentPrice) {
        this.hasPresentPrice = hasPresentPrice;
    }

    public String getHasOriginalPrice() {
        return hasOriginalPrice;
    }

    public void setHasOriginalPrice(String hasOriginalPrice) {
        this.hasOriginalPrice = hasOriginalPrice;
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
