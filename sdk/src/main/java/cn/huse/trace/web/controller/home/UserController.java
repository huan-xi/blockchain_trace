package cn.huse.trace.web.controller.home;

import cn.huse.trace.web.common.ReturnMessageMap;
import cn.huse.trace.web.common.Utils;
import cn.huse.trace.web.common.auth.jwt.JwtUserTokenUtil;
import cn.huse.trace.web.config.parsetoken.ParseToken;
import cn.huse.trace.web.dao.DaoException;
import cn.huse.trace.web.entity.User;
import cn.huse.trace.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;

/**
 * @author: huanxi
 * @date: 2019/4/27 22:25
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户接口", description = "用户接口")
public class UserController {
    @Resource
    UserService userService;
    @Value("${upload.path}")
    String uploadPath;

    @PostMapping("")
    @ApiOperation("用户注册")
    public ReturnMessageMap addUser(@Validated User user) {
        try {
            userService.addUser(user);
        } catch (DaoException e) {
            return new ReturnMessageMap(e.getMessage());
        }
        return new ReturnMessageMap("register successfully");
    }

    @PostMapping("/token")
    @ApiOperation("用户登入获取token")
    public ReturnMessageMap addToken(@Validated User user) {
        User user1 = userService.getUser(user.getAccount());
        if (user1 == null) return new ReturnMessageMap(4010, "not exist this user");
        if (user1.getPassword().equals(Utils.passwd(user.getPassword()))) {
            String token = JwtUserTokenUtil.generateToken(user1);
            return new ReturnMessageMap(token);
        }
        return new ReturnMessageMap(4011, "username or password error");
    }

    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public ReturnMessageMap getUserInfo(@ParseToken String userId) {
        return new ReturnMessageMap(userService.getUser(userId));
    }

    @PutMapping("/info")
    @ApiOperation("修改用户信息")
    public ReturnMessageMap updateUserInfo(User user, @ParseToken String userId) throws DaoException {
        user.setAccount(userId);
        userService.update(user);
        return new ReturnMessageMap("update successfully!");
    }


    @PostMapping("/header")
    @ApiOperation("修改用户头像")
    public ReturnMessageMap updateUserHeader(@ParseToken String userId, MultipartFile image) throws DaoException {
        File file;
        try {
            file = Utils.saveFile(image, uploadPath);
        } catch (Exception e) {
            return new ReturnMessageMap(5013, e.getMessage());
        }
        if (file == null) return new ReturnMessageMap("upload file failed!");
        User user = userService.getUser(userId);
        if (user != null) {
            user.setHeaderUrl(file.getName());
            userService.update(user);
        }
        return new ReturnMessageMap(user);
    }

    @GetMapping("projects")
    @ApiOperation("查看我发布的项目")
    public ReturnMessageMap getMyProjects(@ParseToken String userId) {

        return null;
    }

}
