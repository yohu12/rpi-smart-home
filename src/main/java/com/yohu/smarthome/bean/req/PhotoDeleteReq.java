package com.yohu.smarthome.bean.req;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PhotoDeleteReq {
    List<String> urlList;
}
