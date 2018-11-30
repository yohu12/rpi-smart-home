package com.yohu.smarthome.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author: huyong
 * @date: 2018/11/30 17:30
 */
@Getter
@Setter
@Entity
@Table(name = "mac_address")
public class MacAddress extends BaseEntity {

    private String name;
    @Column(unique = true)
    private String address;
    private String ip;
}
