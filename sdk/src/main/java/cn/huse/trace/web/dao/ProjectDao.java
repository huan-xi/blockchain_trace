package cn.huse.trace.web.dao;

import cn.huse.trace.web.entity.Project;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: huanxi
 * @date: 2019/4/28 13:44
 */
@Component
public class ProjectDao extends BaseDao<Project> {
    private static final String SELECROT_BY_UDER_ID = "{\"selector\": {\"_id\": {\"$regex\": \"^project_%s_.{0,}$\"}},\"limit\": %d,\"skip\": %d}";
    private static final String SELECROT_ALL = "{\"selector\": {\"_id\": { \"$gt\": \"\"}},\"limit\": %d,\"skip\": %d}";

    public List<Project> all(int page, int size) {
        List<Project> ts = new ArrayList<>();
//        String sql = String.format(SELECROT_STRING, size,size*page);
//        RequestBody body = RequestBody.create(JSON_HEADER, sql);
        return null;
    }

    public List<Project> queryByUserId(String userId, int page, int size) {
        String sql = String.format(SELECROT_BY_UDER_ID, userId, size, size * (page - 1));
        return query(sql);
    }
}
