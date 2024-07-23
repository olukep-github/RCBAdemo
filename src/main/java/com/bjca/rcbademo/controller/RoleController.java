package com.bjca.rcbademo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjca.rcbademo.dao.PermissionMapper;
import com.bjca.rcbademo.dao.RoleMapper;
import com.bjca.rcbademo.dao.RolePermissionMapper;
import com.bjca.rcbademo.entity.sysPermission;
import com.bjca.rcbademo.entity.sysRole;
import com.bjca.rcbademo.entity.sysRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @PostMapping("role")
    public String addRole(sysRole role) {
        int i=roleMapper.insert(role);
        if(i>0)
            return "Add role success!";
        else
            return "Add role failed!";
    }

    @GetMapping("role")
    public List<sysRole> getRoles() {
        List<sysRole> roles=roleMapper.selectList(null);
        return roles;
    }

    @DeleteMapping("role")
    public String deleteRole(int id) {
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.eq("id",id);
        updateWrapper.set("is_delete",1);
        int i = roleMapper.update(null, updateWrapper);
        if (i>0)
            return "Delete user success!";
        else
            return "Delete user failed!";
    }

    @PostMapping("role/grant")
    public String grantRole(int roleid,int permissionid) {
        sysRolePermission role_permission=new sysRolePermission();
        role_permission.setPermissionId(permissionid);
        role_permission.setRoleId(roleid);

        QueryWrapper<sysRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",roleid);
        int count = roleMapper.selectCount(queryWrapper);
        if (count == 0) {
            return "Grant role failed!\n" +
                    "Role or Permission isn't exist!";
        }

        QueryWrapper<sysPermission> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("id",permissionid);
        count = permissionMapper.selectCount(queryWrapper1);
        if (count == 0){
            return "Grant role failed!\n" +
                    "Role or Permission isn't exist!";
        }

        int flag = 0;
        flag = flag + roleMapper.selectOne(queryWrapper).getIsDelete()
                + permissionMapper.selectOne(queryWrapper1).getIsDelete();
        if (flag > 0){
            return "Grant role failed!\n" +
                    "Role or Permission isn't exist!";
        }

        int i = rolePermissionMapper.insert(role_permission);

        if(i>0)
            return "Grant user success!";
        else
            return "Grant user failed!";
    }

    @DeleteMapping("role/grant")
    public String revokeRole(int roleid,int permissionid) {
        QueryWrapper<sysRolePermission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role_id",roleid);
        queryWrapper.eq("permission_Id",permissionid);
        int i = rolePermissionMapper.delete(queryWrapper);
        if(i>0)
            return "Revoke role success!";
        else
            return "Revoke role failed!\n" +
                    "Role or Permission isn't exist!";
    }
}
