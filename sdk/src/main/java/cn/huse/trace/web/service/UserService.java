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

    public void addUser(User user) throws DaoException {
        user.setStatus(1);
        user.setPassword(Utils.passwd(user.getPassword()));
        userDao.addUser(user);
    }
    public User getUser(String account) {
        return userDao.getUser(account);
    }
}
