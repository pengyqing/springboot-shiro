package com.pyq.mapper;

import com.pyq.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pengyq
 * @since 2018-12-06
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    public List<Integer> findUserIdByRoleId(Integer roleId);

}
