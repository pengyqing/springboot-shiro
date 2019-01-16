package com.pyq.service;

import com.pyq.entity.RoleResources;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pengyq
 * @since 2018-12-06
 */

public interface RoleResourcesService extends IService<RoleResources> {

    public void addRoleResources(RoleResources roleResources);

}
