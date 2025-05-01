package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_team")
public class Team {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String description;

    private Long leaderId;

    private Integer memberCount;

    private Date createTime;

    private Date updateTime;
}    