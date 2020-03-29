package com.ithome.controller;


import com.ithome.utils.AliyunOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileController {

    @Autowired
    private AliyunOssUtil aliyunOssUtil;

    @RequestMapping("/file/upload")
    @ResponseBody
    public Map<String,Object> testUpload(@RequestParam("editormd-image-file")MultipartFile file, Model model){
        Map<String,Object> responseResult = new HashMap<>();
        String filename = file.getOriginalFilename();
        try {
            if(file != null){
                if(!"".equals(filename.trim())){
                    File newFile = new File(filename);
                    FileOutputStream outputStream = new FileOutputStream(newFile);
                    outputStream.write(file.getBytes());
                    outputStream.close();
                    file.transferTo(newFile);
                    String url = aliyunOssUtil.upload(newFile);
                    responseResult.put("success",1);
                    responseResult.put("message","上传成功");
                    responseResult.put("url",url);
                }
            }
        } catch (FileNotFoundException e) {
            responseResult.put("success",0);
            responseResult.put("message","上传失败");
            e.printStackTrace();
        } catch (IOException e) {
            responseResult.put("success",0);
            responseResult.put("message","上传失败");
            e.printStackTrace();
        }
        return responseResult;
    }
}

