package cn.huse.trace.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 众筹项目
 *
 * @author: huanxi
 * @date: 2019/4/28 13:35
 */
@Data
@NoArgsConstructor
@ApiModel("众筹项目")
public class Project implements BaseEntity, Serializable {
    public static final int STATUS_PASS = 1;
    public static final int STATUS_NOT_PASS = 1;
    public static final int STATUS_WAITE = 0;
    public static final int STATUS_ALL = -1;
    @ApiModelProperty("项目Id")
    private String projectId;
    @ApiModelProperty("项目描述图片")
    private String image;
    @ApiModelProperty("发起用户")
    private String userId;
    @ApiModelProperty("项目标题")
    private String title;
    @ApiModelProperty("项目描述")
    private String desc;
    @ApiModelProperty("目标金额")
    private float targetAmount;
    @ApiModelProperty("状态(0 待审核,1 审核通过，2 审核未通过)")
    private int status;

    @Override
    @JsonIgnore
    public String getId() {
        return this.projectId;
    }
}
