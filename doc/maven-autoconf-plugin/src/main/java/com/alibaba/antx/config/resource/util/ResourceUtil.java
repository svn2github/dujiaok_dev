package com.alibaba.antx.config.resource.util;

import java.net.URI;

public class ResourceUtil {
    public static String getUsername(URI uri) {
        if (uri == null) {
            return null;
        }

        String user = uri.getUserInfo();
        int colon = user == null ? -1 : user.indexOf(":");

        return colon <= 0 ? user : user.substring(0, colon);
    }
}
