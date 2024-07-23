package com.bjca.rcbademo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bjca.rcbademo.dao.RoleMapper;
import com.bjca.rcbademo.dao.UserMapper;
import com.bjca.rcbademo.dao.UserRoleMapper;
import com.bjca.rcbademo.entity.sysRole;
import com.bjca.rcbademo.entity.sysUser;
import com.bjca.rcbademo.entity.sysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    @PostMapping("user")
    public String addUser(sysUser user) {
        int i=userMapper.insert(user);
        if(i>0)
            return "Add user success!";
        else
            return "Add user failed!";
    }

    @GetMapping("user")
    public List<sysUser> getUsers() {
        List<sysUser> users=userMapper.selectList(null);
        return users;
    }

    @GetMapping("user/{id}")
    public sysUser getUser(@PathVariable int id) {
        int i = userMapper.selectById(id).getIsDelete();
        if(i>0)
            return null;
         i = userMapper.selectById(id).getId();
        if(i == 0)
            return null;
        return userMapper.ShowUserMegById(id);
    }

    @DeleteMapping("user")
    public String deleteUser(int id) {
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.eq("id",id);
        updateWrapper.set("is_delete",1);
        int i = userMapper.update(null, updateWrapper);
        if (i>0)
            return "Delete user success!";
        else
            return "Delete user failed!";
    }

    @PostMapping("user/grant")
    public String grantUser(int userid,int roleid) {
        sysUserRole user_role=new sysUserRole();
        user_role.setUserId(userid);
        user_role.setRoleId(roleid);

        QueryWrapper<sysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",userid);
        sysUser user=userMapper.selectOne(queryWrapper);
        if (user == null){
            return "Grant user failed!\n" +
                    "User or Role isn't exist!";
        }

        QueryWrapper<sysRole> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.eq("id",roleid);
        sysRole role=roleMapper.selectOne(queryWrapper1);
        if (role == null){
            return "Grant user failed!\n" +
                    "User or Role isn't exist!";
        }

        int flag = 0;
        flag = flag + userMapper.selectOne(queryWrapper).getIsDelete()
                + roleMapper.selectOne(queryWrapper1).getIsDelete();
        if (flag > 0){
            return "Grant user failed!\n" +
                    "User or Role isn't exist!";
        }

        int i = userRoleMapper.insert(user_role);

        if(i>0)
            return "Grant user success!";
        else
            return "Grant user failed!";
    }

    @DeleteMapping("user/grant")
    public String revokeUser(int userid,int roleid) {
        QueryWrapper<sysUserRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userid);
        queryWrapper.eq("role_id",roleid);
        int i = userRoleMapper.delete(queryWrapper);
        if(i>0)
            return "Revoke user success!";
        else
            return "Revoke user failed!\n" +
                    "User or Role isn't exist!";
    }
}
