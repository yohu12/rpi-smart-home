package com.yohu.smarthome.controller;

import com.yohu.smarthome.bean.base.BaseResponse;
import com.yohu.smarthome.bean.req.PhotoAddReq;
import com.yohu.smarthome.bean.req.PhotoDeleteReq;
import com.yohu.smarthome.entity.Photo;
import com.yohu.smarthome.service.PhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("photo")
@Api(value = "相片接口", produces = "application/json", tags = "相片接口")
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @GetMapping("/list")
    @ApiOperation(value = "相片列表", notes = "相片列表", consumes = "application/json")
    public BaseResponse<List<Photo>> list() {

        BaseResponse<List<Photo>> response = new BaseResponse<>();
        List<Photo> photoList = photoService.findAll();

        response.setContent("查询成功");
        response.setContent("0000");
        response.setMsg(photoList);
        return response;
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加相片", notes = "添加相片", consumes = "application/json")
    public BaseResponse<List<Photo>> add(@RequestBody PhotoAddReq photoAddReq) {

        List<Photo> photoList = new ArrayList<>();
        for (String url : photoAddReq.getUrlList()) {
            Photo photo = new Photo();
            photo.setPhotoUrl(url);
            photoList.add(photo);
        }

        BaseResponse<List<Photo>> response = new BaseResponse<>();
        List<Photo> photoListResp = photoService.save(photoList);

        response.setContent("添加成功");
        response.setCode("0000");
        response.setMsg(photoListResp);
        return response;
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除相片", notes = "删除相片", consumes = "application/json")
    public BaseResponse delete(@RequestBody PhotoDeleteReq photoDeleteReq) {

        BaseResponse response = new BaseResponse<>();
        photoService.deleteByUrl(photoDeleteReq.getUrlList());

        response.setContent("删除成功");
        response.setContent("0000");
        return response;
    }
}
