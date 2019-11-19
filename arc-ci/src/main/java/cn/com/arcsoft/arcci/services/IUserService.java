package cn.com.arcsoft.arcci.services;

import cn.com.arcsoft.arcci.entity.User;
import cn.com.arcsoft.arcci.services.base.IBaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Fururur
 * @create 2019-11-07-13:43
 */
@Transactional
public interface IUserService extends IBaseService<User, Long> {
    List<User> listAll();

    boolean checkUser(User u);
}
