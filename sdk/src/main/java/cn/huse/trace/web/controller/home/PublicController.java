package cn.huse.trace.web.controller.home;

import cn.huse.trace.web.common.ReturnMessageMap;
import cn.huse.trace.web.common.Utils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author: huanxi
 * @date: 2019/3/15 23:26
 */
@RestController
@Api(value = "公开接口", description = "公开接口")
@RequestMapping("/public")
public class PublicController {
    @Value("${upload.path}")
    String uploadPath;

    @PostMapping("upload")
    public ReturnMessageMap upload(MultipartFile image) {
        File file = null;
        try {
            file = Utils.saveFile(image, uploadPath);
        } catch (Exception e) {
            return new ReturnMessageMap(5013, e.getMessage());
        }
        if (file == null) return new ReturnMessageMap(5014, "save file failed");
        return new ReturnMessageMap(file.getName());
    }
}
