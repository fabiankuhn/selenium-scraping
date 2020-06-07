package ch.zhaw.ti.productRaw;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductRaw {
    @Id
    @Getter
    @Setter
    private String url;

    // Preis

    @Getter
    @Setter
    private String price;


    // General Info

    @Getter
    @Setter
    private String manufacturer;

    @Getter
    @Setter
    private String productType;

    @Getter
    @Setter
    private String operationSystem;

    @Getter
    @Setter
    private String operationSystemWindows;

    @Getter
    @Setter
    private String operationSystemMac;

    @Getter
    @Setter
    private String salesRanking;


    // Specs

    @Getter
    @Setter
    private String driveType;

    @Getter
    @Setter
    private String driveCapacity;

    @Getter
    @Setter
    private String driveCapacitySSD;

    @Getter
    @Setter
    private String processorFamily;

    @Getter
    @Setter
    private String processorType;

    @Getter
    @Setter
    private String processorClockFrequency;

    @Getter
    @Setter
    private String processorClockFrequencyMax;

    @Getter
    @Setter
    private String processorCores;

    @Getter
    @Setter
    private String processorThreads;

    @Getter
    @Setter
    private String ramAmount;

    @Getter
    @Setter
    private String ramOnBoard;

    @Getter
    @Setter
    private String ramMax;

    @Getter
    @Setter
    private String ramFrequency;

    @Getter
    @Setter
    private String graphicsPerformance;

    @Getter
    @Setter
    private String graphicCard;

    @Getter
    @Setter
    private String graphicCardFamily;

    @Getter
    @Setter
    private String systemArchitecture;


    // Dimensions

    @Getter
    @Setter
    private String deviceHeight;

    @Getter
    @Setter
    private String deviceLength;

    @Getter
    @Setter
    private String deviceWidth;

    @Getter
    @Setter
    private String deviceWeight;


    // Material

    @Getter
    @Setter
    private String deviceMaterial;


    // Battery

    @Getter
    @Setter
    private String batteryCells;

    @Getter
    @Setter
    private String batteryType;

    @Getter
    @Setter
    private String batteryCapacity;

    @Getter
    @Setter
    private String batteryMaxRunTime;

    @Getter
    @Setter
    private String batteryPowerSupply;


    // Display

    @Getter
    @Setter
    private String displayResolutionType;

    @Getter
    @Setter
    private String displayResolution;

    @Getter
    @Setter
    private String displayScale;

    @Getter
    @Setter
    private String displayTechnology;


    // Connectivity

    @Getter
    @Setter
    private String wLanController;

    @Getter
    @Setter
    private String wLanType;

    @Getter
    @Setter
    private String bluetoothVersion;

    @Getter
    @Setter
    private String ethernetPortSpeed;

    @Getter
    @Setter
    private String amountUSB;

    @Getter
    @Setter
    private String amountHDMI;

    @Getter
    @Setter
    private String amountJackInput;

    @Getter
    @Setter
    private String amountDocking;


    // Camera

    @Getter
    @Setter
    private String cameraExists;

    @Getter
    @Setter
    private String cameraResolution;

    @Getter
    @Setter
    private String cameraVideoResolution;


    // Security

    @Getter
    @Setter
    private String security;

    // Optical Drive
    @Getter
    @Setter
    private String opticalDrive;

}
