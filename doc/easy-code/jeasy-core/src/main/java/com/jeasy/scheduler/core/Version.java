package com.jeasy.scheduler.core;

import lombok.Data;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Data
public class Version {

    private static final String VERSION = "uncode-schedule-1.0.0";

    public static String getVersion() {
        return VERSION;
    }

    public static boolean isCompatible(final String dataVersion) {
        return VERSION.compareTo(dataVersion) >= 0;
    }
}
