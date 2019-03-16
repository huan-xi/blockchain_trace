package cn.huse.trace.web.response.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: huanxi
 * @date: 2019/3/15 13:33
 */
@ApiModel("区块链信息")
public class BlockChainInfoModel {
    @ApiModelProperty("区块链区块数量")
    private Long height;
    @ApiModelProperty("最新区块Hash")
    private String currentBlockHash;
    @ApiModelProperty("前一区块Hash值")
    private String previousBlockHash;

    public BlockChainInfoModel() {
    }

    public BlockChainInfoModel(Long height, String currentBlockHash, String previousBlockHash) {
        this.height = height;
        this.currentBlockHash = currentBlockHash;
        this.previousBlockHash = previousBlockHash;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getCurrentBlockHash() {
        return currentBlockHash;
    }

    public void setCurrentBlockHash(String currentBlockHash) {
        this.currentBlockHash = currentBlockHash;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public void setPreviousBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }
}
