package cn.huse.trace.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: huanxi
 * @date: 2019/4/29 12:10
 */
@Data
public class Transaction implements BaseEntity, Serializable {

    public static final String FLAG = Transaction.class.getSimpleName();
    private String id;
    private float amount;
    private String inId;
    private String outId;
    private long time;
    //交易备注
    private String desc;

    public Transaction() {
        this.time = System.currentTimeMillis();
    }

    @Override
    @JsonIgnore
    public String getId() {
        return this.id;
    }
}
