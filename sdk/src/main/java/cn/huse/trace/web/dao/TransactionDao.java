package cn.huse.trace.web.dao;

import cn.huse.trace.web.cache.CacheHelper;
import cn.huse.trace.web.cache.RedisDao;
import cn.huse.trace.web.entity.Transaction;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: huanxi
 * @date: 2019/4/29 12:14
 */
@Component
public class TransactionDao extends BaseDao<Transaction> {
    @Resource
    RedisDao redisDao;
    @Resource
    CacheHelper cacheHelper;

    /**
     * 获取所有我的交易
     * 先查充值
     */

    public List<Transaction> getTransactionByUserId(String userKey,int page,int size) {
        String sql = "{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$regex\": \"^Transaction_.{0,}$\"\n" +
                "      },\n" +
                "      \"$or\": [\n" +
                "         {\n" +
                "            \"inId\": {\n" +
                "               \"$eq\": \"%s\"\n" +
                "            }\n" +
                "         },\n" +
                "         {\n" +
                "            \"outId\": {\n" +
                "               \"$eq\": \"%s\"\n" +
                "            }\n" +
                "         }\n" +
                "      ]\n" +
                "   },\n" +
                "   \"skip\": %d,\n" +
                "   \"limit\": %d\n" +
                "}";
        sql=String.format(sql,userKey,userKey,(page - 1) * size, size);
        return query(sql);
    }
    public List<Transaction> getTransactionByUserId(String userKey) {
        String sql = "{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$regex\": \"^Transaction_.{0,}$\"\n" +
                "      },\n" +
                "      \"$or\": [\n" +
                "         {\n" +
                "            \"inId\": {\n" +
                "               \"$eq\": \"%s\"\n" +
                "            }\n" +
                "         },\n" +
                "         {\n" +
                "            \"outId\": {\n" +
                "               \"$eq\": \"%s\"\n" +
                "            }\n" +
                "         }\n" +
                "      ]\n" +
                "   }\n" +
                "}";
        sql=String.format(sql,userKey,userKey);
        return query(sql);
    }

    //获取所有转给我的
    public List<Transaction> getTransactionInByUserId(String userKey) {
        String sql = "{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$regex\": \"^Transaction_.{0,}$\"\n" +
                "      },\n" +
                "      \"inId\": {\n" +
                "         \"$eq\": \"%s\"\n" +
                "      }\n" +
                "   }\n" +
                "}";
        sql=String.format(sql,userKey);
        return query(sql);
    }

    //获取所有我转出的交易
    public List<Transaction> getTransactionOutByUserId(String userKey) {
        String sql = "{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$regex\": \"^Transaction_.{0,}$\"\n" +
                "      },\n" +
                "      \"outId\": {\n" +
                "         \"$eq\": \"%s\"\n" +
                "      }\n" +
                "   }\n" +
                "}";
        sql=String.format(sql,userKey);
        return query(sql);
    }

    public float getBalance(String userId) {
        final float[] balance = {0};
        List<Transaction> a = getTransactionByUserId(userId);
        if (a == null) return 0;
        a.forEach(transaction -> {
            if (transaction.getInId().equals(userId))
                balance[0] -= transaction.getAmount();
            else if (transaction.getOutId().equals(userId))
                balance[0] += transaction.getAmount();
        });
        return balance[0];
    }
}
