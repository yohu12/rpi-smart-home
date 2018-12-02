package com.yohu.smarthome.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "remote_controller")
public class RemoteControler extends BaseEntity{


    private String controllerName;

    private String location;
}
