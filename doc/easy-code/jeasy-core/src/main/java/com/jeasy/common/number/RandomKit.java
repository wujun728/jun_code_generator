package com.jeasy.common.number;

import com.jeasy.common.str.StrKit;

import java.util.*;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class RandomKit {

    private static Random random = new Random();

    /**
     * 用于随机选的数字
     */
    private static final String BASE_NUMBER = "0123456789";
    /**
     * 用于随机选的字符
     */
    private static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";
    /**
     * 用于随机选的字符和数字
     */
    private static final String BASE_CHAR_NUMBER = BASE_CHAR + BASE_NUMBER;

    /**
     * 获取一个随机long类型数
     *
     * @return
     */
    public static long randomLong() {
        Random rand = new Random(System.currentTimeMillis());
        return rand.nextLong();
    }

    /**
     * 得到n位长度的随机数
     *
     * @param n 随机数的长度
     * @return 返回 n位的随机整数
     */
    public static int randomNumberByGivenLength(int n) {
        int temp = 0;
        int min = (int) Math.pow(10, n - 1);
        int max = (int) Math.pow(10, n);
        Random rand = new Random(System.currentTimeMillis());

        while (true) {
            temp = rand.nextInt(max);
            if (temp >= min) {
                break;
            }
        }

        return temp;
    }

    /**
     * 获得指定范围内的随机数
     *
     * @param min 最小数
     * @param max 最大数
     * @return 随机数
     */
    public static int randomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    /**
     * 获得随机数
     *
     * @return 随机数
     */
    public static int randomInt() {
        return random.nextInt();
    }

    /**
     * 获得指定范围内的随机数 [0,limit)
     *
     * @param limit 限制随机数的范围，不包括这个数
     * @return 随机数
     */
    public static int randomInt(int limit) {
        return random.nextInt(limit);
    }

    /**
     * 随机获得列表中的元素
     *
     * @param <T>  元素类型
     * @param list 列表
     * @return 随机元素
     */
    public static <T> T randomEle(List<T> list) {
        return randomEle(list, list.size());
    }

    /**
     * 随机获得列表中的元素
     *
     * @param <T>   元素类型
     * @param list  列表
     * @param limit 限制列表的前N项
     * @return 随机元素
     */
    public static <T> T randomEle(List<T> list, int limit) {
        return list.get(randomInt(limit));
    }

    /**
     * 随机获得列表中的一定量元素
     *
     * @param <T>   元素类型
     * @param list  列表
     * @param count 随机取出的个数
     * @return 随机元素
     */
    public static <T> List<T> randomEles(List<T> list, int count) {
        final List<T> result = new ArrayList<T>(count);
        int limit = list.size();
        while (--count > 0) {
            result.add(randomEle(list, limit));
        }

        return result;
    }

    /**
     * 获得一个随机的字符串（只包含数字和字符）
     *
     * @param length 字符串的长度
     * @return 随机字符串
     */
    public static String randomString(int length) {
        return randomString(BASE_CHAR_NUMBER, length);
    }

    /**
     * 获得一个只包含数字的字符串
     *
     * @param length 字符串的长度
     * @return 随机字符串
     */
    public static String randomNumbers(int length) {
        return randomString(BASE_NUMBER, length);
    }

    /**
     * 获得一个随机的字符串
     *
     * @param baseString 随机字符选取的样本
     * @param length     字符串的长度
     * @return 随机字符串
     */
    public static String randomString(String baseString, int length) {
        StringBuffer sb = new StringBuffer();

        if (length < 1) {
            length = 1;
        }
        int baseLength = baseString.length();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(baseLength);
            sb.append(baseString.charAt(number));
        }
        return sb.toString();
    }

    /**
     * @return 随机UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 随机取size个列表，随机排序
     *
     * @param list
     * @return
     */
    public static <T> T random(final List<T> list) {
        if (list != null) {
            List<T> result = new ArrayList<>(list.size());
            result.addAll(list);
            Collections.shuffle(result);
            if (result.size() >= 1) {
                return result.get(0);
            }
        }
        return null;
    }

    /**
     * 随机取size个列表，随机排序
     *
     * @param list
     * @param size
     * @return
     */
    public static <T> List<T> random(final List<T> list, int size) {
        if (list != null) {
            List<T> result = new ArrayList<>(list.size());
            result.addAll(list);
            Collections.shuffle(result);
            if (result.size() >= size) {
                return result.subList(0, size);
            }
        }
        return Collections.emptyList();
    }

    /**
     * 随机取size个列表，随机排序，当size足够的情况下，排除excludeList里的元素，
     *
     * @param list
     * @param excludeList
     * @param size
     * @return
     */
    public static <T> List<T> random(final List<T> list, final List<T> excludeList, int size) {
        if (list != null) {
            List<T> result = new ArrayList<>(list.size());
            result.addAll(list);
            //如果list总数多余size+excludeList
            if (result.size() >= size + (excludeList != null ? excludeList.size() : 0)) {
                result.removeAll(excludeList);
            }
            Collections.shuffle(result);
            if (result.size() >= size) {
                return result.subList(0, size);
            } else {
                return result;
            }
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 生成num个长度为length的字符串（字符串各不相同）,字符串只包含字母
     *
     * @param length 字符串的长度
     * @param num    字符串的个数
     * @return
     */
    public static String[] random(final int length, final int num) {
        return buildRandom(length, num);
    }


    /**
     * 生成长度为length的字符串,字符串只包含数字
     *
     * @param length 字符串的长度
     * @return
     */
    public static String random(final int length) {
        return buildRandom(length);
    }

    /**
     * 生成num个长度为length的字符串，组成如 123-123-123 格式(只包含数字)
     *
     * @param length
     * @param num
     * @return
     */
    public static String randomBunch(int length, int num) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < num; i++) {
            str.append(random(length));
            if (i != num - 1) {
                str.append("-");
            }
        }
        return str.toString();
    }

    /**
     * 生成num个长度为length的字符串（字符串各不相同）,字符串只包含字母
     *
     * @param length 字符串的长度
     * @param num    字符串的个数
     * @return
     */
    public static String[] buildRandom(final int length, final int num) {
        if (num < 1 || length < 1) {
            return null;
        }
        // 存放临时结果，以避免重复值的发生
        Set<String> tempSet = new HashSet<>(num);

        // 生成num个不相同的字符串
        while (tempSet.size() < num) {
            tempSet.add(buildRandom(length));
        }

        String[] set = new String[num];
        set = tempSet.toArray(set);
        return set;
    }

    /**
     * 返回指定位数的整数
     *
     * @param length
     * @return
     */
    public static int buildIntRandom(final int length) {
        String maxStr = StrKit.padEnd("1", length + 1, '0');
        long max = Long.parseLong(maxStr);
        long i = random.nextLong() % max;
        String result = String.valueOf(i);
        result = StrKit.containsIgnoreCase(result, StrKit.S_MIDDLELINE) ? StrKit.subSuf(result, 1) : result;
        return Integer.parseInt(result);
    }

    /**
     * 取小于指定范围内的整数
     *
     * @param length
     * @return
     */
    public static int buildIntRandomBy(final int length) {
        Random r = new Random();
        return r.nextInt(length);
    }

    /**
     * 生成长度为length的字符串,字符串只包含数字
     *
     * @param length 字符串的长度
     * @return
     */
    public static String buildRandom(final int length) {
        // 长度为length的最多整数
        String maxStr = StrKit.padEnd("1", length + 1, '0');
        long max = Long.parseLong(maxStr);
        // 取正数，并限制其长度
        long i = random.nextLong() % max;

        String result = String.valueOf(i);
        result = StrKit.containsIgnoreCase(result, StrKit.S_MIDDLELINE) ? StrKit.subSuf(result, 1) : result;
        return StrKit.padPre(result, length, '0');
    }
}
