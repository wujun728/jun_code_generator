package com.jeasy.base.web.controller;

import com.jeasy.common.str.StrKit;
import com.jeasy.common.web.WafKit;
import org.springframework.web.util.HtmlUtils;

import java.beans.PropertyEditorSupport;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class StringEscapeEditor extends PropertyEditorSupport {

    private static final boolean FILTER_XSS = true;

    private static final boolean FILTER_SQL = true;

    public StringEscapeEditor() {
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        return value != null ? value.toString() : StrKit.S_EMPTY;
    }

    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        if (text == null) {
            setValue(null);
        } else {
            String filterText;
            if (FILTER_XSS) {
                filterText = WafKit.stripXSS(text);
            }

            if (FILTER_SQL) {
                filterText = WafKit.stripSqlInjection(text);
            }
            setValue(HtmlUtils.htmlEscape(filterText));
        }
    }

}
