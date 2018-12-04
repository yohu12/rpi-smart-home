package com.yohu.smarthome.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "photo")
public class Photo extends BaseEntity {
    /**
     * 相片地址
     */
    private String photoUrl;
    /**
     * 排序
     */
    private Integer sortNum;
}
