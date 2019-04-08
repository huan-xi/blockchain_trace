package cn.huse.trace.web.controller.blocknet;

import cn.huse.trace.sdk.trace.ChaincodeManager;
import cn.huse.trace.sdk.trace.FabricManager;
import cn.huse.trace.web.common.PageResult;
import cn.huse.trace.web.common.ReturnMessage;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @author: huanxi
 * @date: 2019/3/15 12:26
 */
//@RestController
//@RequestMapping("/block")
//@Api(value = "区块信息", description = "区块信息查询接口")
public class BlockController {
    ChaincodeManager fabricManager;

    @GetMapping("/blocks")
    @ApiOperation("获取区块")
    public ReturnMessage getBlocks(@ApiParam("页数") int page, @ApiParam("每页大小") int size) {
        if (size <= 0) return null;
        if (size > 10) {
            return new ReturnMessage(3000, "once fetch to many infos");
        } else {
            long total = 0;
            int totalPage;
            List<BlockInfoModel> blockInfoModels = new ArrayList<>();
            try {
                total = fabricManager.getChannelInstant().queryBlockchainInfo().getHeight();
                totalPage = (int) (total / size);
            } catch (ProposalException | InvalidArgumentException e) {
                e.printStackTrace();
                return new ReturnMessage(100, "query blockchain error!");
            }
            //最后一个
            int j=0;
            for (long i = total-(page-1)*size-1; j < size; j++,i--) {
                if (i < 0) break;
                BlockInfoModel b;
                try {
                    b = fabricManager.queryBlockByNumber(i);
                } catch (InvalidArgumentException | ProposalException | IOException e) {
                    e.printStackTrace();
                    return new ReturnMessage(12, "query block error!");
                }
                blockInfoModels.add(b);
            }
            return new ReturnMessage(1, new PageResult(total, blockInfoModels, page));
        }
    }

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
