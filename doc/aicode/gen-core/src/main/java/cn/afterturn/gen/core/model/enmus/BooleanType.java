package cn.afterturn.gen.core.model.enmus;

/**
 * @author by jueyue on 18-5-20.
 */
public enum BooleanType {

    YES(1, "1"), NO(2, "2");

    private int intD;
    private String str;

    BooleanType(int intD, String str) {
        this.intD = intD;
        this.str = str;
    }

    public int getIntD() {
        return intD;
    }

    public void setIntD(int intD) {
        this.intD = intD;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
