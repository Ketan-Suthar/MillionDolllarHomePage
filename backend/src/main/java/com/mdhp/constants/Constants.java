package com.mdhp.constants;


import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Constants {
    public static final short PIXEL_START = 0;
    public static final short PIXEL_END = 99;
    public static final String DT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSSS";
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(Constants.DT_FORMAT, Locale.ENGLISH);
    public static final String ZONE_ID = "Asia/Kolkata";
    public static final String IMAGES_PATH = "src/main/resources/static/images/";

}
