package cn.huse.trace.web.controller.home;

import cn.huse.trace.web.common.ReturnMessageMap;
import cn.huse.trace.web.config.parsetoken.ParseToken;
import cn.huse.trace.web.dao.DaoException;
import cn.huse.trace.web.entity.Project;
import cn.huse.trace.web.service.ProjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: huanxi
 * @date: 2019/4/28 15:52
 */
@RestController
@RequestMapping("project")
public class ProjectController {

    @Resource
    private ProjectService projectService;

    @PostMapping
    @ApiOperation("发布众筹项目")
    public ReturnMessageMap addProject(Project project, @ParseToken String userId) {
        project.setUserId(userId);
        try {
            projectService.addProject(project);
        } catch (DaoException e) {
            return new ReturnMessageMap(5010, "add project failed!");
        }
        return new ReturnMessageMap("public successfully!");
    }
}
