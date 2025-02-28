package com.mdhp.pojo;

import lombok.Data;


@Data
public class CanvasPojo {
    private Point p1;
    private Point p2;
    private int days;
    private String hoverText;
    private String image;
    private String url;

    public String getPointsForDb() {
        return p1.getX() + "," + p1.getY() + "," + p2.getX() + "," + p2.getY();
    }
}
