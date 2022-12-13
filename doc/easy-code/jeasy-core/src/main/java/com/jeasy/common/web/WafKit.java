package com.jeasy.common.web;

import com.google.common.collect.Lists;
import com.jeasy.common.Func;
import com.jeasy.common.str.StrKit;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Web防火墙工具类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class WafKit {

    private static final Pattern SCRIPT_PATTERN = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);

    private static final Pattern SCRIPT_SRC_SINGLE_PATTERN = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE
        | Pattern.MULTILINE | Pattern.DOTALL);

    private static final Pattern SCRIPT_SRC_DOUBLE_PATTERN = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE
        | Pattern.MULTILINE | Pattern.DOTALL);

    private static final Pattern SCRIPT_END_PATTERN = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);

    private static final Pattern SCRIPT_START_PATTERN = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE
        | Pattern.MULTILINE | Pattern.DOTALL);

    private static final Pattern SCRIPT_EVAL_PATTERN = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE
        | Pattern.MULTILINE | Pattern.DOTALL);

    private static final Pattern SCRIPT_EXPRESSION_PATTERN = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE
        | Pattern.MULTILINE | Pattern.DOTALL);

    private static final Pattern JAVA_SCRIPT_PATTERN = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);

    private static final Pattern VB_SCRIPT_PATTERN = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);

    private static final Pattern ONLOAD_SCRIPT_PATTERN = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE
        | Pattern.MULTILINE | Pattern.DOTALL);

    private static final Pattern ONERROR_SCRIPT_PATTERN = Pattern.compile("onerror(.*?)=", Pattern.CASE_INSENSITIVE
        | Pattern.MULTILINE | Pattern.DOTALL);

    private static final List<Pattern> XSS_PATTERN_LIST = Lists.newArrayList(Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>", Pattern.CASE_INSENSITIVE),
        Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile("(javascript:|vbscript:|view-source:)*", Pattern.CASE_INSENSITIVE),
        Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile("(window\\.location|window\\.|\\.location|document\\.cookie|document\\.|alert\\(.*?\\)|window\\.open\\()*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile("<+\\s*\\w*\\s*(oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onerror=|onerroupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove|onmousout|onmouseover|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy|onbeforecut|onbeforedeactivate|onbeforeeditocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange|onchange|onclick|oncontextmenu|onpaste|onpropertychange|onreadystatechange|onreset|onresize|onresizend|onresizestart|onrowenter|onrowexit|onrowsdelete|onrowsinserted|onscroll|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload)+\\s*=+", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

    /**
     * @param value 待处理内容
     * @return
     * @Description 过滤XSS脚本内容
     */
    public static String cleanXSS(String value) {
        if (StrKit.isNotBlank(value)) {
            Matcher matcher;
            for (Pattern pattern : XSS_PATTERN_LIST) {
                matcher = pattern.matcher(value);
                if (matcher.find()) {
                    value = matcher.replaceAll(StrKit.S_EMPTY);
                }
            }
            value = value.replaceAll("<", "<").replaceAll(">", ">");
        }
        return value;
    }

    /**
     * @param value 待处理内容
     * @return
     * @Description 过滤XSS脚本内容
     */
    public static String stripXSS(String value) {
        String rlt = null;

        if (Func.isNotEmpty(value)) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            rlt = value.replaceAll(StrKit.S_EMPTY, StrKit.S_EMPTY);

            // Avoid anything between script tags
            rlt = SCRIPT_PATTERN.matcher(rlt).replaceAll(StrKit.S_EMPTY);

            // Avoid anything in a src='...' type of expression
            rlt = SCRIPT_SRC_SINGLE_PATTERN.matcher(rlt).replaceAll(StrKit.S_EMPTY);

            rlt = SCRIPT_SRC_DOUBLE_PATTERN.matcher(rlt).replaceAll(StrKit.S_EMPTY);

            // Remove any lonesome </script> tag
            rlt = SCRIPT_END_PATTERN.matcher(rlt).replaceAll(StrKit.S_EMPTY);

            // Remove any lonesome <script ...> tag
            rlt = SCRIPT_START_PATTERN.matcher(rlt).replaceAll(StrKit.S_EMPTY);

            // Avoid eval(...) expressions
            rlt = SCRIPT_EVAL_PATTERN.matcher(rlt).replaceAll(StrKit.S_EMPTY);

            // Avoid expression(...) expressions
            rlt = SCRIPT_EXPRESSION_PATTERN.matcher(rlt).replaceAll(StrKit.S_EMPTY);

            // Avoid javascript:... expressions
            rlt = JAVA_SCRIPT_PATTERN.matcher(rlt).replaceAll(StrKit.S_EMPTY);

            // Avoid vbscript:... expressions
            rlt = VB_SCRIPT_PATTERN.matcher(rlt).replaceAll(StrKit.S_EMPTY);

            // Avoid onload= expressions
            rlt = ONLOAD_SCRIPT_PATTERN.matcher(rlt).replaceAll(StrKit.S_EMPTY);

            // Avoid onerror= expressions
            rlt = ONERROR_SCRIPT_PATTERN.matcher(rlt).replaceAll(StrKit.S_EMPTY);
        }

        return rlt;
    }

    /**
     * @param value 待处理内容
     * @return
     * @Description 过滤SQL注入内容
     */
    public static String stripSqlInjection(String value) {
        return Func.isEmpty(value) ? null : value.replaceAll("('.+--)|(--)|(%7C)", StrKit.S_EMPTY);
    }

    /**
     * @param value 待处理内容
     * @return
     * @Description 过滤SQL/XSS注入内容
     */
    public static String stripSqlXSS(String value) {
        return stripXSS(stripSqlInjection(value));
    }

    public static void main(String[] args) {
        System.out.println(stripXSS("< img src='1' onerror=alert(2)>"));
        String value = cleanXSS("<script language=text/javascript>alert(document.cookie);</script>");
        System.out.println("type-1: '" + value + "'");
        value = cleanXSS("<script src='' onerror='alert(document.cookie)'></script>");
        System.out.println("type-2: '" + value + "'");
        value = cleanXSS("</script>");
        System.out.println("type-3: '" + value + "'");
        value = cleanXSS(" eval(abc);");
        System.out.println("type-4: '" + value + "'");
        value = cleanXSS(" expression(abc);");
        System.out.println("type-5: '" + value + "'");
        value = cleanXSS("<img src='' onerror='alert(document.cookie);'></img>");
        System.out.println("type-6: '" + value + "'");
        value = cleanXSS("<img src='' onerror='alert(document.cookie);'/>");
        System.out.println("type-7: '" + value + "'");
        value = cleanXSS("<img src='' onerror='alert(document.cookie);'>");
        System.out.println("type-8: '" + value + "'");
        value = cleanXSS("<script language=text/javascript>alert(document.cookie);");
        System.out.println("type-9: '" + value + "'");
        value = cleanXSS("<script>window.location='url'");
        System.out.println("type-10: '" + value + "'");
    }

}
