package com.ssnn.dujiaok.web.velocity.toolbox;

import com.ssnn.dujiaok.constant.CommonChannelConstants;
import com.ssnn.dujiaok.constant.IndexChannelConstants;
import com.ssnn.dujiaok.constant.SlefDriveChannelConstants;

/**
 * @author ib 2012-2-22 上午01:17:09
 */
public class FrontConstants {

    public IndexChannelConstants     indexChannel     = new IndexChannelConstants();
    public CommonChannelConstants    commonChannel    = new CommonChannelConstants();
    public SlefDriveChannelConstants selfDriveChannel = new SlefDriveChannelConstants();

    public CommonChannelConstants getCommonChannel() {
        return commonChannel;
    }

    public void setCommonChannel(CommonChannelConstants commonChannel) {
        this.commonChannel = commonChannel;
    }

    public IndexChannelConstants getIndexChannel() {
        return indexChannel;
    }

    public void setIndexChannel(IndexChannelConstants indexChannel) {
        this.indexChannel = indexChannel;
    }

    public SlefDriveChannelConstants getSelfDriveChannel() {
        return selfDriveChannel;
    }

    public void setSelfDriveChannel(SlefDriveChannelConstants selfDriveChannel) {
        this.selfDriveChannel = selfDriveChannel;
    }

}
