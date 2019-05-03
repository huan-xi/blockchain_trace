package cn.huse.trace.web.controller.home;

import cn.huse.trace.web.common.ReturnMessageMap;
import cn.huse.trace.web.common.Utils;
import cn.huse.trace.web.common.auth.jwt.JwtUserTokenUtil;
import cn.huse.trace.web.config.parsetoken.ParseToken;
import cn.huse.trace.web.dao.DaoException;
import cn.huse.trace.web.entity.Transaction;
import cn.huse.trace.web.entity.User;
import cn.huse.trace.web.entity.view.UserView;
import cn.huse.trace.web.service.ProjectService;
import cn.huse.trace.web.service.TransactionService;
import cn.huse.trace.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

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
    @Resource
    ProjectService projectService;
    @Resource
    TransactionService transactionService;
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
        User user1 = userService.getUserByAccount(user.getAccount());
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
        User user = userService.getUser(userId);
        return new ReturnMessageMap(new UserView(user, transactionService.getBalance(userId)));
    }

    @PutMapping("/info")
    @ApiOperation("修改用户信息(性别和名字)")
    public ReturnMessageMap updateUserInfo(User user, @ParseToken String userId) throws DaoException {
        user.setAccount(userId);
        User t = userService.getUser(userId);
        if (!StringUtils.isEmpty(user.getSex())) t.setSex(user.getSex());
        if (!StringUtils.isEmpty(user.getName())) t.setName(user.getName());
        if (!StringUtils.isEmpty(user.getPhone())) t.setPhone(user.getPhone());
        if (!StringUtils.isEmpty(user.getDesc())) t.setDesc(user.getDesc());
        userService.update(t);
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
        return new ReturnMessageMap("上传成功");
    }

    @GetMapping("projects")
    @ApiOperation("查看我发布的项目")
    public ReturnMessageMap getMyProjects(@ParseToken String userId) {
        return new ReturnMessageMap(projectService.queryByUserId(userId));
    }

    @PostMapping("charge")
    @ApiOperation("模拟充值")
    public ReturnMessageMap charge(@ParseToken String userId, @ApiParam("充值金额") float amount) {
        if (amount <= 0) return new ReturnMessageMap(4011, "amount must be gt 0");
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setInId("bank");
        transaction.setOutId(userId);
        transaction.setDesc("从银行卡充值");
        try {
            transactionService.addTransaction(transaction);
        } catch (DaoException e) {
            return new ReturnMessageMap(5021, e.getMessage());
        }
        return new ReturnMessageMap("ok");
    }

    @PostMapping("withdraw")
    @ApiOperation("模拟提现")
    public ReturnMessageMap withdraw(@ParseToken String userId, @ApiParam("充值金额") float amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setInId(userId);
        transaction.setOutId("bank");
        transaction.setDesc("体现");
        try {
            transactionService.addTransaction(transaction);
        } catch (DaoException e) {
            return new ReturnMessageMap(5021, e.getMessage());
        }
        return new ReturnMessageMap("ok");
    }

    @PostMapping("transfer")
    @ApiOperation("模拟投资众筹")
    public ReturnMessageMap transfer(@ParseToken String userId, @ApiParam("转账金额") float amount, @ApiParam("众筹项目Id") String projectId) {
        if (projectService.getProject(projectId) == null) return new ReturnMessageMap(4030, "not exists this project!");
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setInId(userId);
        transaction.setOutId(projectId);
        transaction.setDesc("众筹");
        try {
            transactionService.addTransaction(transaction);
        } catch (DaoException e) {
            return new ReturnMessageMap(5021, e.getMessage());
        }
        return new ReturnMessageMap("ok");
    }

    @GetMapping("balance")
    @ApiOperation("查询余额")
    public ReturnMessageMap getBalance(@ParseToken String userId) {
        return new ReturnMessageMap(transactionService.getBalance(userId));
    }

    @GetMapping("transaction")
    @ApiOperation("获取我的所有交易")
    public ReturnMessageMap getTransaction(@ParseToken String userId, int page, int size) {
        return new ReturnMessageMap(transactionService.getTransactionByUserId(userId, page, size));
    }

    @GetMapping("transaction/in")
    @ApiOperation("获取转给我的所有交易")
    public ReturnMessageMap getTransactionIn(@ParseToken String userId) {
        return new ReturnMessageMap(transactionService.getTransactionInByUserId(userId));
    }

    @GetMapping("transaction/out")
    @ApiOperation("获取我转出的所有交易")
    public ReturnMessageMap getTransactionOut(@ParseToken String userId) {
        return new ReturnMessageMap(transactionService.getTransactionOutByUserId(userId));
    }

}
