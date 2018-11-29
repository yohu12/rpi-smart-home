package com.yohu.smarthome.controller;

import com.yohu.smarthome.bean.base.BaseResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author huyong
 * @since 2018/11/29
 */
@RestController
@RequestMapping("rpi")
@Api(value = "树莓派状态接口", produces = "application/json", tags = "树莓派状态接口")
public class RpiStatusController {
    @GetMapping("/status")
    @ApiOperation(value = "查询状态", notes = "查询状态", consumes = "application/json")
    public BaseResponse getRpiStatus() {
        BaseResponse response = new BaseResponse();
        response.setCode("0000");
        response.setContent("查询成功");
        return response;
    }
}
