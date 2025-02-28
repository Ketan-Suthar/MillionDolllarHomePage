package com.mdhp.utils;

public class CanvasUtils {
    /*
    i have 4 point (x1, y1), (x2,y2) and (x3, y3), (x4,y4) where point 1 and 2 represents top left and botton right point of
    selected area, same 3 and 4 represent another selected area. assume top most and left most is (0,0). check wethere given to
    selected area intersects or not
     */
    public static boolean doRectanglesIntersect(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        // Check if one rectangle is to the left or to the right of the other
        if (x2 < x3 || x4 < x1) {
            return false; // No intersection if one rectangle is to the left of the other
        }

        // Check if one rectangle is above or below the other
        if (y2 < y3 || y4 < y1) {
            return false; // No intersection if one rectangle is above the other
        }

        // If neither of the above conditions is true, the rectangles intersect
        return true;
    }
}
