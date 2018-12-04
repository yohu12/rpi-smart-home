package com.yohu.smarthome.controller;

import com.yohu.smarthome.bean.base.BaseResponse;
import com.yohu.smarthome.config.AppConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("file")
@Api(value = "文件操作接口", produces = "application/json", tags = "文件操作")
public class FileController {
    @Autowired
    private AppConfig appConfig;

    @ApiOperation(value = "文件上传", notes = "文件上传", consumes = "application/json")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public BaseResponse<String> upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {

        BaseResponse<String> response = new BaseResponse<>();

        String filePath = appConfig.getUploadPath();

        File dir = new File(filePath);
        if (!dir.exists() && !dir.mkdirs()) {
            response.setCode("0001");
            response.setContent("上传失败");

            return response;
        }
        //file.isEmpty(); 判断图片是否为空
        //file.getSize(); 图片大小进行判断
        String name = request.getParameter("name");
        System.out.println("用户名：" + name);

        // 获取文件名
        String fileName = file.getOriginalFilename();
        System.out.println("上传的文件名为：" + fileName);

        // 获取文件的后缀名,比如图片的jpeg,png
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("上传的后缀名为：" + suffixName);

        // 文件上传后的路径
        fileName = UUID.randomUUID() + suffixName;
        System.out.println("转换后的名称:" + fileName);


        File dest = new File(filePath + fileName);

        try {
            file.transferTo(dest);

            response.setCode("0000");
            response.setContent("上传成功");
            response.setMsg(fileName);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return response;
    }

}
