package com.pyq.mapper;

import com.pyq.entity.Role;
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
public interface RoleMapper extends BaseMapper<Role> {

    public List<Role> queryRoleListWithSelected(Integer id);

}
