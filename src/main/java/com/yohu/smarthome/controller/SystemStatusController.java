package com.yohu.smarthome.controller;

import com.yohu.smarthome.bean.base.BaseResponse;
import com.yohu.smarthome.bean.system.SystemStatus;
import com.yohu.smarthome.util.SystemStatusUtils;

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
@RequestMapping("sys")
@Api(value = "系统状态接口", produces = "application/json", tags = "系统状态接口")
public class SystemStatusController {

    @GetMapping("/list")
    @ApiOperation(value = "查询状态", notes = "查询地址", consumes = "application/json")
    public BaseResponse<SystemStatus> list() {
        BaseResponse<SystemStatus> response = new BaseResponse<>();
        response.setCode("0000");
        response.setMsg(SystemStatusUtils.getSystemStatus());
        response.setContent("查询成功");
        return response;
    }
}
