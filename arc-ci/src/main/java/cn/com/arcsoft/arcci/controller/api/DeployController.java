package cn.com.arcsoft.arcci.controller.api;

import cn.com.arcsoft.arcci.config.PropertiesConfig;
import cn.com.arcsoft.arcci.entity.Deploy;
import cn.com.arcsoft.arcci.entity.base.RestResult;
import cn.com.arcsoft.arcci.entity.base.ResultCode;
import cn.com.arcsoft.arcci.services.IDeployService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Pipeline;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fururur
 * @create 2019-11-08-15:46
 */
@Slf4j
@RestController
@RequestMapping("/api/deploy")
public class DeployController {
    private PropertiesConfig prop;
    private IDeployService deployService;

    public DeployController(PropertiesConfig prop, IDeployService deployService) {
        this.prop = prop;
        this.deployService = deployService;
    }

    @RequestMapping("getListBy")
    public Object getListBy(String projectName) {
        return new RestResult(deployService.findBy(projectName));
    }

    @RequestMapping(value = "createOrUpdate", method = RequestMethod.POST)
    public Object createOrUpdate(@RequestBody Deploy d) {
        log.info(d.toString());
        deployService.saveOrUpdate(d);
        return new RestResult(null);
    }

    @RequestMapping("delete")
    public Object delete(Long id) {
        deployService.deleteById(id);
        return new RestResult(null);
    }

    @RequestMapping("detail")
    public Object detail(Long id) {
        return new RestResult(deployService.getById(id));
    }

    @RequestMapping("projectList")
    public Object projectList() {
        List<String> projects = deployService.getProjectNameFromDeploy();
        List<Map<String, String>> list = new ArrayList<>();
        projects.forEach(x -> {
            Map<String, String> map = new HashMap<>();
            map.put("value", x);
            map.put("label", x);
            list.add(map);
        });
        return new RestResult(list);
    }

    @RequestMapping(value = "createPipeline", method = RequestMethod.POST)
    public Object createPipeline(HttpServletRequest request) throws GitLabApiException {
        JSONObject params = parserJson(request);
        GitLabApi api = new GitLabApi(prop.getGitlabUrl(), prop.getAccessToken());
        String[] tmp = params.getString("projectUrl").split("/");
        String path = tmp[1] + "/" + tmp[2];
        String ref = params.getString("ref");
        Map<String, String> variables = params.getObject("params", Map.class);
        Map<String, String> namedVariables = new HashMap<>();
        namedVariables.put("SERVER_IP", variables.get("ip"));
        namedVariables.put("SERVER_PORT", variables.get("port"));
        namedVariables.put("SERVER_USER", variables.get("user"));
        Pipeline p = api.getPipelineApi().createPipeline(path, ref, namedVariables);
        Map<String, String> ret = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (p != null) {
            ret.put("web_url", p.getWebUrl());
            ret.put("sha", p.getSha());
            ret.put("id", p.getId().toString());
            return new RestResult(ret);
        } else
            return new RestResult(ResultCode.CREATE_PIPLINE_FAILED.getCode(), null);
    }


    protected JSONObject parserJson(HttpServletRequest request) {
        StringBuffer buffer = new StringBuffer();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return JSONObject.parseObject(buffer.toString());
    }
}
