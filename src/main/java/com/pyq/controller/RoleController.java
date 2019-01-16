package com.pyq.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pyq.entity.Role;
import com.pyq.entity.RoleResources;
import com.pyq.service.RoleResourcesService;
import com.pyq.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pengyq
 * @since 2018-12-06
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private RoleResourcesService roleResourcesService;

    @RequestMapping("/roles")
    public Map<String,Object> getAll(Role role, String draw,
                                     @RequestParam(required = false, defaultValue = "1") int start,
                                     @RequestParam(required = false, defaultValue = "10") int length){

        Map<String,Object> map = new HashMap<>();
        IPage<Role> pageInfo = roleService.selectByPage(role, (start/length)+1, length);
        map.put("draw",draw);
        map.put("recordsTotal",pageInfo.getTotal());
        map.put("recordsFiltered",pageInfo.getTotal());
        map.put("data", pageInfo.getRecords());
        return map;
    }

    @RequestMapping("/rolesWithSelected")
    public List<Role> rolesWithSelected(Integer uid){
        return roleService.queryRoleListWithSelected(uid);
    }

    //分配角色
    @RequestMapping("/saveRoleResources")
    public String saveRoleResources(RoleResources roleResources){
        if(StringUtils.isEmpty(roleResources.getRoleId()))
            return "error";
        try {
            roleResourcesService.addRoleResources(roleResources);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping(value = "/add")
    public String add(Role role) {
        try {
            roleService.save(role);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    @RequestMapping(value = "/delete")
    public String delete(Integer id){
        try{
            roleService.delRole(id);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

}
