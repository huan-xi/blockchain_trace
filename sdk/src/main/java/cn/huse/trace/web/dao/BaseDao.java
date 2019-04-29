package cn.huse.trace.web.dao;

import cn.huse.trace.sdk.util.StringUtil;
import cn.huse.trace.web.cache.CacheHelper;
import cn.huse.trace.web.cache.RedisDao;
import cn.huse.trace.web.common.QueryResult;
import cn.huse.trace.web.entity.BaseEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 缓存优化
 * 未做更新操作，去缓存中读取
 * 做更新，存缓存
 *
 * @author: huanxi
 * @date: 2019/4/28 13:46
 */
public class BaseDao<T extends BaseEntity> {
    @Resource
    FabricDao baseDao;
    @Value("${couchdb.url}")
    protected String url;
    @Resource
    OkHttpClient okHttpClient;
    @Resource
    CacheHelper cacheHelper;
    @Resource
    RedisDao redisDao;

    protected static final MediaType JSON_HEADER = MediaType.parse("application/json; charset=utf-8");

    public boolean add(T t) throws DaoException {
        QueryResult a = baseDao.set(t.getId(), JSON.toJSONString(t));
        if (!a.isSuccess()) throw new DaoException(a.getData());
        return true;
    }

    public T get(String id) {
        String a = baseDao.get(id);
        if (a == null) return null;
        T t = null;
        try {
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            Type clazz = type.getActualTypeArguments()[0];
            t = JSON.parseObject(StringUtil.formatJsonString(a), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) t;
    }

    public List<T> query(String sql) {
        System.out.println("执行sql语句：" + sql);
        List<T> ts = new ArrayList<>();
        RequestBody body = RequestBody.create(JSON_HEADER, sql);
        final Request request = new Request.Builder()
                .url(url + "_find").post(body)
                .build();
        final Call call = okHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            assert response != null;
            JSONObject res = null;
            res = JSON.parseObject(response.body().string());
            JSONArray t = (JSONArray) res.get("docs");
            assert t.size() > 0;
            t.forEach(idObject -> {
                String id = ((JSONObject) idObject).getString("_id");
//                baseDao.delete(id);
                if (!StringUtils.isEmpty(id)) {
                    T t1 = getValueBytes(id);
                    if (t1 != null) ts.add(t1);
                }
            });
        } catch (Exception e) {
            return null;
        }
        return ts;
    }

    public boolean update(T t) throws DaoException {
        QueryResult a = baseDao.update(t.getId(), JSON.toJSONString(t));
        if (!a.isSuccess()) throw new DaoException(a.getData());
        return true;
    }

    public void deleteAll() {
        query("{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$gt\": \"\"\n" +
                "      }\n" +
                "   }\n" +
                "}");
    }

    public T getValueBytes(String id) {
        T t = null;
        if (cacheHelper.isKeyAble(CacheHelper.VALUEBYTE + id)) {
            t = (T) redisDao.get(CacheHelper.VALUEBYTE + id);
        }
        if (t == null) {
            final Request request = new Request.Builder()
                    .url(url + id + "/valueBytes").get()
                    .build();
            final Call call = okHttpClient.newCall(request);
            Response response = null;
            try {
                response = call.execute();
                String res = response.body().string();
                assert res != null;
                ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
                Type clazz = type.getActualTypeArguments()[0];
                t = JSON.parseObject(StringUtil.formatJsonString(res), clazz);
                if (t != null) {
                    redisDao.set(CacheHelper.VALUEBYTE + id, t);
                    cacheHelper.setKeyAble(CacheHelper.VALUEBYTE + id, true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return t;
    }
}
