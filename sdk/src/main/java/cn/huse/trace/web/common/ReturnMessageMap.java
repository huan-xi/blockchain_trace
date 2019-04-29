package cn.huse.trace.web.common;

import java.io.Serializable;
import java.util.HashMap;

public class ReturnMessageMap extends HashMap implements Serializable {
    private static final long serialVersionUID = -3307660546347616895L;
    private final int OK_RESPONSE = 200;
    private int status;//状态码

    public ReturnMessageMap(Object msg) {
        putOk();
        addMsg(msg);
    }

    public int getStatus() {
        return status;
    }

    public ReturnMessageMap() {
        putOk();
    }

    private void putOk() {
        this.put("status", this.OK_RESPONSE);
    }

    public ReturnMessageMap(int status) {
        this.put("status", status);
    }
    public ReturnMessageMap(int status,String msg) {
        this.put("status",status);
        this.put("msg",msg);
    }

    public void addError(String error) {
        this.put("error", error);
    }
    public void addMsg(Object error) {
        this.put("msg", error);
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
