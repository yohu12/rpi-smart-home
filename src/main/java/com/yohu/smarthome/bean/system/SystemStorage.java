package com.yohu.smarthome.bean.system;

import lombok.Getter;
import lombok.Setter;

/**
 * @author huyong
 * @since 2018/12/3
 */
@Getter
@Setter
public class SystemStorage {

    /**
     * 磁盘名字
     */
    private String driverName;

    /**
     * 总容量
     */
    private String totalStorage;

    /**
     * 已用大小
     */
    private String usedStorage;

    /**
     * 使用率
     */
    private String usedFreq;
}
