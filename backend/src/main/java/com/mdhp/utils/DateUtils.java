package com.mdhp.utils;

import com.mdhp.constants.Constants;

import java.time.LocalDateTime;
import java.time.ZoneId;


public class DateUtils {
    public static String getNow() {
        return LocalDateTime.now().atZone(ZoneId.of(Constants.ZONE_ID)).format(Constants.TIME_FORMATTER);
    }
}
