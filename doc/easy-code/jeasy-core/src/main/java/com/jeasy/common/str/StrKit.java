package com.jeasy.common.str;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jeasy.common.Func;
import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.collection.CollectionKit;
import com.jeasy.common.security.Md5Kit;
import com.jeasy.common.support.StrFormatter;
import org.apache.commons.lang3.StringUtils;

import java.io.StringReader;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * 字符串工具类
 *
 * @author xiaoleilu
 */
public class StrKit {

    public static final char C_SPACE = ' ';
    public static final char C_TAB = '\t';
    public static final char C_DOT = '.';
    public static final char C_SLASH = '/';
    public static final char C_BACKSLASH = '\\';
    public static final char C_CR = '\r';
    public static final char C_LF = '\n';
    public static final char C_UNDERLINE = '_';
    public static final char C_COMMA = ',';
    public static final char C_DELIM_START = '{';
    public static final char C_DELIM_END = '}';
    public static final char C_PARENTHESES_START = '(';
    public static final char C_PARENTHESES_END = ')';
    public static final char C_DOUBLE_QUOTE = '"';
    public static final char C_FORWARD_QUOTE = '\'';
    public static final char C_BACK_QUOTE = '`';
    public static final char C_POUND = '#';
    public static final char C_COLON = ':';
    public static final char C_DOLLAR = '$';
    public static final char C_MIDDLELINE = '-';
    public static final char C_QUESTION = '?';
    public static final char C_ASTERISK = '*';
    public static final char C_SEMICOLON = ';';
    public static final char C_EQUAL = '=';

    public static final String S_SPACE = " ";
    public static final String S_TAB = "\t";
    public static final String S_DOT = ".";
    public static final String S_SLASH = "/";
    public static final String S_BACKSLASH = "\\";
    public static final String S_EMPTY = "";
    public static final String S_CR = "\r";
    public static final String S_LF = "\n";
    public static final String S_CRLF = "\r\n";
    public static final String S_UNDERLINE = "_";
    public static final String S_COMMA = ",";
    public static final String S_DOUBLE_QUOTE = "\"";
    public static final String S_FORWARD_QUOTE = "'";
    public static final String S_BACK_QUOTE = "`";
    public static final String S_DELIM_START = "{";
    public static final String S_DELIM_END = "}";
    public static final String S_PARENTHESES_START = "(";
    public static final String S_PARENTHESES_END = ")";
    public static final String S_POUND = "#";
    public static final String S_COLON = ":";
    public static final String S_DOLLAR = "$";
    public static final String S_MIDDLELINE = "-";
    public static final String S_QUESTION = "?";
    public static final String S_ASTERISK = "*";
    public static final String S_SEMICOLON = ";";
    public static final String S_SINGLE_AND = "&";
    public static final String S_HTML_NBSP = "&nbsp;";
    public static final String S_HTML_AMP = "&amp";
    public static final String S_HTML_QUOTE = "&quot;";
    public static final String S_HTML_LT = "&lt;";
    public static final String S_HTML_GT = "&gt;";
    public static final String S_EQUAL = "=";

    public static final String S_EMPTY_JSON = "{}";
    public static final String S_HTTP_PREFIX = "http://";
    public static final String S_HTTPS_PREFIX = "https://";
    public static final String S_NULL = "null";
    public static final String S_UNDEFINED = "undefined";
    public static final String S_GET = "get";
    public static final String S_SET = "set";
    public static final int TWO_SIZE = 2;
    public static final int SIXTEEN = 16;

    // ------------------------------------------------------------------------ Blank

