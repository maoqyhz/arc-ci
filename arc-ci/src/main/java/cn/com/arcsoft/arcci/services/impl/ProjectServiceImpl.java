package cn.com.arcsoft.arcci.services.impl;

import cn.com.arcsoft.arcci.entity.Project;
import cn.com.arcsoft.arcci.repo.IProjectRepository;
import cn.com.arcsoft.arcci.services.IProjectService;
import cn.com.arcsoft.arcci.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fururur
 * @create 2019-11-08-9:27
 */
@Service
public class ProjectServiceImpl extends BaseServiceImpl<Project, Long> implements IProjectService {
    private final IProjectRepository repository;

    public ProjectServiceImpl(IProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Project> listAll() {
        return repository.findAll();
    }
}
