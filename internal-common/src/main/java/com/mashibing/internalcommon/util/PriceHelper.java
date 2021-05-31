package com.mashibing.internalcommon.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * description  价格计算 <BR>
 *
 * @author zhao.song
 * @version 1.0
 * @since 2021/5/31 9:34
 **/
@Slf4j
public final class PriceHelper {

    /**
     * description: 加法运算 <BR>
     *
     * @param a:数值a
     * @param value:其他数值
     * @return {@link BigDecimal}
     * @author zhao.song   2021/5/31 9:54
     */
    public static BigDecimal add(BigDecimal a, BigDecimal... value) {
        return resetScale(Arrays.stream(value).reduce(a, BigDecimal::add));
    }

    /**
     * description: 减法运算 <BR>
     *
     * @param a:
     * @param value:
     * @return {@link BigDecimal}
     * @author zhao.song   2021/5/31 9:55
     */
    public static BigDecimal subtract(BigDecimal a, BigDecimal... value) {
        return resetScale(Arrays.stream(value).reduce(a, BigDecimal::subtract)).max(BigDecimal.ZERO);
    }

    /**
     * description: 减法运算 <BR>
     *
     * @param a:
     * @param value:
     * @return {@link BigDecimal}
     * @author zhao.song   2021/5/31 9:55
     */
    public static BigDecimal subtract(double a, double... value) {
        return subtract(BigDecimal.valueOf(a)
                , Arrays.stream(value).mapToObj(BigDecimal::valueOf).toArray(BigDecimal[]::new));
    }

    /**
     * description: 乘法运算 <BR>
     *
     * @param a:
     * @param b:
     * @return {@link BigDecimal}
     * @author zhao.song   2021/5/31 9:59
     */
    public static BigDecimal multiply(BigDecimal a, double b) {
        return multiply(a, BigDecimal.valueOf(b));
    }

    /**
     * description: 乘法运算 <BR>
     *
     * @param a:
     * @param b:
     * @return {@link BigDecimal}
     * @author zhao.song   2021/5/31 10:01
     */
    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return resetScale(a.multiply(b));
    }

    /**
     * description: 乘法运算 <BR>
     *
     * @param a:
     * @param value:
     * @return {@link BigDecimal}
     * @author zhao.song   2021/5/31 10:04
     */
    public static BigDecimal multiply(BigDecimal a, BigDecimal... value) {
        return resetScale(Arrays.stream(value).reduce(a, BigDecimal::multiply));
    }

    /**
     * description: 除法运算 <BR>
     *
     * @param a:
     * @param b:
     * @return {@link BigDecimal}
     * @author zhao.song   2021/5/31 10:07
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        return resetScale(a.divide(b));
    }

    /**
     * description: 除法运算 <BR>
     *
     * @param a:
     * @param b:
     * @return {@link BigDecimal}
     * @author zhao.song   2021/5/31 10:08
     */
    public static BigDecimal divide(BigDecimal a, double b) {
        return divide(a, BigDecimal.valueOf(b));
    }

    /**
     * 大小比较
     *
     * @param a 数值a
     * @param b 数值b
     * @return a与b中较小的数值
     */
    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        return resetScale(a.min(b));
    }


    /**
     * description: 精度设置 <BR>
     *
     * @param value:
     * @return {@link BigDecimal}
     * @author zhao.song   2021/5/31 9:52
     */
    public static BigDecimal resetScale(BigDecimal value) {
        try {
            return value.setScale(2, RoundingMode.DOWN);
        } catch (Exception ex) {
            log.error("精度设置发生错误", ex);
        }
        return null;
    }

    public static void main(String[] args) {
        BigDecimal d1 = BigDecimal.valueOf(2);
        BigDecimal d2 = BigDecimal.valueOf(2.6);
        BigDecimal d3 = BigDecimal.valueOf(3.0);
        System.out.println(multiply(d1, d2, d3).doubleValue());
    }
}
