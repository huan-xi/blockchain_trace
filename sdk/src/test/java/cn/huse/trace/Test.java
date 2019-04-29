package cn.huse.trace;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author: huanxi
 * @date: 2019/3/6 23:54
 */
public class Test {
    public static final MediaType JSON_HEADER = MediaType.parse("application/json; charset=utf-8");

    @org.junit.Test
    public void testQuery() {
        String url = "http://120.77.34.74:5984/mychannel_funding/_find";
        OkHttpClient okHttpClient = new OkHttpClient();
        String pattern = "project_1234";
        String selector = "{\"selector\": {\"_id\": {\"$regex\": \"%s\"}}}";
        String sql = String.format(selector, pattern);
        RequestBody body = RequestBody.create(JSON_HEADER, sql);
        final Request request = new Request.Builder()
                .url(url).post(body)
                .build();
        final Call call = okHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert response != null;
        JSONObject res = null;
        try {
            res = JSON.parseObject(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray t = (JSONArray) res.get("docs");
        assert t.size() > 0;
        String id = ((JSONObject) t.get(0)).getString("_id");
        System.out.println(id);
    }

    @org.junit.Test
    public void test() throws InvalidArgumentException, NoSuchAlgorithmException, IOException, TransactionException, NoSuchProviderException, CryptoException, InvalidKeySpecException, ProposalException {
    }
}
