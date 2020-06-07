package com.hfc.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by hfc on 2020/5/13.
 */
@RestController
public class FIleController {

    @GetMapping(value = "/file/download")
    public boolean downloadFIle(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("begin --" + System.currentTimeMillis());

        String filePath = "F:\\qqqq.tar.gz";

        File file = new File(filePath);
        response.reset();
//        response.setContentType("application/octet-stream");
        response.setContentType("application/x-msdownload");
        response.setContentLength((int) file.length());
        response.setHeader( "content-disposition",
                String.format("attachment; filename=\"%s\"", file.getName()));

        OutputStream out = response.getOutputStream();
        try (FileInputStream in = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
        out.flush();

        System.out.println("end -- " + System.currentTimeMillis());
        System.out.println(file.delete());

        return true;
    }

}
