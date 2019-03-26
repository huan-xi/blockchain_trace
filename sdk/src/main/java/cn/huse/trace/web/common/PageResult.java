//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.huse.trace.web.common;

import java.io.Serializable;
import java.util.List;

public class PageResult implements Serializable {
    private static final long serialVersionUID = 6159543136429894293L;
    private long total;
    private List rows;
    private int currentPage;

    public PageResult(long total, List rows, int currentPage) {
        this.total = total;
        this.rows = rows;
        this.currentPage = currentPage;
    }

    public PageResult() {
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return this.rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
