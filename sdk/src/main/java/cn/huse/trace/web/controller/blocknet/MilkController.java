package cn.huse.trace.web.controller.blocknet;

import cn.huse.trace.sdk.Functions;
import cn.huse.trace.sdk.trace.ChaincodeManager;
import cn.huse.trace.sdk.trace.FabricManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author: huanxi
 * @date: 2019/3/15 14:12
 */
//@RestController
//@Api(value = "奶粉操作", description = "对奶粉初始化，加工，检测")
//@RequestMapping("/milk")
public class MilkController {
    ChaincodeManager fabricManager;

    @PostMapping("/process")
    public Map<String, String> processMilk(@NotNull @ApiParam("id") String name, @NotNull @ApiParam("加工信息") String processInfo) {
        try {
            return fabricManager.invoke(Functions.processMilk, new String[]{name, processInfo});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/inspect")
    public Map<String, String> inspectionMilk(@NotNull @ApiParam("id") String name, @NotNull @ApiParam("检测信息") String inspectionInfo) {
        try {
            return fabricManager.invoke(Functions.inspectionMilk, new String[]{name, inspectionInfo});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @PostMapping("/add")
    @ApiOperation("初始化奶粉信息，即添加奶粉信息")
    public Map<String, String> addMilk(
            @NotNull @ApiParam("id") String name,
            @NotNull @ApiParam("所有者") String owner,
            @NotNull @ApiParam("初始化数据(重量，颜色等)") String data) {
        try {
            return fabricManager.invoke(Functions.initMilk, new String[]{name, owner, data});
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
