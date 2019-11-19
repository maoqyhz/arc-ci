package cn.com.arcsoft.arcci.repo;

import cn.com.arcsoft.arcci.entity.User;
import cn.com.arcsoft.arcci.repo.base.IBaseRepository;

/**
 * @author Fururur
 * @create 2019-11-07-13:42
 */
public interface IUserRepository extends IBaseRepository<User,Long> {
    User findByUsername(String username);

}