    /**
     * 字符串是否为空白 空白的定义如下： <br>
     * 1、为null <br>
     * 2、为不可见字符（如空格）<br>
     * 3、StrKit.S_EMPTY<br>
     *
     * @param str 被检测的字符串
     * @return 是否为空
     */
    public static boolean isBlank(String str) {
        int length;

        if ((str == null) || ((length = str.length()) == 0)) {
            return true;
        }

        for (int i = 0; i < length; i++) {
            // 只要有一个非空字符即为非空字符串
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串是否为非空白 空白的定义如下： <br>
     * 1、不为null <br>
     * 2、不为不可见字符（如空格）<br>
     * 3、不为StrKit.S_EMPTY<br>
     *
     * @param str 被检测的字符串
     * @return 是否为非空
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 是否包含空字符串
     *
     * @param strs 字符串列表
     * @return 是否包含空字符串
     */
    public static boolean hasBlank(String... strs) {
        if (CollectionKit.isEmpty(strs)) {
            return true;
        }

        for (String str : strs) {
            if (isBlank(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 给定所有字符串是否为空白
     *
     * @param strs 字符串
     * @return 所有字符串是否为空白
     */
    public static boolean isAllBlank(String... strs) {
        if (CollectionKit.isEmpty(strs)) {
            return true;
        }

        for (String str : strs) {
            if (isNotBlank(str)) {
                return false;
            }
        }
        return true;
    }

    // ------------------------------------------------------------------------ Empty

    /**
     * 字符串是否为空，空的定义如下 1、为null <br>
     * 2、为StrKit.S_EMPTY<br>
     *
     * @param str 被检测的字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 字符串是否为非空白 空白的定义如下： <br>
     * 1、不为null <br>
     * 2、不为StrKit.S_EMPTY<br>
     *
     * @param str 被检测的字符串
     * @return 是否为非空
     */
    public static boolean isNotEmpty(String str) {
        return false == isEmpty(str);
    }

    /**
     * 当给定字符串为null时，转换为Empty
     *
     * @param str 被转换的字符串
     * @return 转换后的字符串
     */
    public static String nullToEmpty(String str) {
        return nullToDefault(str, S_EMPTY);
    }

    /**
     * 如果字符串是<code>null</code>，则返回指定默认字符串，否则返回字符串本身。
     * <p/>
     * <pre>
     * nullToDefault(null, &quot;default&quot;)  = &quot;default&quot;
     * nullToDefault(&quot;&quot;, &quot;default&quot;)    = &quot;&quot;
     * nullToDefault(&quot;  &quot;, &quot;default&quot;)  = &quot;  &quot;
     * nullToDefault(&quot;bat&quot;, &quot;default&quot;) = &quot;bat&quot;
     * </pre>
     *
     * @param str        要转换的字符串
     * @param defaultStr 默认字符串
     * @return 字符串本身或指定的默认字符串
     */
    public static String nullToDefault(String str, String defaultStr) {
        return (str == null) ? defaultStr : str;
    }

    /**
     * 当给定字符串为空字符串时，转换为<code>null</code>
     *
     * @param str 被转换的字符串
     * @return 转换后的字符串
     */
    public static String emptyToNull(String str) {
        return isEmpty(str) ? null : str;
    }

    /**
     * 是否包含空字符串
     *
     * @param strs 字符串列表
     * @return 是否包含空字符串
     */
    public static boolean hasEmpty(String... strs) {
        if (CollectionKit.isEmpty(strs)) {
            return true;
        }

        for (String str : strs) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否全部为空字符串
     *
     * @param strs 字符串列表
     * @return 是否全部为空字符串
     */
    public static boolean isAllEmpty(String... strs) {
        if (CollectionKit.isEmpty(strs)) {
            return true;
        }

        for (String str : strs) {
            if (isNotEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    // ------------------------------------------------------------------------ Trim

    /**
     * 除去字符串头尾部的空白，如果字符串是<code>null</code>，依然返回<code>null</code>。
     * <p/>
     * <p>
     * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
     * <p/>
     * <pre>
     * trim(null)          = null
     * trim(&quot;&quot;)            = &quot;&quot;
     * trim(&quot;     &quot;)       = &quot;&quot;
     * trim(&quot;abc&quot;)         = &quot;abc&quot;
     * trim(&quot;    abc    &quot;) = &quot;abc&quot;
     * </pre>
     * <p/>
     * </p>
     *
     * @param str 要处理的字符串
     * @return 除去空白的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
     */
    public static String trim(String str) {
        return (null == str) ? null : trim(str, 0);
    }

    /**
     * 给定字符串数组全部做去首尾空格
     *
     * @param strs 字符串数组
     */
    public static void trim(String[] strs) {
        if (null == strs) {
            return;
        }
        String str;
        for (int i = 0; i < strs.length; i++) {
            str = strs[i];
            if (null != str) {
                strs[i] = str.trim();
            }
        }
    }

    /**
     * 除去字符串头部的空白，如果字符串是<code>null</code>，则返回<code>null</code>。
     * <p/>
     * <p>
     * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
     * <p/>
     * <pre>
     * trimStart(null)         = null
     * trimStart(&quot;&quot;)           = &quot;&quot;
     * trimStart(&quot;abc&quot;)        = &quot;abc&quot;
     * trimStart(&quot;  abc&quot;)      = &quot;abc&quot;
     * trimStart(&quot;abc  &quot;)      = &quot;abc  &quot;
     * trimStart(&quot; abc &quot;)      = &quot;abc &quot;
     * </pre>
     * <p/>
     * </p>
     *
     * @param str 要处理的字符串
     * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>StrKit.S_EMPTY</code>，则返回 <code>null</code>
     */
    public static String trimStart(String str) {
        return trim(str, -1);
    }

    /**
     * 除去字符串尾部的空白，如果字符串是<code>null</code>，则返回<code>null</code>。
     * <p/>
     * <p>
     * 注意，和<code>String.trim</code>不同，此方法使用<code>Character.isWhitespace</code> 来判定空白， 因而可以除去英文字符集之外的其它空白，如中文空格。
     * <p/>
     * <pre>
     * trimEnd(null)       = null
     * trimEnd(&quot;&quot;)         = &quot;&quot;
     * trimEnd(&quot;abc&quot;)      = &quot;abc&quot;
     * trimEnd(&quot;  abc&quot;)    = &quot;  abc&quot;
     * trimEnd(&quot;abc  &quot;)    = &quot;abc&quot;
     * trimEnd(&quot; abc &quot;)    = &quot; abc&quot;
     * </pre>
     * <p/>
     * </p>
     *
     * @param str 要处理的字符串
     * @return 除去空白的字符串，如果原字串为<code>null</code>或结果字符串为<code>StrKit.S_EMPTY</code>，则返回 <code>null</code>
     */
    public static String trimEnd(String str) {
        return trim(str, 1);
    }

    /**
     * 除去字符串头尾部的空白符，如果字符串是<code>null</code>，依然返回<code>null</code>。
     *
     * @param str  要处理的字符串
     * @param mode <code>-1</code>表示trimStart，<code>0</code>表示trim全部， <code>1</code>表示trimEnd
     * @return 除去指定字符后的的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
     */
    public static String trim(String str, int mode) {
        if (str == null) {
            return null;
        }

        int length = str.length();
        int start = 0;
        int end = length;

        // 扫描字符串头部
        if (mode <= 0) {
            while ((start < end) && (Character.isWhitespace(str.charAt(start)))) {
                start++;
            }
        }

        // 扫描字符串尾部
        if (mode >= 0) {
            while ((start < end) && (Character.isWhitespace(str.charAt(end - 1)))) {
                end--;
            }
        }

        if ((start > 0) || (end < length)) {
            return str.substring(start, end);
        }

        return str;
    }

    /**
     * 是否以指定字符串开头
     *
     * @param str          被监测字符串
     * @param prefix       开头字符串
     * @param isIgnoreCase 是否忽略大小写
     * @return 是否以指定字符串开头
     */
    public static boolean startWith(String str, String prefix, boolean isIgnoreCase) {
        if (isIgnoreCase) {
            return str.toLowerCase().startsWith(prefix.toLowerCase());
        } else {
            return str.startsWith(prefix);
        }
    }

    /**
     * 是否以指定字符串结尾
     *
     * @param str          被监测字符串
     * @param suffix       结尾字符串
     * @param isIgnoreCase 是否忽略大小写
     * @return 是否以指定字符串结尾
     */
    public static boolean endWith(String str, String suffix, boolean isIgnoreCase) {
        if (isIgnoreCase) {
            return str.toLowerCase().endsWith(suffix.toLowerCase());
        } else {
            return str.endsWith(suffix);
        }
    }

    /**
     * 是否包含特定字符，忽略大小写，如果给定两个参数都为<code>null</code>，返回true
     *
     * @param str     被检测字符串
     * @param testStr 被测试是否包含的字符串
     * @return 是否包含
     */
    public static boolean containsIgnoreCase(String str, String testStr) {
        if (null == str) {
            //如果被监测字符串和
            return null == testStr;
        }
        return str.toLowerCase().contains(testStr.toLowerCase());
    }

    /**
     * 获得set或get方法对应的标准属性名<br/>
     * 例如：setName 返回 name
     *
     * @param getOrSetMethodName
     * @return 如果是set或get方法名，返回field， 否则null
     */
    public static String getGeneralField(String getOrSetMethodName) {
        if (getOrSetMethodName.startsWith(S_GET) || getOrSetMethodName.startsWith(S_SET)) {
            return removePreAndLowerFirst(getOrSetMethodName, 3);
        }
        return null;
    }

    /**
     * 生成set方法名<br/>
     * 例如：name 返回 setName
     *
     * @param fieldName 属性名
     * @return setXxx
     */
    public static String genSetter(String fieldName) {
        return upperFirstAndAddPre(fieldName, S_SET);
    }

    /**
     * 生成get方法名
     *
     * @param fieldName 属性名
     * @return getXxx
     */
    public static String genGetter(String fieldName) {
        return upperFirstAndAddPre(fieldName, S_GET);
    }

    /**
     * 去掉首部指定长度的字符串并将剩余字符串首字母小写<br/>
     * 例如：str=setName, preLength=3 -> return name
     *
     * @param str       被处理的字符串
     * @param preLength 去掉的长度
     * @return 处理后的字符串，不符合规范返回null
     */
    public static String removePreAndLowerFirst(String str, int preLength) {
        if (str == null) {
            return null;
        }
        if (str.length() > preLength) {
            char first = Character.toLowerCase(str.charAt(preLength));
            if (str.length() > preLength + 1) {
                return first + str.substring(preLength + 1);
            }
            return String.valueOf(first);
        } else {
            return str;
        }
    }

    /**
     * 去掉首部指定长度的字符串并将剩余字符串首字母小写<br/>
     * 例如：str=setName, prefix=set -> return name
     *
     * @param str    被处理的字符串
     * @param prefix 前缀
     * @return 处理后的字符串，不符合规范返回null
     */
    public static String removePreAndLowerFirst(String str, String prefix) {
        return lowerFirst(removePrefix(str, prefix));
    }

    /**
     * 原字符串首字母大写并在其首部添加指定字符串 例如：str=name, preString=get -> return getName
     *
     * @param str       被处理的字符串
     * @param preString 添加的首部
     * @return 处理后的字符串
     */
    public static String upperFirstAndAddPre(String str, String preString) {
        if (str == null || preString == null) {
            return null;
        }
        return preString + upperFirst(str);
    }

    /**
     * 大写首字母<br>
     * 例如：str = name, return Name
     *
     * @param str 字符串
     * @return 字符串
     */
    public static String upperFirst(String str) {
        if (StrKit.isBlank(str)) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + subSuf(str, 1);
    }

    /**
     * 小写首字母<br>
     * 例如：str = Name, return name
     *
     * @param str 字符串
     * @return 字符串
     */
    public static String lowerFirst(String str) {
        if (isBlank(str)) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + subSuf(str, 1);
    }

    /**
     * 去掉指定前缀
     *
     * @param str    字符串
     * @param prefix 前缀
     * @return 切掉后的字符串，若前缀不是 preffix， 返回原字符串
     */
    public static String removePrefix(String str, String prefix) {
        if (isEmpty(str) || isEmpty(prefix)) {
            return str;
        }

        if (str.startsWith(prefix)) {
            // 截取后半段
            return subSuf(str, prefix.length());
        }
        return str;
    }

    /**
     * 忽略大小写去掉指定前缀
     *
     * @param str    字符串
     * @param prefix 前缀
     * @return 切掉后的字符串，若前缀不是 prefix， 返回原字符串
     */
    public static String removePrefixIgnoreCase(String str, String prefix) {
        if (isEmpty(str) || isEmpty(prefix)) {
            return str;
        }

        if (str.toLowerCase().startsWith(prefix.toLowerCase())) {
            // 截取后半段
            return subSuf(str, prefix.length());
        }
        return str;
    }

    /**
     * 去掉指定后缀
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     */
    public static String removeSuffix(String str, String suffix) {
        if (isEmpty(str) || isEmpty(suffix)) {
            return str;
        }

        if (str.endsWith(suffix)) {
            // 截取前半段
            return subPre(str, str.length() - suffix.length());
        }
        return str;
    }

    /**
     * 去掉指定后缀，并小写首字母
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     */
    public static String removeSufAndLowerFirst(String str, String suffix) {
        return lowerFirst(removeSuffix(str, suffix));
    }

    /**
     * 忽略大小写去掉指定后缀
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     */
    public static String removeSuffixIgnoreCase(String str, String suffix) {
        if (isEmpty(str) || isEmpty(suffix)) {
            return str;
        }

        if (str.toLowerCase().endsWith(suffix.toLowerCase())) {
            return subPre(str, str.length() - suffix.length());
        }
        return str;
    }

    /**
     * 如果给定字符串不是以prefix开头的，在开头补充 prefix
     *
     * @param str    字符串
     * @param prefix 前缀
     * @return 补充后的字符串
     */
    public static String addPrefixIfNot(String str, String prefix) {
        if (isEmpty(str) || isEmpty(prefix)) {
            return str;
        }
        if (false == str.startsWith(prefix)) {
            str = prefix + str;
        }
        return str;
    }

    /**
     * 如果给定字符串不是以suffix结尾的，在尾部补充 suffix
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 补充后的字符串
     */
    public static String addSuffixIfNot(String str, String suffix) {
        if (isEmpty(str) || isEmpty(suffix)) {
            return str;
        }
        if (false == str.endsWith(suffix)) {
            str += suffix;
        }
        return str;
    }

    /**
     * 清理空白字符
     *
     * @param str 被清理的字符串
     * @return 清理后的字符串
     */
    public static String cleanBlank(String str) {
        if (str == null) {
            return null;
        }

        return str.replaceAll("\\s*", S_EMPTY);
    }

    /**
     * 切分字符串<br>
     * a#b#c -> [a,b,c] <br>
     * a##b#c -> [a,StrKit.S_EMPTY,b,c]
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @return 切分后的集合
     */
    public static List<String> split(String str, char separator) {
        return split(str, separator, 0);
    }

    /**
     * 切分字符串
     *
     * @param str       被切分的字符串
     * @param separator 分隔符字符
     * @param limit     限制分片数
     * @return 切分后的集合
     */
    public static List<String> split(String str, char separator, int limit) {
        if (str == null) {
            return null;
        }
        List<String> list = new ArrayList<String>(limit == 0 ? SIXTEEN : limit);
        if (limit == 1) {
            list.add(str);
            return list;
        }

        // 未结束切分的标志
        boolean isNotEnd = true;
        int strLen = str.length();
        StringBuilder sb = new StringBuilder(strLen);
        for (int i = 0; i < strLen; i++) {
            char c = str.charAt(i);
            if (isNotEnd && c == separator) {
                list.add(sb.toString());
                // 清空StringBuilder
                sb.delete(0, sb.length());

                // 当达到切分上限-1的量时，将所剩字符全部作为最后一个串
                if (limit != 0 && list.size() == limit - 1) {
                    isNotEnd = false;
                }
            } else {
                sb.append(c);
            }
        }
        // 加入尾串
        list.add(sb.toString());
        return list;
    }

    /**
     * 切分字符串<br>
     * from jodd
     *
     * @param str       被切分的字符串
     * @param delimiter 分隔符
     * @return 字符串
     */
    public static String[] split(String str, String delimiter) {
        if (str == null) {
            return null;
        }
        if (str.trim().length() == 0) {
            return new String[]{str};
        }

        // del length
        int dellen = delimiter.length();
        // one more for the last
        int maxparts = (str.length() / dellen) + TWO_SIZE;
        int[] positions = new int[maxparts];

        int i, j = 0;
        int count = 0;
        positions[0] = -dellen;
        while ((i = str.indexOf(delimiter, j)) != -1) {
            count++;
            positions[count] = i;
            j = i + dellen;
        }
        count++;
        positions[count] = str.length();

        String[] result = new String[count];

        for (i = 0; i < count; i++) {
            result[i] = str.substring(positions[i] + dellen, positions[i + 1]);
        }
        return result;
    }

    /**
     * 改进JDK subString<br>
     * index从0开始计算，最后一个字符为-1<br>
     * 如果from和to位置一样，返回 StrKit.S_EMPTY <br>
     * 如果from或to为负数，则按照length从后向前数位置，如果绝对值大于字符串长度，则from归到0，to归到length<br>
     * 如果经过修正的index中from大于to，则互换from和to
     * example: <br>
     * abcdefgh 2 3 -> c <br>
     * abcdefgh 2 -3 -> cde <br>
     *
     * @param string    String
     * @param fromIndex 开始的index（包括）
     * @param toIndex   结束的index（不包括）
     * @return 字串
     */
    public static String sub(String string, int fromIndex, int toIndex) {
        int len = string.length();

        if (fromIndex < 0) {
            fromIndex = len + fromIndex;
            if (fromIndex < 0) {
                fromIndex = 0;
            }
        } else if (fromIndex > len) {
            fromIndex = len;
        }

        if (toIndex < 0) {
            toIndex = len + toIndex;
            if (toIndex < 0) {
                toIndex = len;
            }
        } else if (toIndex > len) {
            toIndex = len;
        }

        if (toIndex < fromIndex) {
            int tmp = fromIndex;
            fromIndex = toIndex;
            toIndex = tmp;
        }

        if (fromIndex == toIndex) {
            return S_EMPTY;
        }

        char[] strArray = string.toCharArray();
        char[] newStrArray = Arrays.copyOfRange(strArray, fromIndex, toIndex);
        return new String(newStrArray);
    }

    /**
     * 切割前部分
     *
     * @param string  字符串
     * @param toIndex 切割到的位置（不包括）
     * @return 切割后的字符串
     */
    public static String subPre(String string, int toIndex) {
        return sub(string, 0, toIndex);
    }

    /**
     * 切割后部分
     *
     * @param string    字符串
     * @param fromIndex 切割开始的位置（包括）
     * @return 切割后的字符串
     */
    public static String subSuf(String string, int fromIndex) {
        if (isEmpty(string)) {
            return null;
        }
        return sub(string, fromIndex, string.length());
    }

    /**
     * 给定字符串是否被字符包围
     *
     * @param str    字符串
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 是否包围，空串不包围
     */
    public static boolean isSurround(String str, String prefix, String suffix) {
        if (StrKit.isBlank(str)) {
            return false;
        }
        if (str.length() < (prefix.length() + suffix.length())) {
            return false;
        }

        return str.startsWith(prefix) && str.endsWith(suffix);
    }

    /**
     * 给定字符串是否被字符包围
     *
     * @param str    字符串
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 是否包围，空串不包围
     */
    public static boolean isSurround(String str, char prefix, char suffix) {
        if (StrKit.isBlank(str)) {
            return false;
        }
        if (str.length() < TWO_SIZE) {
            return false;
        }

        return str.charAt(0) == prefix && str.charAt(str.length() - 1) == suffix;
    }

    /**
     * 重复某个字符
     *
     * @param c     被重复的字符
     * @param count 重复的数目
     * @return 重复字符字符串
     */
    public static String repeat(char c, int count) {
        char[] result = new char[count];
        for (int i = 0; i < count; i++) {
            result[i] = c;
        }
        return new String(result);
    }

    /**
     * 重复某个字符串
     *
     * @param str   被重复的字符
     * @param count 重复的数目
     * @return 重复字符字符串
     */
    public static String repeat(String str, int count) {

        // 检查
        final int len = str.length();
        final long longSize = (long) len * (long) count;
        final int size = (int) longSize;
        if (size != longSize) {
            throw new ArrayIndexOutOfBoundsException("Required String length is too large: " + longSize);
        }

        final char[] array = new char[size];
        str.getChars(0, len, array, 0);
        int n;
        // n <<= 1相当于n *2
        for (n = len; n < size - n; n <<= 1) {
            System.arraycopy(array, 0, array, n, n);
        }
        System.arraycopy(array, 0, array, n, size - n);
        return new String(array);
    }

    /**
     * 比较两个字符串（大小写敏感）。
     * <p/>
     * <pre>
     * equals(null, null)   = true
     * equals(null, &quot;abc&quot;)  = false
     * equals(&quot;abc&quot;, null)  = false
     * equals(&quot;abc&quot;, &quot;abc&quot;) = true
     * equals(&quot;abc&quot;, &quot;ABC&quot;) = false
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equals(str2);
    }

    /**
     * 比较两个字符串（大小写不敏感）。
     * <p/>
     * <pre>
     * equalsIgnoreCase(null, null)   = true
     * equalsIgnoreCase(null, &quot;abc&quot;)  = false
     * equalsIgnoreCase(&quot;abc&quot;, null)  = false
     * equalsIgnoreCase(&quot;abc&quot;, &quot;abc&quot;) = true
     * equalsIgnoreCase(&quot;abc&quot;, &quot;ABC&quot;) = true
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equalsIgnoreCase(str2);
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * 转义{}：     format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * 转义\：        format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... params) {
        if (CollectionKit.isEmpty(params) || isBlank(template)) {
            return template;
        }
        return StrFormatter.format(template, params);
    }

    /**
     * 格式化文本，使用 {varName} 占位<br>
     * map = {a: "aValue", b: "bValue"}
     * format("{a} and {b}", map)    ---->    aValue and bValue
     *
     * @param template 文本模板，被替换的部分用 {key} 表示
     * @param map      参数值对
     * @return 格式化后的文本
     */
    public static String format(String template, Map<?, ?> map) {
        if (null == map || map.isEmpty()) {
            return template;
        }

        for (Entry<?, ?> entry : map.entrySet()) {
            template = template.replace("{" + entry.getKey() + "}", utf8Str(entry.getValue()));
        }
        return template;
    }

    /**
     * 编码字符串
     *
     * @param str     字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 编码后的字节码
     */
    public static byte[] bytes(String str, String charset) {
        return bytes(str, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * 编码字符串
     *
     * @param str     字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 编码后的字节码
     */
    public static byte[] bytes(String str, Charset charset) {
        if (Func.isEmpty(str)) {
            return null;
        }

        if (Func.isEmpty(charset)) {
            return str.getBytes(CharsetKit.DEFAULT_CHARSET);
        }
        return str.getBytes(charset);
    }

    /**
     * 将对象转为字符串<br>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 2、对象数组会调用Arrays.toString方法
     *
     * @param obj 对象
     * @return 字符串
     */
    public static String utf8Str(Object obj) {
        return str(obj, CharsetKit.CHARSET_UTF_8);
    }

    /**
     * 将对象转为字符串<br>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 2、对象数组会调用Arrays.toString方法
     *
     * @param obj         对象
     * @param charsetName 字符集
     * @return 字符串
     */
    public static String str(Object obj, String charsetName) {
        return str(obj, Charset.forName(charsetName));
    }

    /**
     * 将对象转为字符串<br>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 2、对象数组会调用Arrays.toString方法
     *
     * @param obj     对象
     * @param charset 字符集
     * @return 字符串
     */
    public static String str(Object obj, Charset charset) {
        if (Func.isEmpty(obj)) {
            return StrKit.S_EMPTY;
        }

        if (obj instanceof String) {
            return (String) obj;
        }
        // else if (obj instanceof byte[] || obj instanceof Byte[]) {
        //    return str((Byte[]) obj, charset);
        // } else if (obj instanceof ByteBuffer) {
        //    return str((ByteBuffer) obj, charset);
        // }
        else if (CollectionKit.isArray(obj)) {
            return CollectionKit.toString(obj);
        }

        return obj.toString();
    }

    /**
     * 将byte数组转为字符串
     *
     * @param bytes   byte数组
     * @param charset 字符集
     * @return 字符串
     */
    public static String str(byte[] bytes, String charset) {
        return str(bytes, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * 解码字节码
     *
     * @param data    字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 解码后的字符串
     */
    public static String str(byte[] data, Charset charset) {
        if (Func.isEmpty(data)) {
            return null;
        }

        if (Func.isEmpty(charset)) {
            return new String(data, CharsetKit.DEFAULT_CHARSET);
        }
        return new String(data, charset);
    }

    /**
     * 将编码的byteBuffer数据转换为字符串
     *
     * @param data    数据
     * @param charset 字符集，如果为空使用当前系统字符集
     * @return 字符串
     */
    public static String str(ByteBuffer data, String charset) {
        if (Func.isEmpty(data)) {
            return null;
        }

        return str(data, Charset.forName(charset));
    }

    /**
     * 将编码的byteBuffer数据转换为字符串
     *
     * @param data    数据
     * @param charset 字符集，如果为空使用当前系统字符集
     * @return 字符串
     */
    public static String str(ByteBuffer data, Charset charset) {
        if (null == charset) {
            charset = Charset.defaultCharset();
        }
        return charset.decode(data).toString();
    }

    /**
     * 字符串转换为byteBuffer
     *
     * @param str     字符串
     * @param charset 编码
     * @return byteBuffer
     */
    public static ByteBuffer byteBuffer(String str, String charset) {
        return ByteBuffer.wrap(StrKit.bytes(str, charset));
    }

    /**
     * 以 conjunction 为分隔符将多个对象转换为字符串
     *
     * @param conjunction 分隔符
     * @param objs        数组
     * @return 连接后的字符串
     */
    public static String join(String conjunction, Object... objs) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (Object item : objs) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(conjunction);
            }
            sb.append(item);
        }
        return sb.toString();
    }

    /**
     * 将驼峰式命名的字符串转换为下划线方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：HelloWorld->hello_world
     *
     * @param camelCaseStr 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String toUnderlineCase(String camelCaseStr) {
        if (camelCaseStr == null) {
            return null;
        }

        final int length = camelCaseStr.length();
        StringBuilder sb = new StringBuilder();
        char c;
        boolean isPreUpperCase = false;
        for (int i = 0; i < length; i++) {
            c = camelCaseStr.charAt(i);
            boolean isNextUpperCase = true;
            if (i < (length - 1)) {
                isNextUpperCase = Character.isUpperCase(camelCaseStr.charAt(i + 1));
            }
            if (Character.isUpperCase(c)) {
                if (!isPreUpperCase || !isNextUpperCase) {
                    if (i > 0) {
                        sb.append(S_UNDERLINE);
                    }
                }
                isPreUpperCase = true;
            } else {
                isPreUpperCase = false;
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 将下划线方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：hello_world->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String toCamelCase(String name) {
        if (name == null) {
            return null;
        }
        if (name.contains(S_UNDERLINE)) {
            name = name.toLowerCase();

            StringBuilder sb = new StringBuilder(name.length());
            boolean upperCase = false;
            for (int i = 0; i < name.length(); i++) {
                char c = name.charAt(i);

                if (c == '_') {
                    upperCase = true;
                } else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return name;
        }
    }

    /**
     * 包装指定字符串
     *
     * @param str    被包装的字符串
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 包装后的字符串
     */
    public static String wrap(String str, String prefix, String suffix) {
        return format("{}{}{}", prefix, str, suffix);
    }

    /**
     * 指定字符串是否被包装
     *
     * @param str    字符串
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 是否被包装
     */
    public static boolean isWrap(String str, String prefix, String suffix) {
        return str.startsWith(prefix) && str.endsWith(suffix);
    }

    /**
     * 指定字符串是否被同一字符包装（前后都有这些字符串）
     *
     * @param str     字符串
     * @param wrapper 包装字符串
     * @return 是否被包装
     */
    public static boolean isWrap(String str, String wrapper) {
        return isWrap(str, wrapper, wrapper);
    }

    /**
     * 指定字符串是否被同一字符包装（前后都有这些字符串）
     *
     * @param str     字符串
     * @param wrapper 包装字符
     * @return 是否被包装
     */
    public static boolean isWrap(String str, char wrapper) {
        return isWrap(str, wrapper, wrapper);
    }

    /**
     * 指定字符串是否被包装
     *
     * @param str        字符串
     * @param prefixChar 前缀
     * @param suffixChar 后缀
     * @return 是否被包装
     */
    public static boolean isWrap(String str, char prefixChar, char suffixChar) {
        return str.charAt(0) == prefixChar && str.charAt(str.length() - 1) == suffixChar;
    }

    /**
     * 补充字符串以满足最小长度 StrKit.padPre("1", 3, '0');//"001"
     *
     * @param str       字符串
     * @param minLength 最小长度
     * @param padChar   补充的字符
     * @return 补充后的字符串
     */
    public static String padPre(String str, int minLength, char padChar) {
        if (str.length() >= minLength) {
            return str;
        }
        StringBuilder sb = new StringBuilder(minLength);
        for (int i = str.length(); i < minLength; i++) {
            sb.append(padChar);
        }
        sb.append(str);
        return sb.toString();
    }

    /**
     * 补充字符串以满足最小长度 StrKit.padEnd("1", 3, '0');//"100"
     *
     * @param str       字符串
     * @param minLength 最小长度
     * @param padChar   补充的字符
     * @return 补充后的字符串
     */
    public static String padEnd(String str, int minLength, char padChar) {
        if (str.length() >= minLength) {
            return str;
        }
        StringBuilder sb = new StringBuilder(minLength);
        sb.append(str);
        for (int i = str.length(); i < minLength; i++) {
            sb.append(padChar);
        }
        return sb.toString();
    }

    /**
     * 创建StringBuilder对象
     *
     * @return StringBuilder对象
     */
    public static StringBuilder builder() {
        return new StringBuilder();
    }

    /**
     * 创建StringBuilder对象
     *
     * @return StringBuilder对象
     */
    public static StringBuilder builder(int capacity) {
        return new StringBuilder(capacity);
    }

    /**
     * 创建StringBuilder对象
     *
     * @return StringBuilder对象
     */
    public static StringBuilder builder(String... strs) {
        final StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str);
        }
        return sb;
    }

    /**
     * 获得StringReader
     *
     * @param str 字符串
     * @return StringReader
     */
    public static StringReader getReader(String str) {
        return new StringReader(str);
    }

    /**
     * 获得StringWriter
     *
     * @return StringWriter
     */
    public static StringWriter getWriter() {
        return new StringWriter();
    }

    /**
     * 统计指定内容中包含指定字符串的数量
     *
     * @param content      内容
     * @param strForSearch 被统计的字符串
     * @return 包含数量
     */
    public static int count(String content, String strForSearch) {
        if (Func.isEmpty(content) || Func.isEmpty(strForSearch)) {
            return 0;
        }
        int contentLength = content.length();
        if (strForSearch.length() > contentLength) {
            return 0;
        }
        return contentLength - content.replace(strForSearch, S_EMPTY).length();
    }

    /**
     * 统计指定内容中包含指定字符的数量
     *
     * @param content       内容
     * @param charForSearch 被统计的字符
     * @return 包含数量
     */
    public static int count(String content, char charForSearch) {
        int count = 0;
        int contentLength = content.length();
        for (int i = 0; i < contentLength; i++) {
            if (charForSearch == content.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获得字符串对应byte数组
     *
     * @param str     字符串
     * @param charset 编码，如果为<code>null</code>使用系统默认编码
     * @return bytes
     */
    public static byte[] getBytes(String str, Charset charset) {
        if (null == str) {
            return null;
        }
        return Func.isEmpty(charset) ? str.getBytes(CharsetKit.DEFAULT_CHARSET) : str.getBytes(charset);
    }

    private static final char SEPARATOR = '_';

    /**
     * 判断是否为中文
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        boolean result = false;
        for (int i = 0; i < str.length(); i++) {
            int chr1 = str.charAt(i);
            // 汉字范围 \u4e00-\u9fa5 (中文)
            if (chr1 >= 19968 && chr1 <= 171941) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 把中文转成Unicode码
     *
     * @param str
     * @return
     */
    public static String chinaToUnicode(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            int chr1 = str.charAt(i);
            // 汉字范围 \u4e00-\u9fa5 (中文)
            if (chr1 >= 19968 && chr1 <= 171941) {
                result.append("\\u").append(Integer.toHexString(chr1));
            } else {
                result.append(str.charAt(i));
            }
        }
        return result.toString();
    }

    /**
     * 判断是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (null == obj) {
            return true;
        } else if (StrKit.S_EMPTY.equals(obj)) {
            return true;
        }
        return false;
    }

    public static boolean isNotNullOrEmpty(Object obj) {
        return !isNullOrEmpty(obj);
    }

    public static String getUniqueName(String filename) {
        return getUUID() + filename.substring(filename.lastIndexOf("."));
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getSimpleUUID() {
        return UUID.randomUUID().toString().replaceAll("-", StrKit.S_EMPTY);
    }

    /**
     * 32位FNV算法
     *
     * @param data 字符串
     * @return String hash
     */
    public static String hash(String data) {

        return data.hashCode() + StrKit.S_EMPTY;
    }

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder())
                .append(Character.toLowerCase(s.charAt(0)))
                .append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转大写
     *
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder())
                .append(Character.toUpperCase(s.charAt(0)))
                .append(s.substring(1)).toString();
        }
    }

    public static String transNullToString(String value) {
        value = value == null ? StrKit.S_EMPTY : value;
        return value;
    }

    public static boolean isInvalieStringArray(String[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        for (String s : array) {
            if (StrKit.isNotBlank(s)) {
                return false;
            }
        }
        return true;
    }

    public static String[] removeEmptyElement(String[] array) {
        List<String> result = Lists.newArrayList();
        for (String s : array) {
            if (StrKit.isNotBlank(s)) {
                result.add(s);
            }
        }
        return result.toArray(new String[result.size()]);
    }

    public static boolean isArrayContainString(String[] strArray, String string) {
        if ((StrKit.S_EMPTY.equals(string)) || (string == null)) {
            return false;
        }
        for (int i = 0; i < strArray.length; i++) {
            if (string.equals(strArray[i])) {
                return true;
            }
        }
        return false;
    }

    public static String[] splitStringToArray(String string, String split) {
        StringTokenizer stT = new StringTokenizer(string, split);
        String[] strArr = new String[stT.countTokens()];
        int i = 0;
        while (stT.hasMoreTokens()) {
            strArr[i] = stT.nextToken();
            i++;
        }
        return strArr;
    }

    public static String formatEditorToHtml(String value) {
        if (value == null) {
            return StrKit.S_EMPTY;
        }
        char[] content = new char[value.length()];
        value.getChars(0, value.length(), content, 0);
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < content.length; i++) {
            switch (content[i]) {
                case '\r':
                    result.append(StrKit.S_EMPTY);
                    break;
                case '\n':
                    result.append(StrKit.S_EMPTY);
                    break;
                case '\013':
                case '\f':
                default:
                    result.append(content[i]);
            }
        }
        return result.toString();
    }

    public static String formatHtmlToStr(String input) {
        if (input == null) {
            return null;
        }
        char[] s = input.toCharArray();
        int length = s.length;
        StringBuffer ret = new StringBuffer(length);

        for (int i = 0; i < length; i++) {
            if (s[i] == '&') {
                if (((i + 3) < length) &&
                    (s[i + 1] == 'l') &&
                    (s[i + TWO_SIZE] == 't') &&
                    // &lt; = <
                    (s[i + 3] == ';')) {
                    ret.append('<');
                    i += 3;
                } else if (((i + 3) < length) &&
                    (s[i + 1] == 'g') &&
                    (s[i + TWO_SIZE] == 't') &&
                    // &gt; = >
                    (s[i + 3] == ';')) {
                    ret.append('>');
                    i += 3;
                } else if (((i + 4) < length) &&
                    (s[i + 1] == 'a') &&
                    (s[i + TWO_SIZE] == 'm') &&
                    (s[i + 3] == 'p') &&
                    // &amp; = &
                    (s[i + 4] == ';')) {
                    ret.append('&');
                    i += 4;
                } else if (((i + 5) < length) &&
                    (s[i + 1] == 'q') &&
                    (s[i + TWO_SIZE] == 'u') &&
                    (s[i + 3] == 'o') &&
                    (s[i + 4] == 't') &&
                    // &quot; = "
                    (s[i + 5] == ';')) {
                    ret.append('"');
                    i += 5;
                } else {
                    ret.append('&');
                }
            } else {
                ret.append(s[i]);
            }
        }// for
        return ret.toString().replaceAll("lt;", "<").replaceAll("gt;", ">");
    }

    public static String formatStringToHtml(String value) {
        if (value == null) {
            return StrKit.S_EMPTY;
        }
        char[] content = new char[value.length()];
        value.getChars(0, value.length(), content, 0);
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < content.length; i++) {
            switch (content[i]) {
                case '\r':
                    result.append(StrKit.S_EMPTY);
                    break;
                case '\n':
                    result.append("<br/>");
                    break;
                case '\t':
                    result.append("    ");
                    break;
                case '\\':
                    result.append(S_SLASH);
                    break;
                case '&':
                    result.append("&amp;");
                    break;
                case '<':
                    result.append("&lt;");
                    break;
                case '>':
                    result.append("&gt;");
                    break;
                case '"':
                    result.append("&quot;");
                    break;
                case '\'':
                    result.append("&#39;");
                    break;
                default:
                    result.append(content[i]);
            }
        }
        return result.toString();
    }

    public static String formatStringToXML(String value) {
        if (value == null) {
            return StrKit.S_EMPTY;
        }
        char[] content = new char[value.length()];
        value.getChars(0, value.length(), content, 0);
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < content.length; i++) {
            switch (content[i]) {
                case '&':
                    result.append("&amp;");
                    break;
                case '<':
                    result.append("&lt;");
                    break;
                case '>':
                    result.append("&gt;");
                    break;
                case '"':
                    result.append("&quot;");
                    break;
                case '\'':
                    result.append("&#39;");
                    break;
                default:
                    result.append(content[i]);
            }
        }
        return result.toString();
    }

    public static boolean hasLength(String str) {
        return (str != null) && (str.length() > 0);
    }

    public static boolean hasText(String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0)) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static String trimLeadingWhitespace(String str) {
        if (str.length() == 0) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str);
        while ((buf.length() > 0) && (Character.isWhitespace(buf.charAt(0)))) {
            buf.deleteCharAt(0);
        }
        return buf.toString();
    }

    public static String trimTrailingWhitespace(String str) {
        if (str.length() == 0) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str);
        while ((buf.length() > 0) && (Character.isWhitespace(buf.charAt(buf.length() - 1)))) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if ((str == null) || (prefix == null)) {
            return false;
        }
        if (str.startsWith(prefix)) {
            return true;
        }
        if (str.length() < prefix.length()) {
            return false;
        }
        String lcStr = str.substring(0, prefix.length()).toLowerCase();
        String lcPrefix = prefix.toLowerCase();
        return lcStr.equals(lcPrefix);
    }

    public static int countOccurrencesOf(String str, String sub) {
        if ((str == null) || (sub == null) || (str.length() == 0) || (sub.length() == 0)) {
            return 0;
        }
        int count = 0;
        int pos = 0;
        int idx;
        while ((idx = str.indexOf(sub, pos)) != -1) {
            count++;
            pos = idx + sub.length();
        }
        return count;
    }

    public static String replace(String inString, Map<String, String> params) {

        if (StrKit.isBlank(inString) || params == null || params.isEmpty()) {
            return inString;
        }

        for (Entry<String, String> entry : params.entrySet()) {
            inString = inString.replaceAll(entry.getKey(), entry.getValue());
        }
        return inString;
    }

    public static String replace(String inString, String oldPattern, String newPattern) {
        if (inString == null) {
            return null;
        }
        if ((oldPattern == null) || (newPattern == null)) {
            return inString;
        }
        StringBuffer sbuf = new StringBuffer();

        int pos = 0;
        int index = inString.indexOf(oldPattern);

        int patLen = oldPattern.length();
        while (index >= 0) {
            sbuf.append(inString.substring(pos, index));
            sbuf.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }
        sbuf.append(inString.substring(pos));

        return sbuf.toString();
    }

    public static String delete(String inString, String pattern) {
        return replace(inString, pattern, StrKit.S_EMPTY);
    }

    public static String deleteAny(String inString, String charsToDelete) {
        if ((inString == null) || (charsToDelete == null)) {
            return inString;
        }
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < inString.length(); i++) {
            char c = inString.charAt(i);
            if (charsToDelete.indexOf(c) == -1) {
                out.append(c);
            }
        }
        return out.toString();
    }

    public static String unqualify(String qualifiedName) {
        return unqualify(qualifiedName, '.');
    }

    public static String unqualify(String qualifiedName, char separator) {
        return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
    }

    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if ((str == null) || (str.length() == 0)) {
            return str;
        }
        StringBuffer buf = new StringBuffer(str.length());
        if (capitalize) {
            buf.append(Character.toUpperCase(str.charAt(0)));
        } else {
            buf.append(Character.toLowerCase(str.charAt(0)));
        }
        buf.append(str.substring(1));
        return buf.toString();
    }

