package cn.com.arcsoft.arcci.services.impl;

import cn.com.arcsoft.arcci.entity.Deploy;
import cn.com.arcsoft.arcci.repo.IDeployRepository;
import cn.com.arcsoft.arcci.services.IDeployService;
import cn.com.arcsoft.arcci.services.base.BaseServiceImpl;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fururur
 * @create 2019-11-08-9:27
 */
@Service
public class DeployServiceImpl extends BaseServiceImpl<Deploy, Long> implements IDeployService {
    private final IDeployRepository repository;

    public DeployServiceImpl(IDeployRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Deploy> findBy(String projectName) {
        Deploy d = new Deploy();
        if (!Strings.isNullOrEmpty(projectName))
            d.setDeployProject(projectName);
        return repository.findByEntity(d);
    }

    @Override
    public List<String> getProjectNameFromDeploy() {
        return repository.findDistinctProject();
    }
}
