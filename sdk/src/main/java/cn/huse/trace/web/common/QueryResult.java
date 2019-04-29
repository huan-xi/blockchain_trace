package cn.huse.trace.web.common;

import lombok.Data;

/**
 * @author: huanxi
 * @date: 2019/4/29 20:30
 */
@Data
public class QueryResult {
    public static int CODE_SUCCESS = 1;
    public static int CODE_ERROR = 0;
    private int code;
    private String data;
    private String txId;

    public boolean isSuccess() {
        return this.code == CODE_SUCCESS;
    }
}
