package cn.huse.trace.web.entity;

import cn.huse.trace.web.common.auth.jwt.JwtUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author: huanxi
 * @date: 2019/4/27 21:44
 */

@NoArgsConstructor
@ApiModel("用户")
@Data
public class User implements JwtUser, BaseEntity {
    @ApiModelProperty("头像地址")
    private String headerUrl;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("昵称")
    private String name;
    @NotNull
    @ApiModelProperty("账号")
    private String account;
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
