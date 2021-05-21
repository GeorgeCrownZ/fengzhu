package com.soft.web.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

@Component
public class DownloadImageUtil {

    public void download(String urlString, String filename) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(5*1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf=new File("/tomcat9/webapps/upload/wechat");
        if(!sf.exists()){
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath()+"/"+filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }

    public String download(MultipartFile file, String id) throws IOException {
        String fileName=file.getOriginalFilename();

        String img = id + fileName.substring(fileName.lastIndexOf("."));

        //创建文件
        File dir = new File("/tomcat9/webapps/upload/wechat", img);
        if(!dir.exists()){
            dir.mkdirs();
        }

        //完成文件上传
        file.transferTo(dir);

        return "/upload/wechat" +  img;
    }

}
