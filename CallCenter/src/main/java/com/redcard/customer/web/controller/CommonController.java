package com.redcard.customer.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/customer/common")
public class CommonController {
    private static Logger logger = LoggerFactory.getLogger(CommonController.class);
    
    @RequestMapping(value = "download")
    public void download(String filepath, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        File file = new File(filepath);
        String name = new String(file.getName().getBytes("GB2312"), "ISO_8859_1");
        response.setHeader("Content-Disposition", "attachment;fileName=" + name);
        InputStream inputStream = null;
        OutputStream os = null;
        try {
            inputStream = new FileInputStream(file);
            os = response.getOutputStream();
            byte[] b = new byte[1024];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (os != null) {
            	os.close();
            }
        }
    }
}
