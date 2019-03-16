package cn.huse.trace.web.controller;

import cn.huse.trace.sdk.trace.ChaincodeManager;
import cn.huse.trace.sdk.trace.FabricManager;
import cn.huse.trace.web.response.model.BlockInfoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.binary.Hex;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author: huanxi
 * @date: 2019/3/15 12:26
 */
@RestController
@RequestMapping("/block")
@Api(value = "区块信息", description = "区块信息查询接口")
public class BlockController {
    ChaincodeManager fabricManager;

    @GetMapping("/query/hash")
    @ApiOperation("根据Hash值获取区块信息")
    public BlockInfoModel queryByHash(@ApiParam("区块号") String hash) {
        try {
            return fabricManager.queryBlockByHash(Hex.decodeHex(hash));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping("/query/no")
    @ApiOperation("根据区块号查询区块,区块号不能超过区块链区块长度")
    public BlockInfoModel queryByNo(@ApiParam("区块号") long no) {
        try {
            return fabricManager.queryBlockByNumber(no);

        } catch (InvalidArgumentException | ProposalException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/query/txid")
    @ApiOperation("根据交易id查询区块")
    public BlockInfoModel query(@ApiParam("交易id") String txid) {
        try {
            return fabricManager.queryBlockByTransactionID(txid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Autowired
    public void setFabricManager(ChaincodeManager fabricManager) {
        this.fabricManager = FabricManager.getInstance().getManager();
    }
}
