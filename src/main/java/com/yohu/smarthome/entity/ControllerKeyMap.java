package com.yohu.smarthome.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "controller_key_map")
public class ControllerKeyMap extends BaseEntity {

    private String keyName;
    private String keyValue;

    private String code;

    @ManyToOne
    private RemoteControler remoteControler;

}
