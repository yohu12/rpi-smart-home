package com.yohu.smarthome.bean.system;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author huyong
 * @since 2018/12/3
 */
@Getter
@Setter
public class SystemStatus {

    /**
     * CPU使用率
     */
    private String cpuFreq;

    /**
     * CPU温度
     */
    private String cpuTemp;

    /**
     * 总内存
     */
    private String totalMemory;

    /**
     * 已用内存
     */
    private String usedMemory;

    /**
     * 运行时间
     */
    private String upTime;
    private String IPAddress;

    private String macAddress;

    private String powerStatus;

    private String powerBattery;

    /**
     * 网卡信息
     */
    private List<SystemNet> systemNetList;

    /**
     * 硬盘信息
     */
    private List<SystemStorage> systemStorageList;
}
