package cn.com.arcsoft.arcci.services;

import cn.com.arcsoft.arcci.entity.Project;
import cn.com.arcsoft.arcci.services.base.IBaseService;

import java.util.List;

/**
 * @author Fururur
 * @create 2019-11-08-9:26
 */
public interface IProjectService  extends IBaseService<Project, Long> {
    List<Project> listAll();
}
