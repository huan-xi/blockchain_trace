package cn.huse.trace.web.dao;

import cn.huse.trace.web.entity.Project;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: huanxi
 * @date: 2019/4/28 13:44
 */
@Component
public class ProjectDao extends BaseDao<Project> {
    private static final String PAGE = ",\"limit\": %d,\"skip\": %d";
    private static final String SELECROT_BY_UDER_ID = "{\"selector\": {\"_id\": {\"$regex\": \"^Project_%s_.{0,}$\"}}}";
    private static final String SELECROT_ALL = "{\"selector\": {\"_id\": {\"$regex\": \"^Project_.{0,}$\"}}}";

    public List<Project> all(int page, int size, int status) {
        List<Project> ts;
        String sql = String.format(SELECROT_ALL, size, size * (page - 1));
        ts = query(sql);
        if (ts != null && ts.size() > 0) {
            List<Project> res;
            if (status != Project.STATUS_ALL) {
                List<Project> finalTs = ts;
                ts.forEach(t -> {
                    if (t.getStatus() != status) finalTs.remove(t);
                });
                ts = finalTs;
            } else return null;
        }
        return ts;
    }

    public List<Project> queryByUserId(String userId) {
        String sql = String.format(SELECROT_BY_UDER_ID, userId);
        return query(sql);
    }
}
