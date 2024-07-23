package com.bjca.rcbademo.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjca.rcbademo.dao.PermissionMapper;
import com.bjca.rcbademo.dao.RolePermissionMapper;
import com.bjca.rcbademo.entity.sysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PermissionController {
    @Autowired
    private PermissionMapper permissionMapper;
    private RolePermissionMapper rolePermissionMapper;

    @GetMapping("permission")
    public List<sysPermission> getPermission() {
        List<sysPermission> list = permissionMapper.selectList(null);
        return list;
    }

    @PostMapping("permission")
    public String  addPermission(sysPermission sysPermission) {
        int i = permissionMapper.insert(sysPermission);
        if (i>0)
            return "Add permission success";
        else
            return "Add permission failed";
    }

    @DeleteMapping("permission")
    public String  delPermission(int id) {
        UpdateWrapper<sysPermission> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("is_delete", 1);
        int i = permissionMapper.update(null, updateWrapper);
        if(i>0)
            return "Delete permission success";
        else
            return "Delete permission failed";
    }
}
