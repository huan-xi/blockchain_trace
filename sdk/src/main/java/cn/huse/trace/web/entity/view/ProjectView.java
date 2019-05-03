package cn.huse.trace.web.entity.view;

import cn.huse.trace.web.entity.Project;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author: huanxi
 * @date: 2019/5/3 15:30
 */
@Data
@ToString
public class ProjectView {

    @ApiModelProperty("项目描述图片")
    private String image;
    @ApiModelProperty("发起用户名")
    private String username;
    private String userHeader;
    @ApiModelProperty("项目标题")
    private String title;
    @ApiModelProperty("项目描述")
    private String desc;
    @ApiModelProperty("目标金额")
    private float targetAmount;
    @ApiModelProperty("发布地址")
    private String address;
    //帮助次数
    private int helpCount;
    //已经筹集金额数
    private float raisedAmount;

    public ProjectView(Project project, int helpCount, String username, String userHeader, float raisedAmount) {
        try {
            BeanUtils.copyProperties(this, project);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        this.helpCount = helpCount;
        this.username = username;
        this.userHeader = userHeader;
        this.raisedAmount = raisedAmount;
    }
}
