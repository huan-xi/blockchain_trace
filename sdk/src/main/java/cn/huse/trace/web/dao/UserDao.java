package cn.huse.trace.web.dao;

import cn.huse.trace.sdk.util.StringUtil;
import cn.huse.trace.web.entity.User;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: huanxi
 * @date: 2019/4/27 21:35
 */
@Component
public class UserDao {
    @Resource
    BaseDao baseDao;

    public boolean addUser(User user) throws DaoException {
        Map<String, String> a = baseDao.set(user.getAccount(), JSON.toJSONString(user));

        if (a.get("code").equals("error")) throw new DaoException(a.get("data"));
        return true;
    }

    public User getUser(String account) {
        String a = baseDao.get(account);
        if (a == null) return null;
        User user = null;
        try {
            user = JSON.parseObject(StringUtil.formatJsonString(a), User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
