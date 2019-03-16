package cn.huse.trace.web.response.model;

import cn.huse.trace.sdk.trace.ChaincodeManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.swagger.annotations.ApiModelProperty;
import org.hyperledger.fabric.protos.ledger.rwset.kvrwset.KvRwset;
import org.hyperledger.fabric.sdk.BlockInfo;
import org.hyperledger.fabric.sdk.TxReadWriteSetInfo;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: huanxi
 * @date: 2019/3/16 0:07
 */
public class EnvelopeModel {
    @ApiModelProperty("信封类型")
    private BlockInfo.EnvelopeType type;
    @ApiModelProperty("操作通道")
    private String channelId;
    @ApiModelProperty("交易ID")
    private String TransactionID;
    @ApiModelProperty("验证状态码")
    private byte ValidationCode;
    @ApiModelProperty("时间戳")
    private Comparable<Date> Timestamp;
    @ApiModelProperty("创建者")
    private BlockInfo.EnvelopeInfo.IdentitiesInfo Creator;
    @ApiModelProperty("随机值")
    private byte[] Nonce;
    @ApiModelProperty("写操作")
    private Map<String, String> ws = new HashMap<>();

    public EnvelopeModel(BlockInfo.EnvelopeInfo envelopeInfo) {
        this.type = envelopeInfo.getType();
        this.channelId = envelopeInfo.getChannelId();
        this.TransactionID = envelopeInfo.getTransactionID();
        this.ValidationCode = envelopeInfo.getValidationCode();
        this.Creator = envelopeInfo.getCreator();
        this.Timestamp = envelopeInfo.getTimestamp();
        this.Nonce = envelopeInfo.getNonce();
        if (envelopeInfo.getType() == BlockInfo.EnvelopeType.TRANSACTION_ENVELOPE) { //交易信封
            BlockInfo.TransactionEnvelopeInfo txeInfo = (BlockInfo.TransactionEnvelopeInfo) envelopeInfo;
            int txCount = txeInfo.getTransactionActionInfoCount();
            //遍历所有交易
            for (int i = 0; i < txCount; i++) {
                BlockInfo.TransactionEnvelopeInfo.TransactionActionInfo txInfo = txeInfo.getTransactionActionInfo(i);
                //读写操作
                TxReadWriteSetInfo rwsetInfo = txInfo.getTxReadWriteSet();
                if (null != rwsetInfo) {
                    for (TxReadWriteSetInfo.NsRwsetInfo nsRwsetInfo : rwsetInfo.getNsRwsetInfos()) {
                        final String namespace = nsRwsetInfo.getNamespace();
                        KvRwset.KVRWSet rws = null;
                        try {
                            rws = nsRwsetInfo.getRwset();
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                        }
                        int rs = -1;
                        assert rws != null;
                        for (KvRwset.KVRead readList : rws.getReadsList()) {
                            rs++;
                        }
                        for (KvRwset.KVWrite writeList : rws.getWritesList()) {
                            try {
                                ws.put(writeList.getKey(), ChaincodeManager.printableString(new String(writeList.getValue().toByteArray(), "UTF-8")));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        }
    }

    public BlockInfo.EnvelopeType getType() {
        return type;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public byte getValidationCode() {
        return ValidationCode;
    }

    public Comparable<Date> getTimestamp() {
        return Timestamp;
    }

    public Map<String, String> getWs() {
        return ws;
    }

    public void setWs(Map<String, String> ws) {
        this.ws = ws;
    }

    public BlockInfo.EnvelopeInfo.IdentitiesInfo getCreator() {
        return Creator;
    }

    public byte[] getNonce() {
        return Nonce;
    }

    public EnvelopeModel() {
    }

    public void setType(BlockInfo.EnvelopeType type) {
        this.type = type;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setTransactionID(String transactionID) {
        TransactionID = transactionID;
    }

    public void setValidationCode(byte validationCode) {
        ValidationCode = validationCode;
    }

    public void setTimestamp(Comparable<Date> timestamp) {
        Timestamp = timestamp;
    }

    public void setCreator(BlockInfo.EnvelopeInfo.IdentitiesInfo creator) {
        Creator = creator;
    }

    public void setNonce(byte[] nonce) {
        Nonce = nonce;
    }
}
