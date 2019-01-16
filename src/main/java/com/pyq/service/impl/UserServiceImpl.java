package com.pyq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pyq.entity.User;
import com.pyq.entity.UserRole;
import com.pyq.mapper.UserMapper;
import com.pyq.mapper.UserRoleMapper;
import com.pyq.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pengyq
 * @since 2018-12-06
 */
@Service("userService")
@Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public IPage<User> selectByPage(User user, int start, int length) {

        Page<User> page = new Page<>(start,length);

        IPage<User> userList = userMapper.selectPage(page,new QueryWrapper<User>()
                                             .like("username", user.getUsername())
                                             .eq(user.getId() != null,"id",user.getId())
                                             .eq(user.getEnable() != null,"enable",user.getEnable()));

        return userList;
    }

    @Override
    public User selectByUsername(String username) {

        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username));

        return user;
    }

    @Override
    public void delUser(Integer userid) {

        // 删除用户
        int delCount = userMapper.deleteById(userid);

        if(delCount > 0){
            //删除用户角色
            userRoleMapper.delete(new QueryWrapper<UserRole>().eq("userid",userid));
        }

    }
}
