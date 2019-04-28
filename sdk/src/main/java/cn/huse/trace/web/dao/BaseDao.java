package cn.huse.trace.web.dao;

import cn.huse.trace.sdk.util.StringUtil;
import cn.huse.trace.web.entity.BaseEntity;
import cn.huse.trace.web.entity.User;
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
import java.util.Map;

/**
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

    protected static final MediaType JSON_HEADER = MediaType.parse("application/json; charset=utf-8");

    public boolean add(T t) throws DaoException {
        System.out.println(JSON.toJSONString(t));
        Map<String, String> a = baseDao.set(t.getId(), JSON.toJSONString(t));
        if (a.get("code").equals("error")) throw new DaoException(a.get("data"));
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
                if (!StringUtils.isEmpty(id)) {
                    T t1 = getValueBytes(id);
                    ts.add(t1);
                }
            });
        } catch (Exception e) {
            return null;
        }
        return ts;
    }
    public boolean update(User user) throws DaoException {
        Map<String, String> a = baseDao.update(user.getAccount(), JSON.toJSONString(user));
        if (a.get("code").equals("error")) throw new DaoException(a.get("data"));
        return true;
    }



    public T getValueBytes(String id) {
        final Request request = new Request.Builder()
                .url(url + id + "/valueBytes").get()
                .build();
        final Call call = okHttpClient.newCall(request);
        Response response = null;
        T t = null;
        try {
            response = call.execute();
            String res = response.body().string();
            assert res != null;
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            Type clazz = type.getActualTypeArguments()[0];
            t = JSON.parseObject(StringUtil.formatJsonString(res), clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }
}
