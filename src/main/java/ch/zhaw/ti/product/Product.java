package ch.zhaw.ti.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    @Getter
    @Setter
    private String url;

    // Preis

    @Getter
    @Setter
    private Double price;


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
    private Integer salesRanking;


    // Specs

    @Getter
    @Setter
    private String driveType;

    @Getter
    @Setter
    private Integer driveCapacity;

    @Getter
    @Setter
    private Integer driveCapacitySSD;

    @Getter
    @Setter
    private String processorFamily;

    @Getter
    @Setter
    private String processorType;

    @Getter
    @Setter
    private Double processorClockFrequency;

    @Getter
    @Setter
    private Double processorClockFrequencyMax;

    @Getter
    @Setter
    private Integer processorCores;

    @Getter
    @Setter
    private Integer processorThreads;

    @Getter
    @Setter
    private Integer ramAmount;

    @Getter
    @Setter
    private Integer ramOnBoard;

    @Getter
    @Setter
    private Integer ramMax;

    @Getter
    @Setter
    private Integer ramFrequency;

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
    private Integer systemArchitecture;


    // Dimensions

    @Getter
    @Setter
    private Double deviceHeight;

    @Getter
    @Setter
    private Double deviceLength;

    @Getter
    @Setter
    private Double deviceWidth;

    @Getter
    @Setter
    private Double deviceWeight;


    // Material

    @Getter
    @Setter
    private String deviceMaterial;


    // Battery

    @Getter
    @Setter
    private Integer batteryCells;

    @Getter
    @Setter
    private String batteryType;

    @Getter
    @Setter
    private Double batteryCapacity;

    @Getter
    @Setter
    private Double batteryMaxRunTime;

    @Getter
    @Setter
    private Integer batteryPowerSupply;


    // Display

    @Getter
    @Setter
    private String displayResolutionType;

    @Getter
    @Setter
    private Integer displayResolutionHeight;

    @Getter
    @Setter
    private Integer displayResolutionWidth;

    @Getter
    @Setter
    private Double displayScale;

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
    private Double bluetoothVersion;

    @Getter
    @Setter
    private Integer ethernetPortSpeed;

    @Getter
    @Setter
    private Integer amountUSB;

    @Getter
    @Setter
    private Integer amountHDMI;

    @Getter
    @Setter
    private Integer amountJackInput;

    @Getter
    @Setter
    private Boolean dockingExists;


    // Camera

    @Getter
    @Setter
    private Boolean cameraExists;

    @Getter
    @Setter
    private Double cameraResolution;

    @Getter
    @Setter
    private Integer cameraVideoResolutionHeight;

    @Getter
    @Setter
    private Integer cameraVideoResolutionWidth;


    // Security

    @Getter
    @Setter
    private String security;

    // Optical Drive
    @Getter
    @Setter
    private String opticalDrive;

}
