package cn.huse.trace.web.controller.blocknet;

import cn.huse.trace.sdk.Functions;
import cn.huse.trace.sdk.trace.ChaincodeManager;
import cn.huse.trace.sdk.trace.FabricManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * @author: huanxi
 * @date: 2019/3/15 23:26
 */
@RestController
@Api(value = "公开接口", description = "公开接口")
@RequestMapping("/public")
public class PublicController {
    ChaincodeManager fabricManager;

    @GetMapping("/query")
    @ApiOperation("查询奶粉信息")
    public Map<String, String> query(String name) {
        try {
            return fabricManager.query(Functions.readMilk, new String[]{name});
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
