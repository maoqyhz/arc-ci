package cn.com.arcsoft.arcci.controller;

import cn.com.arcsoft.arcci.config.PropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fururur
 * @create 2019-11-04-13:51
 */
@Slf4j
@RestController
@RequestMapping("/home")
public class HomeController {

    private PropertiesConfig prop;
    // private GitLabApi gitLabApi = new GitLabApi(prop.getGitlabUrl(), prop.getAccessToken());

    public HomeController(PropertiesConfig prop) {
        this.prop = prop;
    }


}
