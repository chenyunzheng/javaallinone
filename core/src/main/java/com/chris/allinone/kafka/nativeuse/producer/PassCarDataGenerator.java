package com.chris.allinone.kafka.nativeuse.producer;

import java.time.LocalDateTime;
import java.util.Random;

public class PassCarDataGenerator {
    private static final String[] PLATE_PREFIXES = {
        "京", "沪", "津", "渝", "冀", "晋", "辽", "吉", "黑",
        "苏", "浙", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘",
        "粤", "琼", "川", "贵", "云", "陕", "甘", "青", "蒙",
        "桂", "宁", "新", "藏"
    };
    
    private static final String[] PLATE_CHARS = {
        "A", "B", "C", "D", "E", "F", "G", "H", "J", "K",
        "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V",
        "W", "X", "Y", "Z"
    };
    
    private static final String[] VEHICLE_COLORS = {
        "蓝色", "黄色", "黑色", "白色", "绿色", "红色", "灰色", "棕色"
    };
    
    private static final String[] VEHICLE_TYPES = {
        "小型汽车", "大型汽车", "使馆汽车", "领馆汽车", "境外汽车",
        "外籍汽车", "两三轮摩托车", "轻便摩托车", "使馆摩托车", "领馆摩托车",
        "境外摩托车", "外籍摩托车", "农用运输车", "拖拉机", "挂车",
        "教练汽车", "教练摩托车", "试验汽车", "试验摩托车", "临时入境汽车",
        "临时入境摩托车", "临时行驶车", "警用汽车", "警用摩托"
    };
    
    private static final Random random = new Random();
    private LocalDateTime passTime;
    
    public PassCarDataGenerator() {
        this.passTime = LocalDateTime.now().minusMinutes(30);
    }
    
    public PassCarData generateVehicleData() {
        // 生成随机车牌号
        String platePrefix = PLATE_PREFIXES[random.nextInt(PLATE_PREFIXES.length)];
        String plateChar = PLATE_CHARS[random.nextInt(PLATE_CHARS.length)];
        String plateNumber = String.format("%s%s%05d", platePrefix, plateChar, random.nextInt(100000));
        
        // 生成随机车辆颜色
        String vehicleColor = VEHICLE_COLORS[random.nextInt(VEHICLE_COLORS.length)];
        
        // 生成随机车辆类型
        String vehicleType = VEHICLE_TYPES[random.nextInt(VEHICLE_TYPES.length)];
        
        // 生成过车时间（顺序递增，每次增加1-10秒）
        int secondsToAdd = random.nextInt(10) + 1;
        passTime = passTime.plusSeconds(secondsToAdd);
        
        // 生成随机速度 (0-120 km/h)
        int speed = random.nextInt(121);
        
        // 生成随机经纬度（以北京为中心的范围）
        double baseLat = 39.9042;  // 北京纬度
        double baseLng = 116.4074; // 北京经度
        double latitude = baseLat + (random.nextDouble() - 0.5) * 0.5;  // ±0.25度范围
        double longitude = baseLng + (random.nextDouble() - 0.5) * 0.5; // ±0.25度范围
        
        return new PassCarData(
            plateNumber, 
            vehicleColor, 
            vehicleType,
            passTime,
            speed, 
            latitude, 
            longitude
        );
    }
}