package com.pyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pyq.entity.RoleResources;
import com.pyq.entity.UserRole;
import com.pyq.mapper.UserRoleMapper;
import com.pyq.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pyq.shiro.UserShiroRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pengyq
 * @since 2018-12-06
 */
@Service("userRoleService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Resource
    private UserShiroRealm userShiroRealm;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public void addUserRole(UserRole userRole) {

        // 根据userid删除
        int delCount = userRoleMapper.delete(new QueryWrapper<UserRole>().eq("roleId", userRole.getUserId()));

        //if(delCount > 0){
            //添加
            String[] roleids = userRole.getRoleId().split(",");
            for (String roleId : roleids) {
                UserRole u = new UserRole();
                u.setUserId(userRole.getUserId());
                u.setRoleId(roleId);
                userRoleMapper.insert(u);
            }

            //更新当前登录的用户的权限缓存
            List<Integer> userid = new ArrayList<Integer>();
            userid.add(userRole.getUserId());
            userShiroRealm.clearUserAuthByUserId(userid);
       // }

    }
}
