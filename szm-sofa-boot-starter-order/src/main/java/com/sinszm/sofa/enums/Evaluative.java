package com.sinszm.sofa.enums;

/**
 * 评价级别
 *
 * @author admin
 */
public enum Evaluative {

    /**
     * 满意评价等级
     */
    LEVEL1("极差", 1),
    LEVEL2("差", 2),
    LEVEL3("一般", 3),
    LEVEL4("满意", 4),
    LEVEL5("非常满意", 5);

    private final String name;

    private final int fraction;

    Evaluative(String name, int fraction) {
        this.name = name;
        this.fraction = fraction;
    }

    public String getName() {
        return name;
    }

    public int getFraction() {
        return fraction;
    }
}
