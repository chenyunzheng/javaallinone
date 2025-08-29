package com.chris.allinone.kafka.nativeuse.producer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class PassCarData {
    private String messageId;
    private String plateNumber;
    private String vehicleColor;
    private String vehicleType;
    private LocalDateTime passTime;
    private int speed; // km/h
    private double latitude;
    private double longitude;

    public PassCarData() {
        this.messageId = UUID.randomUUID().toString();
    }

    public PassCarData(String plateNumber, String vehicleColor, String vehicleType,
                       LocalDateTime passTime, int speed, double latitude, double longitude) {
        this.messageId = UUID.randomUUID().toString();
        this.plateNumber = plateNumber;
        this.vehicleColor = vehicleColor;
        this.vehicleType = vehicleType;
        this.passTime = passTime;
        this.speed = speed;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    @JsonProperty("message_id")
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @JsonProperty("plate_number")
    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    @JsonProperty("vehicle_color")
    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    @JsonProperty("vehicle_type")
    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @JsonProperty("pass_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getPassTime() {
        return passTime;
    }

    public void setPassTime(LocalDateTime passTime) {
        this.passTime = passTime;
    }

    @JsonProperty("speed")
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @JsonProperty("latitude")
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "VehicleData{" +
                "messageId='" + messageId + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", vehicleColor='" + vehicleColor + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", passTime=" + passTime +
                ", speed=" + speed +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}