package com.jeasy.scheduler.util;

import com.jeasy.common.date.DateKit;
import com.jeasy.common.str.StrKit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.regex.Pattern;


/**
 * 调度处理工具类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
@Data
public final class ScheduleUtil {

    public static final String OWN_SIGN_BASE = "BASE";

    public static final String LOCALHOST = "127.0.0.1";

    public static final String ANYHOST = "0.0.0.0";

    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    private ScheduleUtil() {
    }

    public static String getLocalHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return StrKit.S_EMPTY;
        }
    }

    public static int getFreeSocketPort() {
        try {
            ServerSocket ss = new ServerSocket(0);
            int freePort = ss.getLocalPort();
            ss.close();
            return freePort;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String getLocalIP() {
        try {
            // 本地IP，如果没有配置外网IP则返回它
            String localip = null;
            // 外网IP
            String netip = null;

            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            // 是否找到外网IP
            boolean finded = false;
            while (netInterfaces.hasMoreElements() && !finded) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
                    if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                        && !ip.getHostAddress().contains(StrKit.S_COLON)) {
                        // 外网IP
                        netip = ip.getHostAddress();
                        finded = true;
                        break;
                    } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                        && !ip.getHostAddress().contains(StrKit.S_COLON)) {
                        // 内网IP
                        localip = ip.getHostAddress();
                    }
                }
            }

            if (StrKit.isNotBlank(netip)) {
                return netip;
            } else {
                return localip;
            }
        } catch (Throwable e) {
            return StrKit.S_EMPTY;
        }
    }

    public static String transferDataToString(final Date d) {
        return DateKit.format(d, DateKit.NORM_DATETIME_PATTERN);
    }

    public static Date transferStringToDate(final String d) throws ParseException {
        return DateKit.parse(d, DateKit.NORM_DATETIME_PATTERN);
    }

    public static Date transferStringToDate(final String d, final String formate) throws ParseException {
        return DateKit.parse(d, formate);
    }

    public static String getTaskTypeByBaseAndOwnSign(final String baseType, final String ownSign) {
        if (ownSign.equals(OWN_SIGN_BASE)) {
            return baseType;
        }
        return baseType + StrKit.S_DOLLAR + ownSign;
    }

    public static String splitBaseTaskTypeFromTaskType(final String taskType) {
        if (taskType.contains(StrKit.S_DOLLAR)) {
            return taskType.substring(0, taskType.indexOf(StrKit.S_DOLLAR));
        } else {
            return taskType;
        }
    }

    public static String splitOwnsignFromTaskType(final String taskType) {
        if (taskType.contains(StrKit.S_DOLLAR)) {
            return taskType.substring(taskType.indexOf(StrKit.S_DOLLAR) + 1);
        } else {
            return OWN_SIGN_BASE;
        }
    }

    public static String buildScheduleKey(final String beanName, final String methodName, final String extKeySuffix) {
        String result = beanName + "#" + methodName;
        if (StrKit.isNotBlank(extKeySuffix)) {
            result += "-" + extKeySuffix;
        }
        return result;
    }


    public static String buildScheduleKey(final String beanName, final String methodName) {
        return buildScheduleKey(beanName, methodName, null);
    }

    /**
     * 分配任务数量
     *
     * @param serverNum         总的服务器数量
     * @param taskItemNum       任务项数量
     * @param maxNumOfOneServer 每个server最大任务项数目
     * @return null
     */
    public static int[] assignTaskNumber(final int serverNum, final int taskItemNum, final int maxNumOfOneServer) {
        int[] taskNums = new int[serverNum];
        int numOfSingle = taskItemNum / serverNum;
        int otherNum = taskItemNum % serverNum;
        if (maxNumOfOneServer > 0 && numOfSingle >= maxNumOfOneServer) {
            numOfSingle = maxNumOfOneServer;
            otherNum = 0;
        }
        for (int i = 0; i < taskNums.length; i++) {
            if (i < otherNum) {
                taskNums[i] = numOfSingle + 1;
            } else {
                taskNums[i] = numOfSingle;
            }
        }
        return taskNums;
    }

    private static String printArray(final int[] items) {
        StringBuilder s = new StringBuilder(StrKit.S_EMPTY);
        for (int i = 0; i < items.length; i++) {
            if (i > 0) {
                s.append(StrKit.S_COMMA);
            }
            s.append(items[i]);
        }
        return s.toString();
    }

    private static boolean isValidAddress(final InetAddress address) {
        if (address == null || address.isLoopbackAddress()) {
            return false;
        }

        String name = address.getHostAddress();
        return name != null
            && !ANYHOST.equals(name)
            && !LOCALHOST.equals(name)
            && IP_PATTERN.matcher(name).matches();
    }

    public static void main(final String[] args) {
        System.out.println(printArray(assignTaskNumber(1, 10, 0)));
        System.out.println(printArray(assignTaskNumber(2, 10, 0)));
        System.out.println(printArray(assignTaskNumber(3, 10, 0)));
        System.out.println(printArray(assignTaskNumber(4, 10, 0)));
        System.out.println(printArray(assignTaskNumber(5, 10, 0)));
        System.out.println(printArray(assignTaskNumber(6, 10, 0)));
        System.out.println(printArray(assignTaskNumber(7, 10, 0)));
        System.out.println(printArray(assignTaskNumber(8, 10, 0)));
        System.out.println(printArray(assignTaskNumber(9, 10, 0)));
        System.out.println(printArray(assignTaskNumber(10, 10, 0)));

        System.out.println("-----------------");

        System.out.println(printArray(assignTaskNumber(1, 10, 3)));
        System.out.println(printArray(assignTaskNumber(2, 10, 3)));
        System.out.println(printArray(assignTaskNumber(3, 10, 3)));
        System.out.println(printArray(assignTaskNumber(4, 10, 3)));
        System.out.println(printArray(assignTaskNumber(5, 10, 3)));
        System.out.println(printArray(assignTaskNumber(6, 10, 3)));
        System.out.println(printArray(assignTaskNumber(7, 10, 3)));
        System.out.println(printArray(assignTaskNumber(8, 10, 3)));
        System.out.println(printArray(assignTaskNumber(9, 10, 3)));
        System.out.println(printArray(assignTaskNumber(10, 10, 3)));
    }
}
