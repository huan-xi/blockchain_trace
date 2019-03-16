package cn.huse.trace;


import cn.huse.trace.sdk.trace.ChaincodeManager;
import cn.huse.trace.sdk.trace.FabricManager;
import com.google.protobuf.ByteString;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.hyperledger.fabric.sdk.BlockInfo;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author: huanxi
 * @date: 2019/3/6 23:54
 */
public class Test {

    @org.junit.Test
    public void test() throws InvalidArgumentException, NoSuchAlgorithmException, IOException, TransactionException, NoSuchProviderException, CryptoException, InvalidKeySpecException, ProposalException {
        ChaincodeManager fabricManager = FabricManager.obtain().getManager();
        try {
            fabricManager.queryBlockByHash(Hex.decodeHex(fabricManager.queryBlockByNumber(3).getPreviousHash()));
        } catch (DecoderException e) {
            e.printStackTrace();
        }
//        fabricManager.invoke()
//        byte[] b = fabricManager.getChannelInstant().queryBlockchainInfo().getCurrentBlockHash();
//        System.out.println(new String(b,"UTF-8"));
//        BlockInfo f = fabricManager.queryBlockByNumber(10);
    }
}
