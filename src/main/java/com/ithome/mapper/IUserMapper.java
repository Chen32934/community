package com.ithome.mapper;

import com.ithome.domain.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface IUserMapper {

    @Insert("insert into user(ACCOUNT_ID,NAME,TOKEN,GMT_CREATE,GMT_MODIFIED,AVATAR_URL) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarurl})")
    public void InsertUser(User u);

    @Select("select * from user where token=#{token} ")
     User findByToken(@Param("token") String token);

    /**
     * 暂时修改可能有多条记录  待修复bug
     * @param creator
     * @return
     */
    @Select("select * from user  where ACCOUNT_ID=#{creator}")
    User findById(@Param("creator") Integer creator);

    @Update("UPDATE  user set NAME=#{name},TOKEN=#{token},GMT_MODIFIED=#{gmtCreate},AVATAR_URL=#{avatarurl} where ACCOUNT_ID=#{accountId}")
    void UpdateUser(User user);
}
