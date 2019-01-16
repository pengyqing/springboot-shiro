package com.pyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pyq.entity.Resources;
import com.pyq.entity.Role;
import com.pyq.entity.RoleResources;
import com.pyq.mapper.RoleMapper;
import com.pyq.mapper.RoleResourcesMapper;
import com.pyq.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pengyq
 * @since 2018-12-06
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleResourcesMapper roleResourcesMapper;

    @Override
    public List<Role> queryRoleListWithSelected(Integer uid) {
        return roleMapper.queryRoleListWithSelected(uid);
    }

    @Override
    public IPage<Role> selectByPage(Role role, int start, int length) {
        Page<Role> page = new Page<>(start, length);

        IPage<Role> roleList = roleMapper.selectPage(page, new QueryWrapper<>(role));

        return roleList;
    }

    @Override
    public void delRole(Integer roleid) {

        // 删除角色
        int delCount = roleMapper.deleteById(roleid);

        if(delCount > 0){
            // //删除角色资源
            roleResourcesMapper.delete(new QueryWrapper<RoleResources>().eq("roleId", roleid));
        }

    }
}
