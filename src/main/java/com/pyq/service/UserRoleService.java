package com.pyq.service;

import com.pyq.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pengyq
 * @since 2018-12-06
 */
public interface UserRoleService extends IService<UserRole> {

    public void addUserRole(UserRole userRole);

}
