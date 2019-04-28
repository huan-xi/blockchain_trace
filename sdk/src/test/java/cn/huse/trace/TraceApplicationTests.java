package cn.huse.trace;

import cn.huse.trace.web.dao.BaseDao;
import cn.huse.trace.web.entity.User;
import cn.huse.trace.web.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TraceApplicationTests {

    @Resource
    UserService userService;
    @Resource
    BaseDao baseDao;

    @Test
    public void addUser() {
        userService.getUser("44c0eeda178ee76730f5a399546a26ff");
    }

    @Test
    public void contextLoads() {
        User user = new User();
        user.setAccount("44c0eeda178ee76730f5a399546a262f");
        user.setName("user1");
        user.setPassword("123");
        user.setSex("男");
        if (userService.getUser(user.getAccount())!=null){
            System.out.println("已存在");
            return;
        }
//        userService.addUser(user);
    }

}
