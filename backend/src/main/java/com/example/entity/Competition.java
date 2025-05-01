package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_competition")
public class Competition {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String description;

    private String organizer;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}    