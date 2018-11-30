package com.yohu.smarthome.bean.base;

import lombok.Getter;
import lombok.Setter;

/**
 * @author huyong
 * @since 2018/11/29
 */
@Getter
@Setter
public class BaseResponse<T> {
    private String code;
    private String content;

    private T msg;

}
