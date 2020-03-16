package com.ithome.mapper;

import com.ithome.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IUserMapper {

    @Insert("insert into user(ACCOUNT_ID,NAME,TOKEN,GMT_CREATE,GMT_MODIFIED) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    public void InsertUser(User u);

    @Select("select * from user where token=#{token} ")
     User findByToken(@Param("token") String token);
}
