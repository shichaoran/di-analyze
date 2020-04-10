package com.vd.canary.data.constants;

public class Constant {

    /* 分页参数 */
    public static Integer ES_PAGE_SIZE = 15;          // 每页数量
    public static Integer ES_DEFAULT_PAGE_NUMBER = 0; // 默认当前页码

    /* 搜索模式 */
    public static String ES_SCORE_MODE_SUM = "sum"; // 权重分求和模式
    public static Float  ES_MIN_SCORE = 10.0F;      // 由于无相关性的分值默认为 1 ，设置权重分最小值为 10

}
