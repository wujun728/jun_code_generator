package com.jeasy.common.number;

/**
 * 距离计算
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public final class DistanceKit {

    private static final double EARTH_RADIUS = 6378137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算距离（单位：米）
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
            Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Double.parseDouble(String.valueOf(Math.round(s * 10000) / 10000));
        return s;
    }

    public static void main(String[] args) {
        System.out.println(getDistance(43.659362, 120.30606, 52.438878, 143.354836));
        System.out.println(DistanceKit.getDistance(39.9136110000, 116.3987640000, 39.9138880000, 116.4073880000));
    }
}