    public static String getFilename(String path) {
        int separatorIndex = path.lastIndexOf(S_SLASH);
        return separatorIndex != -1 ? path.substring(separatorIndex + 1) : path;
    }

    public static String applyRelativePath(String path, String relativePath) {
        path = path.replace("\\", S_SLASH);
        boolean isEnd = path.endsWith(S_SLASH);
        String newDirectory = path;
        if (isEnd) {
            newDirectory = path.substring(0, path.length() - 1);
        }
        if (!relativePath.startsWith(S_SLASH)) {
            newDirectory = newDirectory + S_SLASH;
        }
        return newDirectory + relativePath;
    }

    public static String applyRelativeDirectory(String path, String relativeDirectory) {
        path = path.replace("\\", S_SLASH);
        boolean isEnd = path.endsWith(S_SLASH);
        String newDirectory = path;
        if (isEnd) {
            newDirectory = path.substring(0, path.length() - 1);
        }
        if (!relativeDirectory.startsWith(S_SLASH)) {
            newDirectory = newDirectory + S_SLASH;
        }
        return newDirectory + relativeDirectory;
    }

    public static String cleanPath(String path) {
        String pathToUse = replace(path, "\\", S_SLASH);
        String[] pathArray = delimitedListToStringArray(pathToUse, S_SLASH);
        List<String> pathElements = Lists.newLinkedList();
        int tops = 0;
        for (int i = pathArray.length - 1; i >= 0; i--) {
            if (".".equals(pathArray[i])) {
                continue;
            }
            if ("..".equals(pathArray[i])) {
                tops++;
            } else if (tops > 0) {
                tops--;
            } else {
                pathElements.add(0, pathArray[i]);
            }
        }

        return collectionToDelimitedString(pathElements, S_SLASH);
    }

