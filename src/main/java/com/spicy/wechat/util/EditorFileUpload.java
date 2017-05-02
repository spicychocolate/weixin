package com.spicy.wechat.util;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class EditorFileUpload{
    
    @Value("${site.imgs.path}")
    private String rootPath;
    
    @RequestMapping(value = "/editor/fileUpload", method = RequestMethod.POST)
    @ResponseBody
    public List<String> fileUpload(MultipartHttpServletRequest multipartRequest) throws IllegalStateException, IOException {
        MultipartFile multipartFile = multipartRequest.getFile("myFileName");
        String newFileName = "ED" + "_" + System.currentTimeMillis() + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        String filePath = rootPath + newFileName;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        multipartFile.transferTo(file);
        List<String> list = new LinkedList<String>();
        list.add(newFileName);
        return list;
    }
}
