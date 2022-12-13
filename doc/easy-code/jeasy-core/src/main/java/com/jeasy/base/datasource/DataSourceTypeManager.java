package com.jeasy.base.datasource;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public final class DataSourceTypeManager {

    public static final String MASTER = "master";

    public static final String SLAVE = "slave";

    private static final ThreadLocal<String> DATA_SOURCE_TYPES = new ThreadLocal<>();

    private DataSourceTypeManager() {

    }

    public static String get() {
        return DATA_SOURCE_TYPES.get();
    }

    public static void set(final String dataSourceType) {
        DATA_SOURCE_TYPES.set(dataSourceType);
    }

    public static void reset() {
        DATA_SOURCE_TYPES.remove();
    }

    public static boolean isChoiceWrite() {
        return DATA_SOURCE_TYPES.get() != null && DATA_SOURCE_TYPES.get().contains(MASTER);
    }

    public static void markRead(final String lookupKey) {
        set(lookupKey);
    }

    public static void markWrite(final String lookupKey) {
        set(lookupKey);
    }
}
