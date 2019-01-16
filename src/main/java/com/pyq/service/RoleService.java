package com.pyq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pyq.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pengyq
 * @since 2018-12-06
 */

public interface RoleService extends IService<Role> {

    public List<Role> queryRoleListWithSelected(Integer uid);

    IPage<Role> selectByPage(Role role, int start, int length);

    /**
     * 删除角色 同时删除角色资源表中的数据
     * @param roleid
     */
    public void delRole(Integer roleid);

}
