package com.zbinyds.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbinyds.security.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zbinyds
 * @description 针对表【t_user(用户表)】的数据库操作Mapper
 * @createDate 2023-06-26 18:37:09
 * @Entity com.zbinyds.security.pojo.User
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

}




