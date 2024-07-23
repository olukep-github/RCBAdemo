package com.bjca.rcbademo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjca.rcbademo.entity.sysUser;


public interface UserMapper extends BaseMapper<sysUser> {
     sysUser ShowUserMegById(int id);
}
