package cn.huse.trace.web.dao;

import cn.huse.trace.web.cache.CacheHelper;
import cn.huse.trace.web.cache.RedisDao;
import cn.huse.trace.web.common.QueryResult;
import cn.huse.trace.web.common.Utils;
import cn.huse.trace.web.entity.BaseEntity;
import cn.huse.trace.web.entity.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
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
@Slf4j
public class BaseDao<T extends BaseEntity> {
    @Resource
    FabricDao fabricDao;
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
        QueryResult a = fabricDao.set(t.getId(), Utils.parseToString(t));
        if (!a.isSuccess()) throw new DaoException(a.getData());
        return true;
    }

    //从区块链中查询
    public T get(String id) {
        T t = null;
        if (cacheHelper.isKeyAble(CacheHelper.KEY + id)) {
            t = (T) redisDao.get(CacheHelper.KEY + id);
        }
        if (t == null) {
            //查valueByteId
            String a = fabricDao.get(id);
            if (a == null) return null;
            try {
                ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
                Type clazz = type.getActualTypeArguments()[0];
                t = JSON.parseObject(a, clazz);
                redisDao.set(CacheHelper.KEY + id, t);
                cacheHelper.setKeyAble(CacheHelper.KEY + id, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    /**
     * 复杂查询，先查Id，在查valueByte
     * http 请求
     * 缓存valueByte
     */
/*
    public List<String> queryId(String sql) {
        List<String> result = new ArrayList<>();
        log.info("执行sql" + sql);
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
                if (!StringUtils.isEmpty(id)) {
                    result.add(id);
                }
            });
            redisDao.lSet(CacheHelper.KEY + sql, result);
            cacheHelper.setKeyAble(CacheHelper.KEY + sql, true);
        } catch (Exception e) {
            return null;
        }
        return result;
    }*/
    public List<T> query(String sql) {
        List<T> ts = new ArrayList<>();
//        log.info("执行sql" + sql);
        RequestBody body = RequestBody.create(JSON_HEADER, sql);
        final Request request = new Request.Builder()
                .url(url + "_find").post(body)
                .build();
        final Call call = okHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            assert response != null;
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class clazz = (Class) type.getActualTypeArguments()[0];
            ts = ((JSONArray) JSON.parseObject(response.body().string()).get("docs")).toJavaList(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ts;
    }

    public boolean update(T t) throws DaoException {
        QueryResult a = fabricDao.update(t.getId(), Utils.parseToString(t));
        if (!a.isSuccess()) throw new DaoException(a.getData());
        if (!t.getId().startsWith(User.FLAG))
            cacheHelper.setKeyAble(CacheHelper.VALUEBYTE + t.getId(), false);
        cacheHelper.setKeyAble(CacheHelper.KEY + t.getId(), false);
        return true;
    }

    public void deleteAll() {
        String sql = "{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$regex\": \".{0,}\"\n" +
                "      }\n" +
                "   }\n" +
                "}";
        RequestBody body = RequestBody.create(JSON_HEADER, sql);

        final Request request = new Request.Builder()
                .url(url + "_find").post(body)
                .build();
        final Call call = okHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            assert response != null;
            JSONArray a = ((JSONArray) JSON.parseObject(response.body().string()).get("docs"));
            a.forEach(e -> {
                String id = ((JSONObject) e).getString("_id");
                fabricDao.delete(id);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public T getValueBytes(String id) {
        String key = CacheHelper.VALUEBYTE + id;
        T t = null;
        if (cacheHelper.isKeyAble(key)) {
            try {
                t = (T) redisDao.get(key);
            } catch (Exception e) {
                e.printStackTrace();
                cacheHelper.setKeyAble(key, false);
            }
        }
        if (t == null) {
            log.info("http query");
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
//                t = JSON.parseObject(StringUtil.formatJsonString(res), clazz);
                if (t != null) {
                    redisDao.set(key, t);
                    cacheHelper.setKeyAble(key, true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return t;
    }*/
}
