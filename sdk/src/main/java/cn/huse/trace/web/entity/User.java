package cn.huse.trace.web.entity;

import cn.huse.trace.web.common.auth.jwt.JwtUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: huanxi
 * @date: 2019/4/27 21:44
 */

@NoArgsConstructor
@ApiModel("用户")
@Data
public class User implements JwtUser, BaseEntity, Serializable {
    public static final String FLAG = User.class.getSimpleName();
    @ApiModelProperty("头像地址")
    private String headerUrl;
    @NotNull
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("昵称")
    private String name;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("详细描述")
    private String desc;
    @ApiModelProperty("注册时间")
    private String createTime;
    @NotNull
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("状态")
    private int status;

    @Override
    @JsonIgnore
    public Object getJwtUserId() {
        return account;
    }

    @Override
    public void setJwtUserId(Object subject) {
        this.account = (String) subject;
    }

    @Override
    public void setJwtStatus(Object status) {
        this.status = (int) status;
    }

    @Override
    @JsonIgnore
    public Object getJwtStatus() {
        return this.status;
    }

    @Override
    @JsonIgnore
    public String getId() {
        return this.account;
    }
}
