package cn.com.arcsoft.arcci.services;

import cn.com.arcsoft.arcci.entity.Test;
import cn.com.arcsoft.arcci.services.base.IBaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Fururur
 * @create 2019-11-07-13:43
 */
@Transactional
public interface ITestService extends IBaseService<Test, Long> {
    List<Test> listAll();
}
