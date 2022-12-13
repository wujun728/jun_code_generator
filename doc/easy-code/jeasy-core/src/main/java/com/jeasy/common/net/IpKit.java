package com.jeasy.common.net;

import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.io.IoKit;
import com.jeasy.common.json.JsonKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.exception.KitException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

/**
 * IP工具类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class IpKit {

    public static final String CITY_NAME = "市";
    public static final String UNKNOWN = "unknown";
    public static final String LOCAL_IP = "127.0.0.1";
    public static final int IP_LENGTH = 15;
    public static final String LOCAL_IP_2 = "0:0:0:0:0:0:0:1";
    /**
     * 查找ip所在城市：新浪
     */
    private static String IP_CITY_URL_FIRST = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=";

    /**
     * 查找ip所在城市：淘宝
     */
    private static String IP_CITY_URL_SECOND = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    public static void main(String[] args) throws Exception {
        String result = IpKit.getCityNameByIP("58.211.8.108", 30000, 30000);
        if (StrKit.isNotBlank(result)) {
            System.out.println(result);
        } else {
            System.out.println("无法识别");
        }
    }

    /**
     * 根据ip获取所属城市名称
     *
     * @param ipString
     * @return
     * @throws Exception
     */
    public static String getCityNameByIP(String ipString, int connectTimeOut, int readTimeOut) throws Exception {
        String cityName = StrKit.S_EMPTY;
        // 新浪
        String temp = IpKit.doGet(IP_CITY_URL_FIRST + ipString, connectTimeOut, readTimeOut);
        if (StrKit.isNotBlank(temp)) {
            AreaDTO areaDTO = JsonKit.fromJson(temp, AreaDTO.class);
            if (areaDTO != null) {
                String city = areaDTO.getCity();
                if (StrKit.isNotBlank(city) && !StrKit.endWith(city, CITY_NAME, true)) {
                    cityName = city + CITY_NAME;
                }
            }
        }
        if (StrKit.isBlank(cityName)) {
            // 淘宝
            temp = IpKit.doGet(IP_CITY_URL_SECOND + ipString, connectTimeOut, readTimeOut);
            if (StrKit.isNotBlank(temp)) {
                AreaResultDTO areaResultDTO = JsonKit.fromJson(temp, AreaResultDTO.class);
                AreaDTO areaDTO = areaResultDTO.getData();
                if (areaDTO != null) {
                    return areaDTO.getCity();
                }
            }
        }
        return cityName;
    }

    /**
     * 淘宝接口返回结果数据
     */
    public static class AreaResultDTO {

        private AreaDTO data;

        /**
         * get data
         *
         * @return
         */
        public AreaDTO getData() {
            return data;
        }

        /**
         * set data
         *
         * @param data
         */
        public void setData(AreaDTO data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "AreaResultDTO{" +
                "data=" + data +
                '}';
        }
    }

    /**
     * 区域dto
     */
    public static class AreaDTO {

        /**
         * 国家
         */
        private String country;
        /**
         * 省
         */
        private String province;
        /**
         * 市
         */
        private String city;

        /**
         * get country
         *
         * @return
         */
        public String getCountry() {
            return country;
        }

        /**
         * set country
         *
         * @param country
         */
        public void setCountry(String country) {
            this.country = country;
        }

        /**
         * get province
         *
         * @return
         */
        public String getProvince() {
            return province;
        }

        /**
         * set province
         *
         * @param province
         */
        public void setProvince(String province) {
            this.province = province;
        }

        /**
         * get city
         *
         * @return
         */
        public String getCity() {
            return city;
        }

        /**
         * set city
         *
         * @param city
         */
        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return "AreaDTO{" +
                "country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
        }
    }

    public static String getRealIP(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Real-IP");
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("x-forwarded-for");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (StrKit.equals(LOCAL_IP, ipAddress) || StrKit.equals(LOCAL_IP_2, ipAddress)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > IP_LENGTH) {
            if (ipAddress.indexOf(StrKit.S_COMMA) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(StrKit.S_COMMA));
            }
        }
        return ipAddress;
    }

    /**
     * do get
     *
     * @param url
     * @return
     * @throws Exception
     */
    private static String doGet(String url, int connectTimeOut, int readTimeOut) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {

            URL realUrl = new URL(url);
            // open connection
            URLConnection connection = realUrl.openConnection();
            connection.setConnectTimeout(connectTimeOut);
            connection.setReadTimeout(readTimeOut);
            // connect
            connection.connect();
            // define BufferedReader to read input content
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), CharsetKit.DEFAULT_CHARSET));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            throw new KitException(e);
        } finally {
            IoKit.close(in);
        }
        return result.toString();
    }
}
