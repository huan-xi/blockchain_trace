package cn.huse.trace.web.service;

import cn.huse.trace.web.common.Utils;
import cn.huse.trace.web.dao.DaoException;
import cn.huse.trace.web.dao.ProjectDao;
import cn.huse.trace.web.entity.Project;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: huanxi
 * @date: 2019/4/28 13:44
 */
@Service
public class ProjectService {
    @Resource
    ProjectDao projectDao;

    private final static String PROJECT_FLAG = "project_";

    public void addProject(Project project) throws DaoException {
        project.setProjectId(PROJECT_FLAG + project.getUserId() + "_" + Utils.getUUID());
        projectDao.add(project);
    }

    public Project getProject(String projectId) {
        return projectDao.get(projectId);
    }

    public void update(Project project) throws DaoException {

    }

}
