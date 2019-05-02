package cn.huse.trace.web.cache;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: huanxi
 * @date: 2019/4/29 13:30
 */
@Component
public class CacheHelper {
    public static final String KEY = "key";
    public static final String SQL = "sql";
    public static String Balance = "Balance";
    @Resource
    RedisDao redisDao;
    private final static String KEY_CANAVLE = "key_can_able_"; //缓存是否能用
    public final static String VALUEBYTE = "valueByte"; //valueByte

    //检查键是否发生改变
    public boolean isKeyAble(String key) {
        Object a = redisDao.get(KEY_CANAVLE + key);
        return a != null && (boolean) a;
    }

    /**
     * 设置键发生改变
     *
     * @param key
     * @param change true 为缓存可用
     * @return
     */
    public boolean setKeyAble(String key, boolean change) {
        return redisDao.set(KEY_CANAVLE + key, change);
    }
}
