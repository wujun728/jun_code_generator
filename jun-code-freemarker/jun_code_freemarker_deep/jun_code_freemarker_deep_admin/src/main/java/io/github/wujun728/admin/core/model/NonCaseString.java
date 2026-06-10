package io.github.wujun728.admin.core.model;

import lombok.AllArgsConstructor;

/**
 * String 包装类，忽略大小写
 */
@AllArgsConstructor
public class NonCaseString implements CharSequence {
    private String value;

    public static NonCaseString of(String str) {
        assert str != null;
        return new NonCaseString(str);
    }

    public int indexOf(String m) {
        String text = this.value;
        if (text == null || m == null || text.length() < m.length()) {
            return -1;
        }
        return text.toLowerCase().indexOf(m.toLowerCase());
    }

    public int lastIndexOf(String m) {
        String text = this.value;
        if (text == null || m == null || text.length() < m.length()) {
            return -1;
        }
        return text.toLowerCase().lastIndexOf(m.toLowerCase());
    }

    public boolean contains(String m) {
        String text = this.value;
        if (text.length() < m.length()) {
            return false;
        }
        return text.toLowerCase().contains(m.toLowerCase());
    }

    public boolean containsAny(String... matchers) {
        for (String matcher : matchers) {
            if (contains(matcher)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsAllIgnoreCase(String... matchers) {
        for (String matcher : matchers) {
            if (!contains(matcher)) {
                return false;
            }
        }
        return true;
    }

    public NonCaseString trim() {
        return NonCaseString.of(this.value.trim());
    }

    public NonCaseString replace(char oldChar, char newChar) {
        return NonCaseString.of(this.value.replace(oldChar, newChar));
    }

    public NonCaseString replaceAll(String regex, String replacement) {
        return NonCaseString.of(this.value.replaceAll(regex, replacement));
    }

    public NonCaseString substring(int beginIndex) {
        return NonCaseString.of(this.value.substring(beginIndex));
    }

    public NonCaseString substring(int beginIndex, int endIndex) {
        return NonCaseString.of(this.value.substring(beginIndex, endIndex));
    }

    public boolean isNotEmpty() {
        return !this.value.isEmpty();
    }

    @Override
    public int length() {
        return this.value.length();
    }

    @Override
    public char charAt(int index) {
        return this.value.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return this.value.subSequence(start, end);
    }

    public String[] split(String regex) {
        return this.value.split(regex);
    }

    public String get() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}