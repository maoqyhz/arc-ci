package cn.com.arcsoft.arcci.services.impl;

import cn.com.arcsoft.arcci.entity.User;
import cn.com.arcsoft.arcci.repo.IUserRepository;
import cn.com.arcsoft.arcci.services.IUserService;
import cn.com.arcsoft.arcci.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Fururur
 * @create 2019-11-07-13:43
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements IUserService {

    private final IUserRepository repository;

    public UserServiceImpl(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> listAll() {
        return repository.findAll();
    }

    @Override
    public boolean checkUser(User u) {
        User model = repository.findByUsername(u.getUsername());
        if (model == null)
            return false;
        return u.getPassword().equals(model.getPassword());
    }
}
