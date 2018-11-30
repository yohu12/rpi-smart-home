package com.yohu.smarthome.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: huyong
 * @date: 2018/9/17 10:18
 * @description
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(EntityListener.class)
public class BaseEntity implements Serializable {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @ApiModelProperty(value = "创建日期")
    private Date createDate;

    /**
     * 删除标记
     */
    @Column(name="is_del",nullable=false,length=1)
    @ApiModelProperty(value = "删除标记")
    private Boolean isdel;
    /**
     * 修改日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @ApiModelProperty(value = "修改日期")
    private Date modifyDate;

}
