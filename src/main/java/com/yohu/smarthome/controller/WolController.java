package com.yohu.smarthome.controller;

import com.yohu.smarthome.bean.GpioStatusRsp;
import com.yohu.smarthome.bean.base.BaseResponse;
import com.yohu.smarthome.entity.MacAddress;
import com.yohu.smarthome.exception.UnableToWakeUpWOLNodeException;
import com.yohu.smarthome.service.RpiStatusService;
import com.yohu.smarthome.service.WolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @author huyong
 * @since 2018/11/29
 */
@RestController
@RequestMapping("rpi")
@Api(value = "远程开机接口", produces = "application/json", tags = "远程开机接口")
public class WolController {
    @Autowired
    private WolService wolService;

    @GetMapping("/wakeUpByMac/{macAddress}")
    @ApiOperation(value = "开机", notes = "通过Mac地址开机", consumes = "application/json")
    public BaseResponse wakeUpByMac(@PathVariable String macAddress) {
        BaseResponse response = new BaseResponse<>();

        try {
            wolService.wakeUP(macAddress);
        } catch (UnableToWakeUpWOLNodeException e) {
            e.printStackTrace();
            response.setCode("0001");
            response.setContent("开启失败");
            return response;
        }
        response.setCode("0000");
        response.setContent("开机命令发送成功");
        return response;
    }

    @PostMapping("/addMacAddress")
    @ApiOperation(value = "添加mac地址", notes = "添加mac地址", consumes = "application/json")
    public BaseResponse<MacAddress> addMacAddress(MacAddress macAddress) {
        BaseResponse<MacAddress> response = new BaseResponse<>();
        response.setCode("0000");
        response.setContent("开机命令发送成功");
        response.setMsg(wolService.save(macAddress));
        return response;
    }

    @GetMapping("/deleteByMacAddress/{macAddress}")
    @ApiOperation(value = "删除Mac记录", notes = "删除Mac记录", consumes = "application/json")
    public BaseResponse<Long> deleteByMacAddress(@PathVariable String macAddress) {
        BaseResponse<Long> response = new BaseResponse<>();
        response.setCode("0000");
        response.setContent("操作成功");
        response.setMsg(wolService.deleteByMacAddress(macAddress));
        return response;
    }
}
