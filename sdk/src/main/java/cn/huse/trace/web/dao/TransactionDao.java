package cn.huse.trace.web.dao;

import cn.huse.trace.web.cache.CacheHelper;
import cn.huse.trace.web.cache.RedisDao;
import cn.huse.trace.web.entity.Transaction;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: huanxi
 * @date: 2019/4/29 12:14
 */
@Component
public class TransactionDao extends BaseDao<Transaction> {
    private static final String SELECTOR_BY_USER_ID = "{\"selector\": {\"_id\": {\"$regex\": \"^Transaction_.{0,}%s.{0,}$\"}}}";
    private static final String KEY_USER_RANSACTION = "user_transaction";
    @Resource
    RedisDao redisDao;
    @Resource
    CacheHelper cacheHelper;

    //获取所有我的交易
    public List<Transaction> getTransactionByUserId(String userKey) {
        List a = null;
        if (cacheHelper.isKeyAble(KEY_USER_RANSACTION + userKey)) {
            //读取缓存
            a = redisDao.lGet(KEY_USER_RANSACTION + userKey, 0, -1);
            if (a != null && a.size() > 0) a = (List) a.get(0);
        }
        if (a == null) {
            String sql = String.format(SELECTOR_BY_USER_ID, userKey);
            a = query(sql);
            redisDao.lSet(KEY_USER_RANSACTION + userKey, a);
            cacheHelper.setKeyAble(KEY_USER_RANSACTION + userKey, true);
        }
        return a;
    }

    //获取所有转给我的
    public List<Transaction> getTransactionInByUserId(String userKey) {
        List<Transaction> in = new ArrayList<>();
        List<Transaction> a = getTransactionByUserId(userKey);
        a.forEach(transaction -> {
            if (transaction.getInId().equals(userKey))
                in.add(transaction);
        });
        return in;
    }

    //获取所有我转出的交易
    public List<Transaction> getTransactionOutByUserId(String userKey) {
        List<Transaction> out = new ArrayList<>();
        List<Transaction> a = getTransactionByUserId(userKey);
        a.forEach(transaction -> {
            if (transaction.getOutId().equals(userKey))
                out.add(transaction);
        });
        return out;
    }

    public float getBalance(String userId) {
        final float[] balance = {0};
        List<Transaction> a = getTransactionByUserId(userId);
        a.forEach(transaction -> {
            if (transaction.getInId().equals(userId))
                balance[0] -= transaction.getAmount();
            else if (transaction.getOutId().equals(userId))
                balance[0] += transaction.getAmount();
        });
        return balance[0];
    }
}
