package com.bjca.rcbademo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class sysRole {

    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String rolename;

    @TableField(fill = FieldFill.INSERT)
    private Date createtime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatetime;

    private int isDelete;

    @TableField(exist = false)
    private List<sysPermission> permissions;
}
