package cn.huse.trace.web.controller.home;

import cn.huse.trace.web.common.ReturnMessageMap;
import cn.huse.trace.web.config.parsetoken.ParseToken;
import cn.huse.trace.web.dao.DaoException;
import cn.huse.trace.web.entity.Project;
import cn.huse.trace.web.entity.Transaction;
import cn.huse.trace.web.entity.User;
import cn.huse.trace.web.entity.view.ProjectView;
import cn.huse.trace.web.service.ProjectService;
import cn.huse.trace.web.service.TransactionService;
import cn.huse.trace.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: huanxi
 * @date: 2019/4/28 15:52
 */
@RestController
@Api(value = "项目接口", description = "项目接口")
@RequestMapping("/project")
public class ProjectController {

    @Resource
    private ProjectService projectService;
    @Resource
    private UserService userService;
    @Resource
    private TransactionService transactionService;

    @GetMapping("all")
    @ApiOperation("获取所有已经审核通过的项目")
    public ReturnMessageMap getAllProject(int page, int size) {
        List<ProjectView> projectViews = new ArrayList<>();
        if (page > 0 && size > 0) {
            List<Project> projects = projectService.all(page, size);
            projects.forEach(project -> {
                List<Transaction> transactions = transactionService.getTransactionOutByUserId(project.getProjectId());
                User user = userService.getUser(project.getUserId());
                float balance = transactionService.getBalance(project.getProjectId());
                if (user!=null)
                projectViews.add(new ProjectView(project, transactions.size(), user.getName(), user.getHeaderUrl(), balance));
            });
            return new ReturnMessageMap(projectViews);
        }
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
        if (project.getProjectId() == null) new ReturnMessageMap(4010, "project id must be not null");
        Project t = projectService.getProject(project.getProjectId());
        if (t == null) new ReturnMessageMap(4032, "not exists this project");
        try {
            project.setCreateTime(t.getCreateTime());
            project.setStatus(t.getStatus());
            projectService.update(project);
        } catch (DaoException e) {
            return new ReturnMessageMap(5014, e.getMessage());
        }
        return new ReturnMessageMap("update successfully!");
    }
}
