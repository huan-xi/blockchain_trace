package cn.huse.trace.web.entity.view;

import cn.huse.trace.web.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;

/**
 * @author: huanxi
 * @date: 2019/5/2 12:16
 */
@Data
@ToString
public class UserView {
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
    @ApiModelProperty("余额")
    private float balance;

    public UserView(User user, float balance) {
        try {
            BeanUtils.copyProperties(this, user);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }
}
