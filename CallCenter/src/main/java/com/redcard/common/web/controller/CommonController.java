package com.redcard.common.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;

@Controller("commonController")
@RequestMapping(value = "/common")
public class CommonController {

    private static Logger logger = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping(value = "download")
    public void download(String filepath, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        File file = new File(URLDecoder.decode(filepath));
        response.setHeader("Content-Disposition", "attachment;fileName=" + file.getName());
        try {
            InputStream inputStream = new FileInputStream(file);
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[1024];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

