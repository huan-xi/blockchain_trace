package cn.huse.trace.web.dao;

import cn.huse.trace.sdk.trace.ChaincodeManager;
import cn.huse.trace.sdk.trace.FabricManager;
import cn.huse.trace.web.common.QueryResult;
import com.alibaba.fastjson.JSON;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author: huanxi
 * @date: 2019/4/27 21:47
 */
@Component
public class FabricDao {
    ChaincodeManager fabricManager;

    public QueryResult set(String key, Object object) {
        try {
            return fabricManager.invoke("set", new String[]{key, JSON.toJSONString(object)});
        } catch (InvalidArgumentException | ProposalException | InterruptedException | ExecutionException | TimeoutException | NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException | CryptoException | TransactionException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public QueryResult update(String key, Object object) {
        try {
            return fabricManager.invoke("update", new String[]{key, JSON.toJSONString(object)});
        } catch (InvalidArgumentException | ProposalException | InterruptedException | ExecutionException | TimeoutException | NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException | CryptoException | TransactionException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String get(String key) {
        try {
            Map<String, String> a = fabricManager.query("get", new String[]{key});
            if (a.get("code").equals("error")) return null;
            return a.get("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public QueryResult delete(String key) {
        try {
            return fabricManager.invoke("delete", new String[]{key});
        } catch (InvalidArgumentException | ProposalException | InterruptedException | ExecutionException | TimeoutException | NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException | CryptoException | TransactionException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Autowired
    public void setFabricManager(ChaincodeManager fabricManager) {
        this.fabricManager = FabricManager.getInstance().getManager();
    }
}
