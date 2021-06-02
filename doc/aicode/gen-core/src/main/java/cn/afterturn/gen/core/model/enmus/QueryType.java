package cn.afterturn.gen.core.model.enmus;

/**
 * "1" =
 * "2" !=
 * "3" &gt;
 * "4" &gt;=
 * "5" &lt;
 * "6" &lt;=
 * "7" like
 * "8" lift like
 * "9" right like
 *
 * @author by jueyue on 18-8-7.
 */
public enum QueryType {
    EQ(1, "="),
    NOT_EQ(2, "&lt;&gt;"),
    GT(3, "&gt;"),
    GT_EQ(4, "&gt;="),
    LT(5, "&lt;"),
    LT_EQ(6, "&lt;="),
    LIKE(7, "like concat('%',concat(key,'%')) "),
    LEFT_LIKE(8, "like concat('%', key)"),
    RIGHT_LIKE(9, "like concat(key, '%')");


    private Integer code;
    private String key;

    QueryType(Integer code, String key) {
        this.code = code;
        this.key = key;
    }

    public Integer getCode() {
        return code;
    }

    public String getKey() {
        return key;
    }
}
