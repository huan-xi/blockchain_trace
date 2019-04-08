//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.huse.trace.web.controller.home;

import cn.huse.trace.web.common.ReturnMessage;
import cn.huse.trace.web.common.Utils;
import cn.huse.trace.web.controller.LoginCache;
import cn.huse.trace.web.mapper.UserMapper;
import cn.huse.trace.web.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.*;
import java.util.Date;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(
        value = "用户接口",
        description = "用户相关操作"
)
@RequestMapping({"/user"})
public class UserController {
    @Resource
    UserMapper userMapper;
    @Value("${upload.path}")
    String uploadPath;

    public UserController() {
    }

    @PostMapping({"/token"})
    @ApiOperation("登入(获取Token)")
    public ReturnMessage getToken(@ApiParam("账号") String account, @ApiParam("密码") String password) {
        if (!StringUtils.isEmpty(account) && !StringUtils.isEmpty(password)) {
            User user = this.userMapper.selectByPrimaryKey(account);
            if (user == null) {
                return new ReturnMessage(2222, "account not exits");
            } else if (user.getPasswd().equals(Utils.passwd(password))) {
                String token = Utils.generateToken();
                LoginCache.login(token, user);
                return new ReturnMessage(1, token);
            } else {
                return new ReturnMessage(2, "login failed password(NO)");
            }
        } else {
            return new ReturnMessage(0, "args not is empty");
        }
    }

    @PostMapping({"/logout"})
    @ApiOperation("注销")
    public ReturnMessage logout(@RequestHeader("token") String token) {
        if (LoginCache.getUser(token) != null) {
            LoginCache.remote(token);
            return new ReturnMessage(1, "login success");
        } else {
            return new ReturnMessage(0, "not login");
        }
    }

    @GetMapping({"/info"})
    @ApiOperation("获取用户信息")
    public ReturnMessage getUserInfo(@RequestHeader("token") String token) {
        User user = LoginCache.getUser(token);
        return user != null ? new ReturnMessage(1, user) : new ReturnMessage(0, "not login");
    }

    @PostMapping({"/update"})
    @ApiOperation("修改用户信息")
    public ReturnMessage updateUserInfo(@RequestHeader("token") String token, String name, String data) {
        User user = LoginCache.getUser(token);
        if (user != null) {
            user.setName(name);
            user.setData(data);
            if (this.userMapper.updateByPrimaryKey(user) > 0) {
                return new ReturnMessage(1, "update success");
            }
        }

        return new ReturnMessage(403, "not login");
    }

    @PostMapping({"/header"})
    @ApiOperation("修改用户头像")
    public ReturnMessage updateUserHeader(@RequestHeader("token") String token, MultipartFile image) {
        User user = LoginCache.getUser(token);
        if (image != null && image.isEmpty()) return new ReturnMessage(0, "image is empty");
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
        if (user != null) {
            user.setHeaderSrc(file.getName());
            if (this.userMapper.updateByPrimaryKey(user) > 0) {
                return new ReturnMessage(1, user);
            }
        }

        return new ReturnMessage(403, "not login");
    }

    @PostMapping({"/register"})
    @ApiOperation("注册")
    public ReturnMessage register(@ApiParam("昵称") String name, @ApiParam("账号") String account, @ApiParam("密码") String password) {
        User user = new User();
        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(account) && !StringUtils.isEmpty(password)) {
            if (this.userMapper.selectByPrimaryKey(account) != null) {
                return new ReturnMessage(2222, "account is exits");
            } else {
                user.setCreatedTime(new Date());
                user.setPasswd(Utils.passwd(password));
                user.setAccount(account);
                user.setName(name);
                this.userMapper.insert(user);
                return new ReturnMessage(1, "register success");
            }
        } else {
            return new ReturnMessage(0, "参数不能为空");
        }
    }
}
