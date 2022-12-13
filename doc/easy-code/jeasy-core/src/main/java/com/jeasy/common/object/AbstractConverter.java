package com.jeasy.common.object;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/8 上午11:35
 */
@Data
@AllArgsConstructor
public abstract class AbstractConverter<F, T> {

    public AbstractConverter(final String property) {
        this.from = property;
        this.to = property;
    }

    public AbstractConverter(final String from, final String to) {
        this.from = from;
        this.to = to;
    }

    private String from;

    private String to;

    private Boolean isLeftSubject = true;

    /**
     * 转换
     *
     * @param val
     * @return
     */
    public abstract T convert(F val);
}
