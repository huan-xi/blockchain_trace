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

    //分页查询所有
    public List<Project> all(int page, int size) {
        String sqlAll = "{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$regex\": \"^Project_.{0,}$\"\n" +
                "      }\n" +
                "   },\n" +
                "   \"skip\": %d,\n" +
                "   \"limit\": %d\n" +
                "}";
        sqlAll = String.format(sqlAll, (page - 1) * size, size);
        return query(sqlAll);
    }  //分页查询所有

    public List<Project> all(int page, int size, int status) {
        String sqlAll = "{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$regex\": \"^Project_.{0,}$\"\n" +
                "      },\n" +
                "      \"status\": {\n" +
                "         \"$eq\": %d\n" +
                "      }\n" +
                "   },\n" +
                "   \"skip\": %d,\n" +
                "   \"limit\": %d\n" +
                "}";
        sqlAll = String.format(sqlAll, status, (page - 1) * size, size);
        return query(sqlAll);
    }

    public List<Project> queryByUserId(String userId) {
        String sql = "{\n" +
                "   \"selector\": {\n" +
                "      \"_id\": {\n" +
                "         \"$regex\": \"^Project_.{0,}$\"\n" +
                "      },\n" +
                "      \"userId\": {\n" +
                "         \"$eq\": \"%s\"\n" +
                "      }\n" +
                "   }\n" +
                "}";
        sql = String.format(sql, userId);
        return query(sql);
    }
}
