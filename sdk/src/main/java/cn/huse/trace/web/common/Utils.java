//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.huse.trace.web.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class Utils {


    public static File saveFile(MultipartFile image, String uploadPath) throws Exception {
        if (image != null && image.isEmpty()) throw new Exception("image is empty");
        File file = new File(uploadPath + Utils.getUUID() + "." + image.getContentType().split("/")[1]);
        OutputStream os = null;
        InputStream in = null;
        try {
            os = new FileOutputStream(file);
            in = image.getInputStream();
            int i;//从输入流读取一定数量的字节，返回 0 到 255 范围内的 int 型字节值
            while ((i = in.read()) != -1) {
                os.write(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static String passwd(String s) {
        return DigestUtils.md5Hex("imp" + s);
    }

    public static String generateToken() {
        return DigestUtils.md5Hex("imp" + System.currentTimeMillis() + Math.random());
    }


    public static String parseToString(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static String getUUID() {
        return DigestUtils.md5Hex("imp" + System.currentTimeMillis() + Math.random());
    }
}
