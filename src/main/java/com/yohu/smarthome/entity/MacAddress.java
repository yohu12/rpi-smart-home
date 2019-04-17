package com.yohu.smarthome.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

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
