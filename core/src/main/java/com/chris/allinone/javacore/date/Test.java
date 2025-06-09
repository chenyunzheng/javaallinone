package com.chris.allinone.javacore.date;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author chrischen
 * @date 2025/6/7
 * @desc TODO描述主要功能
 */
public class Test {

    public static void main(String[] args) throws Exception {
        //Calendar、时区
        System.out.println("===> Calendar、时区");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019,11,31, 11,12,13);
        System.out.println(calendar.getTime());

        Calendar calendar2 = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        calendar2.set(2019,11,31, 11,12,13);
        System.out.println(calendar2.getTime());

        //时间戳为绝对值，时间戳在不同时区显示成不同的时间
        System.out.println("===> 时间戳为绝对值，时间戳在不同时区显示成不同的时间");
        String stringDate = "2020-01-02 22:00:00";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //默认时区解析时间表示
        Date date1 = inputFormat.parse(stringDate);
        System.out.println(date1 + ":" + date1.getTime());
        //纽约时区解析时间表示
        inputFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date date2 = inputFormat.parse(stringDate);
        System.out.println(date2 + ":" + date2.getTime());

        System.out.println("===> Java 8 时区 + 日期时间");
        //一个时间表示
        stringDate = "2020-01-02 22:00:00";
        //初始化三个时区
        ZoneId timeZoneSH = ZoneId.of("Asia/Shanghai");
        ZoneId timeZoneNY = ZoneId.of("America/New_York");
        ZoneId timeZoneJST = ZoneOffset.ofHours(9);
        //格式化器
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime date = ZonedDateTime.of(LocalDateTime.parse(stringDate, dateTimeFormatter), timeZoneJST);
        //使用DateTimeFormatter格式化时间，可以通过withZone方法直接设置格式化使用的时区
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        System.out.println(timeZoneSH.getId() + outputFormat.withZone(timeZoneSH).format(date));
        System.out.println(timeZoneNY.getId() + outputFormat.withZone(timeZoneNY).format(date));
        System.out.println(timeZoneJST.getId() + outputFormat.withZone(timeZoneJST).format(date));

        System.out.println("===> simpledateformat bug");
//        simpleDateFormatBug();

        System.out.println("===> 当前时间之后 30 天的时间");
        Date today = new Date();
//        Date nextMonth = new Date(today.getTime() + 30 * 1000 * 60 * 60 * 24);
        Date nextMonth = new Date(today.getTime() + 30L * 1000 * 60 * 60 * 24);
        System.out.println(today);
        System.out.println(nextMonth);

        //测试jdbc connection: serverTimeZone
//        String jdbcConn = "jdbc:mysql://127.0.0.1:3306/test?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
        String jdbcConn = "jdbc:mysql://127.0.0.1:3306/test?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=America/New_York";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "1qaz2wsx");
        Connection connection = DriverManager.getConnection(jdbcConn, properties);
        PreparedStatement preparedStatement = connection.prepareStatement("select * from time_test");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Timestamp ts = rs.getTimestamp(1);
            Timestamp dt = rs.getTimestamp(2);
            System.out.println("ts = " + ts + ", dt = " + dt.getTime());
        }
    }

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static void simpleDateFormatBug() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 20; i++) {
            //提交20个并发解析时间的任务到线程池，模拟并发环境
            threadPool.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        System.out.println(SIMPLE_DATE_FORMAT.parse("2020-01-01 11:12:13"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }
}
