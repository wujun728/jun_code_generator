package com.jeasy.common.support;

import java.io.OutputStream;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public interface IMultiOutputStream {

    /**
     * Builds the output stream.
     *
     * @param params the params
     * @return the output stream
     */
    public OutputStream buildOutputStream(Integer... params);

}
