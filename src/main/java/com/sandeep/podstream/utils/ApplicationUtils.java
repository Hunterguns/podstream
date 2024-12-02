package com.sandeep.podstream.utils;

import com.amazonaws.util.StringUtils;

public class ApplicationUtils {

    public static String generateFileNameWithTimeStamp(String filename, String timestamp) {
        if (StringUtils.isNullOrEmpty(filename) || !filename.contains(".")) {
            return filename + "_" + timestamp;
        }
        int dotIndex = filename.lastIndexOf(".");
        return filename.substring(0, dotIndex) + "_" + timestamp + filename.substring(dotIndex);
    }
}
