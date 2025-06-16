package com.chris.allinone.javacore.calc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author chrischen
 * @date 2025/6/16
 * @desc 数值计算的坑
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(0.1+0.2);
        System.out.println(1.0-0.8);
        System.out.println(4.015*100);
        System.out.println(123.3/100);

        double amount1 = 2.15;
        double amount2 = 1.10;
        if (amount1 - amount2 == 1.05)
            System.out.println("OK");

        System.out.println(new BigDecimal(0.1).add(new BigDecimal(0.2)));
        System.out.println(new BigDecimal(1.0).subtract(new BigDecimal(0.8)));
        System.out.println(new BigDecimal(4.015).multiply(new BigDecimal(100)));
        System.out.println(new BigDecimal(123.3).divide(new BigDecimal(100)));

        System.out.println(new BigDecimal("0.1").add(new BigDecimal("0.2")));
        System.out.println(new BigDecimal("1.0").subtract(new BigDecimal("0.8")));
        System.out.println(new BigDecimal("4.015").multiply(new BigDecimal("100")));
        System.out.println(new BigDecimal("123.3").divide(new BigDecimal("100")));
        System.out.println(BigDecimal.valueOf(0.1).add(BigDecimal.valueOf(0.2)));

        double num1 = 3.35d;
        float num2 = 3.35f;
        System.out.println(String.format("%.1f", num1));//四舍五入
        System.out.println(String.format("%.1f", num2));

        BigDecimal num11 = new BigDecimal("3.35");
        BigDecimal num21 = num11.setScale(1, BigDecimal.ROUND_DOWN);
        System.out.println(num21);
        BigDecimal num3 = num11.setScale(1, BigDecimal.ROUND_HALF_UP);
        System.out.println(num3);

        System.out.println(new BigDecimal("1.0").equals(new BigDecimal("1")));

        Set<BigDecimal> hashSet1 = new HashSet<>();
        hashSet1.add(new BigDecimal("1.0"));
        System.out.println(hashSet1.contains(new BigDecimal("1")));//返回false

        //TreeSet使用元素的compareTo方法比较
        Set<BigDecimal> treeSet = new TreeSet<>();
        treeSet.add(new BigDecimal("1.0"));
        System.out.println(treeSet.contains(new BigDecimal("1")));//返回true

        Set<BigDecimal> hashSet2 = new HashSet<>();
        hashSet2.add(new BigDecimal("1.0").stripTrailingZeros());
        System.out.println(hashSet2.contains(new BigDecimal("1.000").stripTrailingZeros()));//返回true

        try {
            long l = Long.MAX_VALUE;
//            System.out.println(Math.addExact(l, 1));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        BigInteger i = new BigInteger(String.valueOf(Long.MAX_VALUE));
        System.out.println(i.add(BigInteger.ONE).toString());
        try {
            long l = i.add(BigInteger.ONE).longValueExact();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
