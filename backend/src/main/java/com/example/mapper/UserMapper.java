package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    // 新增方法
    @Update("UPDATE user SET role = #{role} WHERE id = #{userId}")
    void updateRole(Long userId, Integer role);
}