    public static boolean pathEquals(String path1, String path2) {
        return cleanPath(path1).equals(cleanPath(path2));
    }

    public static Locale parseLocaleString(String localeString) {
        String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
        String language = parts.length > 0 ? parts[0] : StrKit.S_EMPTY;
        String country = parts.length > 1 ? parts[1] : StrKit.S_EMPTY;
        String variant = parts.length > TWO_SIZE ? parts[TWO_SIZE] : StrKit.S_EMPTY;
        return language.length() > 0 ? new Locale(language, country, variant) : null;
    }

    public static String[] addStringToArray(String[] arr, String str) {
        String[] newArr = new String[arr.length + 1];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        newArr[arr.length] = str;
        return newArr;
    }

    public static String[] sortStringArray(String[] source) {
        if (source == null) {
            return new String[0];
        }
        Arrays.sort(source);
        return source;
    }

    public static String[] tokenizeToStringArray(String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens,
                                                 boolean ignoreEmptyTokens) {
        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = Lists.newArrayList();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if ((!ignoreEmptyTokens) || (token.length() > 0)) {
                tokens.add(token);
            }
        }
        return tokens.toArray(new String[tokens.size()]);
    }

    public static String[] delimitedListToStringArray(String str, String delimiter) {
        if (str == null) {
            return new String[0];
        }
        if (delimiter == null) {
            return new String[]{str};
        }

        List<String> result = Lists.newArrayList();
        int pos = 0;
        int delPos;
        while ((delPos = str.indexOf(delimiter, pos)) != -1) {
            result.add(str.substring(pos, delPos));
            pos = delPos + delimiter.length();
        }
        if ((str.length() > 0) && (pos <= str.length())) {
            result.add(str.substring(pos));
        }

        return result.toArray(new String[result.size()]);
    }

    public static String[] commaDelimitedListToStringArray(String str) {
        return delimitedListToStringArray(str, ",");
    }

    public static Set<String> commaDelimitedListToSet(String str) {
        Set<String> set = Sets.newTreeSet();
        String[] tokens = commaDelimitedListToStringArray(str);
        for (int i = 0; i < tokens.length; i++) {
            set.add(tokens[i]);
        }
        return set;
    }

    public static String arrayToDelimitedString(Object[] arr, String delim) {
        if (arr == null) {
            return StrKit.S_EMPTY;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(delim);
            }
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    public static <T> String collectionToDelimitedString(Collection<T> coll, String delim, String prefix, String suffix) {
        if (coll == null) {
            return StrKit.S_EMPTY;
        }

        StringBuffer sb = new StringBuffer();
        Iterator<T> it = coll.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (i > 0) {
                sb.append(delim);
            }
            sb.append(prefix).append(it.next()).append(suffix);
            i++;
        }
        return sb.toString();
    }

    public static <T> String collectionToDelimitedString(Collection<T> coll, String delim) {
        return collectionToDelimitedString(coll, delim, StrKit.S_EMPTY, StrKit.S_EMPTY);
    }

    public static String arrayToCommaDelimitedString(Object[] arr) {
        return arrayToDelimitedString(arr, ",");
    }

    public static <T> String collectionToCommaDelimitedString(Collection<T> coll) {
        return collectionToDelimitedString(coll, ",");
    }

    public static String percent(double p1, double p2) {
        double p3 = p1 / p2;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(0);
        String str = nf.format(p3);
        return str;
    }

    public static String filterString(String str) {
        if (StrKit.isBlank(str)) {
            return str;
        }
        str = str.trim();
        String[] filter = {"+", "-", "&&", "||", "!", "(", ")", "{", "}", "[", "]", "^", "\"", "~", "*", "?", ":",
            "\\", ";"};
        for (String s : filter) {
            str = StrKit.replace(str, s, " ");
        }
        return str;
    }

    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许数字
        String regEx = "[^0-9]";
        // 清除掉所有特殊字符
        //String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll(StrKit.S_EMPTY).trim();
    }

    /**
     * 检测是否有emoji字符
     *
     * @param source
     * @return 一旦含有就抛出
     */
    public static boolean containsEmoji(String source) {
        if (StrKit.isBlank(source)) {
            return false;
        }

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }

        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return codePoint == 0x0 || codePoint == 0x9 || codePoint == 0xA || codePoint == 0xD || codePoint >= 0x20 && codePoint <= 0xD7FF || codePoint >= 0xE000 && codePoint <= 0xFFFD;
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {

        if (!containsEmoji(source)) {
            // 如果不包含，直接返回
            return source;
        }
        //到这里铁定包含
        StringBuilder buf = null;

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }

                buf.append(codePoint);
            }
        }

        if (buf == null) {
            // 如果没有找到 emoji表情，则返回源字符串
            return source;
        } else {
            if (buf.length() == len) {
                // 这里的意义在于尽可能少的toString，因为会重新生成字符串
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }

    }

    public static String html2Text(String inputString) {
        // 含html标签的字符串
        String htmlStr = inputString;
        String textStr = StrKit.S_EMPTY;
        Pattern pScript;
        Matcher mScript;
        Pattern pStyle;
        Matcher mStyle;
        Pattern pHtml;
        Matcher mHtml;

        try {
            // 定义script的正则表达式
            String regExScript = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            // 定义style的正则表达式
            String regExStyle = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            // 定义HTML标签的正则表达式
            //String regEx_html = "<[^>]+>";
            // 包括div的不剔除  去除其它所有html标签
            String regExHtmlWithoutDiv = "<(?!\\s*div)(?!/\\s*div)(?!\\s*/div)[^>]+>";
            pScript = Pattern.compile(regExScript, Pattern.CASE_INSENSITIVE);
            mScript = pScript.matcher(htmlStr);
            // 过滤script标签
            htmlStr = mScript.replaceAll(StrKit.S_EMPTY);

            pStyle = Pattern.compile(regExStyle, Pattern.CASE_INSENSITIVE);
            mStyle = pStyle.matcher(htmlStr);
            // 过滤style标签
            htmlStr = mStyle.replaceAll(StrKit.S_EMPTY);

            pHtml = Pattern.compile(regExHtmlWithoutDiv,
                Pattern.CASE_INSENSITIVE);
            mHtml = pHtml.matcher(htmlStr);
            // 过滤html标签
            htmlStr = mHtml.replaceAll(StrKit.S_EMPTY);

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("html2Text: " + e.getMessage());
        }

        return textStr;
    }

    public static String escapeStringParam(String param) {
        if (StringUtils.isEmpty(param)) {
            return param;
        }
        char[] paramArr = param.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char p : paramArr) {
            switch (p) {
                case '\'':
                    sb.append("\\").append(p);
                    break;
                case '\"':
                    sb.append("\\").append(p);
                    break;
                case '\\':
                    sb.append("\\").append(p);
                    break;
                case '&':
                    sb.append("\\").append(p);
                    break;
                case '_':
                    sb.append("\\").append(p);
                    break;
                default:
                    sb.append(p);
            }
        }
        return sb.toString();
    }

    /**
     * 计算两个字符串最长相同子串
     *
     * @param @param  s1
     * @param @param  s2
     * @param @return
     * @return String
     */
    public static String search(String s1, String s2) {
        String max = StrKit.S_EMPTY;
        for (int i = 0; i < s1.length(); i++) {
            for (int j = i; j <= s1.length(); j++) {
                String sub = s1.substring(i, j);
                if ((s2.indexOf(sub) != -1) && sub.length() > max.length()) {
                    max = sub;
                }
            }
        }
        return max;
    }

    /**
     * 根据content,key,按预定算法计算hash值.
     *
     * @param text
     * @param key
     * @return 加密结果
     */
    public static String hash(String text, String key) {
        // 0.检验输入合法性
        if (text == null) {
            throw new IllegalArgumentException("text can't be null");
        }
        if (key == null) {
            throw new IllegalArgumentException("key can't be null");
        }

        // 1.令S=md5(key);将text末尾填0至16字节的整数倍(n),将补0后的text按16字节分组
        // 为c(1),c(2),...c(n);令b(1),b(2),...b(n)为中间变量;令最终结果为hash.
        String s = Md5Kit.md5(key);
        byte[] textData = text.getBytes(CharsetKit.DEFAULT_CHARSET);
        int len = textData.length;
        int n = (len + 15) / SIXTEEN;
        byte[] tempData = new byte[n * SIXTEEN];
        for (int i = len; i < n * SIXTEEN; i++) {
            tempData[i] = 0;
        }
        System.arraycopy(textData, 0, tempData, 0, len);
        textData = tempData;
        String[] c = new String[n];
        for (int i = 0; i < n; i++) {
            c[i] = new String(textData, SIXTEEN * i, SIXTEEN, CharsetKit.DEFAULT_CHARSET);
        }
        // end c
        String[] b = new String[n];
        String hash;

        // 2.计算b(i)
        // b(1)=md5(S+c(1))
        // b(2)=md5(b(1)+c(2))
        // ...
        // b(n)=md5(b(n-1)+c(n))
        String temp = s;
        StringBuilder target = new StringBuilder();
        for (int i = 0; i < n; i++) {
            b[i] = Md5Kit.md5(temp + c[i]);
            temp = b[i];
            target.append(b[i]);
        }

        // 3.hash=md5(b(1)+b(2)+...+b(n))
        hash = Md5Kit.md5(target.toString());
        return hash;
    }

    /**
     * 骆驼峰结构: 比如gmt_modify ==> gmtModify
     *
     * @param name
     * @return
     */
    public static String toCamelName(String name) {
        char[] chars = name.toCharArray();
        char[] result = new char[chars.length];
        int curPos = 0;
        boolean upperCaseNext = false;
        for (char ch : chars) {
            if (ch == '_') {
                upperCaseNext = true;
            } else if (upperCaseNext) {
                result[curPos++] = Character.toUpperCase(ch);
                upperCaseNext = false;
            } else {
                result[curPos++] = ch;
            }
        }
        return new String(result, 0, curPos);
    }

    public static String toUnderlineName(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i >= 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0) {
                        sb.append(SEPARATOR);
                    }
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }
}
