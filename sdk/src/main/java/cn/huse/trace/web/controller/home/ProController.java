//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.huse.trace.web.controller.home;

import cn.huse.trace.web.common.PageResult;
import cn.huse.trace.web.common.ReturnMessage;
import cn.huse.trace.web.mapper.ProMapper;
import cn.huse.trace.web.pojo.Pro;
import cn.huse.trace.web.pojo.ProExample;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(
        value = "信息获取接口",
        description = "系统信息"
)
@RequestMapping({"/info"})
public class ProController {
    @Resource
    ProMapper proMapper;

    public ProController() {
    }

    @GetMapping({"/milks"})
    @ApiOperation("获取奶粉信息(每次不能超过20条)")
    public ReturnMessage getMilks(@ApiParam("页数") int page, @ApiParam("每页大小") int size) {
        if ( size > 20) {
            return new ReturnMessage(3000, "once fetch to many infos");
        } else {
            PageHelper.startPage(page, size);
            ProExample example = new ProExample();
            Page<Pro> positions = (Page)this.proMapper.selectByExample(example);
            return new ReturnMessage(1, new PageResult(positions.getTotal(), positions.getResult(), positions.getPageNum()));
        }
    }
}
