package com.pyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pyq.entity.RoleResources;
import com.pyq.mapper.RoleResourcesMapper;
import com.pyq.mapper.UserRoleMapper;
import com.pyq.service.RoleResourcesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pyq.shiro.UserShiroRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
@Service("roleResourcesService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
public class RoleResourcesServiceImpl extends ServiceImpl<RoleResourcesMapper, RoleResources> implements RoleResourcesService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RoleResourcesMapper roleResourcesMapper;

    @Autowired
    private UserShiroRealm userShiroRealm;

    /**
     * 更新权限
     * @param roleResources 
     * @author Pengyq
     * @date 2018-12-07 11:38:39
     * @version v1.0.0
     */
    @Override
    public void addRoleResources(RoleResources roleResources) {

        // 根据角色id删除
        int delCount = roleResourcesMapper.delete(new QueryWrapper<RoleResources>().eq("roleId", roleResources.getRoleId()));

        if(delCount > 0){
            //添加
            if(!StringUtils.isEmpty(roleResources.getResourcesId())){
                String[] resourcesArr = roleResources.getResourcesId().split(",");
                for(String resourcesId:resourcesArr ){
                    RoleResources r = new RoleResources();
                    r.setRoleId(roleResources.getRoleId());
                    r.setResourcesId(resourcesId);
                    roleResourcesMapper.insert(r);
                }
            }
        }

        List<Integer> userIds= userRoleMapper.findUserIdByRoleId(roleResources.getRoleId());

        //更新当前登录的用户的权限缓存
        userShiroRealm.clearUserAuthByUserId(userIds);

    }
}
