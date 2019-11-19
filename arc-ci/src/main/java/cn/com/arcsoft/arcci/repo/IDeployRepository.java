package cn.com.arcsoft.arcci.repo;

import cn.com.arcsoft.arcci.entity.Deploy;
import cn.com.arcsoft.arcci.repo.base.IBaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * @author Fururur
 * @create 2019-11-08-9:28
 */
public interface IDeployRepository extends IBaseRepository<Deploy, Long> {
    @Query(nativeQuery = true, value = "select distinct(deploy_project) from deploy")
    List<String> findDistinctProject();
}
