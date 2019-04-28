package cn.huse.trace.web.controller.blocknet;

import cn.huse.trace.sdk.trace.ChaincodeManager;
import cn.huse.trace.sdk.trace.FabricManager;
import cn.huse.trace.web.response.model.BlockChainInfoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Hex;
import org.hyperledger.fabric.sdk.BlockchainInfo;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author: huanxi
 * @date: 2019/3/15 11:31
 */

@RequestMapping("/blockchain")
@RestController
@Api(value = "区块链信息", description = "区块链信息查询接口")
public class BlockChainController {
    ChaincodeManager fabricManager;
    private BlockchainInfo getBlockchainInfo() {
        try {
            return fabricManager.getChannelInstant().queryBlockchainInfo();
        } catch (ProposalException | InvalidArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation("查询最新区块hash值")
    @GetMapping("/currenthash")
    public String getCurrentHash() {
        byte[] b = Objects.requireNonNull(getBlockchainInfo()).getCurrentBlockHash();
        return Hex.encodeHexString(b);
    }

    @ApiOperation("查询区块链信息")
    @GetMapping("/info")
    public BlockChainInfoModel getInfo() {
        BlockchainInfo b = getBlockchainInfo();
        assert b != null;
        return new BlockChainInfoModel(b.getHeight(), Hex.encodeHexString(b.getCurrentBlockHash()), Hex.encodeHexString(b.getPreviousBlockHash()));
    }

    @Autowired
    public void setFabricManager(ChaincodeManager fabricManager) {
        this.fabricManager = FabricManager.getInstance().getManager();
    }
}
