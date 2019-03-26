package cn.huse.trace.web.response.model;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.codec.binary.Hex;
import org.hyperledger.fabric.sdk.BlockInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: huanxi
 * @date: 2019/3/15 13:33
 */
@ApiModel("区块信息")
public class BlockInfoModel {
    @ApiModelProperty("区块号")
    private Long blockNumber;
    @ApiModelProperty("区块数据Hash")
    private String dataHash;
    @ApiModelProperty("前一区块Hash")
    private String previousHash;
    @ApiModelProperty("大小")
    private int size;
    @ApiModelProperty("封装信息")
    private List<EnvelopeModel> envelopes = new ArrayList();

    public BlockInfoModel(BlockInfo blockInfo) {
        this.blockNumber = blockInfo.getBlockNumber();
        this.dataHash = Hex.encodeHexString(blockInfo.getDataHash());
        this.previousHash = Hex.encodeHexString(blockInfo.getPreviousHash());
        //处理分装信息
        blockInfo.getEnvelopeInfos().forEach(envelopeInfo -> {
            envelopes.add(new EnvelopeModel(envelopeInfo));
        });
        this.size = JSON.toJSONString(this).getBytes().length*1024;
    }

    public BlockInfoModel() {
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<EnvelopeModel> getEnvelopes() {
        return envelopes;
    }

    public void setEnvelopes(List<EnvelopeModel> envelopes) {
        this.envelopes = envelopes;
    }

    public Long getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Long blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getDataHash() {
        return dataHash;
    }

    public void setDataHash(String dataHash) {
        this.dataHash = dataHash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }
}
