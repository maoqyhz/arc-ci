package cn.com.arcsoft.arcci.services.impl;

import cn.com.arcsoft.arcci.entity.Test;
import cn.com.arcsoft.arcci.repo.ITestRepository;
import cn.com.arcsoft.arcci.services.ITestService;
import cn.com.arcsoft.arcci.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fururur
 * @create 2019-11-07-13:43
 */
@Service
public class TestServiceImpl extends BaseServiceImpl<Test, Long> implements ITestService {

    private final ITestRepository repository;

    public TestServiceImpl(ITestRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Test> listAll() {
        return repository.findAll();
    }
}
