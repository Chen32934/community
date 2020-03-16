package com.ithome.mapper;

import com.ithome.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMapper {

    @Insert("insert into user(ACCOUNT_ID,NAME,TOKEN,GMT_CREATE,GMT_MODIFIED) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    public void InsertUser(User u);

}
