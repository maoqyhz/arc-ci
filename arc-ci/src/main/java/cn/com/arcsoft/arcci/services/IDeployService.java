package cn.com.arcsoft.arcci.services;

import cn.com.arcsoft.arcci.entity.Deploy;
import cn.com.arcsoft.arcci.services.base.IBaseService;

import java.util.List;

/**
 * @author Fururur
 * @create 2019-11-08-9:26
 */
public interface IDeployService extends IBaseService<Deploy, Long> {
    List<Deploy> findBy(String projectName);
    List<String> getProjectNameFromDeploy();
}
