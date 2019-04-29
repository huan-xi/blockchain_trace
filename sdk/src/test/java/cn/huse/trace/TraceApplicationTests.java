package cn.huse.trace;

import cn.huse.trace.web.dao.DaoException;
import cn.huse.trace.web.dao.ProjectDao;
import cn.huse.trace.web.dao.TransactionDao;
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
    ProjectDao baseDao;
    @Resource
    TransactionDao transactionDao;

    @Test
    public void queryTransaction(){
//        List<Transaction> a = transactionDao.getTransactionByUserId("user_1234ddd");
        System.out.println(transactionDao.getBalance("user_1234ddd"));
    }

    @Test
    public void query() {
        baseDao.deleteAll();
//        Project b = baseDao.getValueBytes("project_1234_1ccd02df63e99f7ded297451c347adb2");
//        System.out.println(b.toString());
//        List<Project> a = baseDao.queryByUserId("1234");
//        List<Project> a = baseDao.all(1, 12);
    }

    @Test
    public void addUser() {
        userService.getUser("44c0eeda178ee76730f5a399546a26ff");
    }

    @Test
    public void contextLoads() {
        User user = new User();
        user.setAccount("44c0eeda178ee767w0f5a399546a262f");
        user.setName("user1");
        user.setPassword("123");
        user.setSex("男");
        try {
            userService.addUser(user);
        } catch (DaoException e) {
            e.printStackTrace();
        }
       /* if (userService.getUser(user.getAccount())!=null){
            System.out.println("已存在");
            return;
        }*/
//        userService.add(user);
    }

}
