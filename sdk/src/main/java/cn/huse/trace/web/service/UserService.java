package cn.huse.trace.web.service;

import cn.huse.trace.web.common.Utils;
import cn.huse.trace.web.dao.DaoException;
import cn.huse.trace.web.dao.UserDao;
import cn.huse.trace.web.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: huanxi
 * @date: 2019/4/27 22:25
 */
@Service
public class UserService {
    @Resource
    UserDao userDao;

    private final String USER_FLAY = "user_";

    public void addUser(User user) throws DaoException {
        user.setStatus(1);
        user.setAccount(USER_FLAY + user.getAccount());
        user.setPassword(Utils.passwd(user.getPassword()));
        userDao.add(user);
    }

    public User getUser(String account) {
//        account = USER_FLAY + account;
        return userDao.get(account);
    }

    public void update(User user) throws DaoException {
        userDao.update(user);
    }
}
