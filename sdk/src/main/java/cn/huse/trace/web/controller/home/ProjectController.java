package cn.huse.trace.web.controller.home;

import cn.huse.trace.web.common.ReturnMessageMap;
import cn.huse.trace.web.config.parsetoken.ParseToken;
import cn.huse.trace.web.dao.DaoException;
import cn.huse.trace.web.entity.Project;
import cn.huse.trace.web.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: huanxi
 * @date: 2019/4/28 15:52
 */
@RestController
@Api(value = "项目接口", description = "项目接口")
@RequestMapping("project")
public class ProjectController {

    @Resource
    private ProjectService projectService;

/*    @GetMapping("all/pass")
    @ApiOperation("获取所有已经审核通过的项目")
    public ReturnMessageMap getAllPassProject(int page, int size) {
        if (page > 0 && size > 0) return new ReturnMessageMap(projectService.all(page, size));
        return null;
    }

    @GetMapping("all/nopass")
    @ApiOperation("获取所有已经审核通过的项目")
    public ReturnMessageMap getAllNoPassProject(int page, int size) {
        if (page > 0 && size > 0) return new ReturnMessageMap(projectService.all(page, size,Project.STATUS_NOT_PASS));
        return null;
    }*/

    @GetMapping("all")
    @ApiOperation("获取所有已经审核通过的项目")
    public ReturnMessageMap getAllProject(int page, int size) {
        if (page > 0 && size > 0) return new ReturnMessageMap(projectService.all(page, size));
        return new ReturnMessageMap(4010, "page or size must be gt 0");
    }

    @PostMapping
    @ApiOperation("发布众筹项目")
    public ReturnMessageMap addProject(Project project, @ParseToken String userId) {
        project.setUserId(userId);
        try {
            projectService.addProject(project);
        } catch (DaoException e) {
            return new ReturnMessageMap(5010, "add project failed!" + e.getMessage());
        }
        return new ReturnMessageMap("public successfully!");
    }

    @PutMapping
    @ApiOperation("更新众筹项目")
    public ReturnMessageMap updateProject(Project project, @ParseToken String userId) {
        try {
//            if (!project.getUserId().equals(userId)) new ReturnMessageMap(4031, "user not have this project");
            projectService.update(project);
        } catch (DaoException e) {
            return new ReturnMessageMap(5014, e.getMessage());
        }
        return new ReturnMessageMap("update successfully!");
    }
}
