package cn.com.arcsoft.arcci.controller.api;

import cn.com.arcsoft.arcci.config.PropertiesConfig;
import cn.com.arcsoft.arcci.entity.Project;
import cn.com.arcsoft.arcci.entity.base.RestResult;
import cn.com.arcsoft.arcci.services.IProjectService;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fururur
 * @create 2019-11-08-9:25
 */
@Slf4j
@RestController
@RequestMapping("/api/project")
public class ProjectController {
    private PropertiesConfig prop;


    private IProjectService projectService;

    public ProjectController(PropertiesConfig prop, IProjectService projectService) {
        this.prop = prop;
        this.projectService = projectService;
    }

    @RequestMapping("listAll")
    public Object getListBy() {
        return new RestResult(projectService.listAll());
    }

    @RequestMapping(value = "createOrUpdate", method = RequestMethod.POST)
    public Object createOrUpdate(@RequestBody Project p) {
        log.info(p.toString());
        projectService.saveOrUpdate(p);
        return new RestResult(null);
    }

    @RequestMapping("delete")
    public Object delete(Long id) {
        projectService.deleteById(id);
        return new RestResult(null);
    }

    @RequestMapping("detail")
    public Object detail(Long id) {
        return new RestResult(projectService.getById(id));
    }


    @RequestMapping("branch")
    public Object branch(String url) throws GitLabApiException {
        GitLabApi api = new GitLabApi(prop.getGitlabUrl(), prop.getAccessToken());
        List<Map<String, String>> list = new ArrayList<>();

        api.getRepositoryApi().getBranches(url2Path(url)).forEach(x -> {
            Map<String, String> map = new HashMap<>();
            map.put("value", x.getName());
            map.put("label", x.getCommit().getMessage());
            list.add(map);
        });
        return new RestResult(list);
    }

    @RequestMapping("tag")
    public Object tag(String url) throws GitLabApiException {

        GitLabApi api = new GitLabApi(prop.getGitlabUrl(), prop.getAccessToken());
        List<Map<String, String>> list = new ArrayList<>();

        api.getTagsApi().getTags(url2Path(url)).forEach(x -> {
            Map<String, String> map = new HashMap<>();
            map.put("value", x.getName());
            map.put("label", x.getMessage());
            list.add(map);
        });
        return new RestResult(list);
    }

    private String url2Path(String projectUrl) {
        String[] tmp = projectUrl.split("/");
        return tmp[1] + "/" + tmp[2];
    }
}
