package com.ithome.mapper;

import com.ithome.domain.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface IQuestionMapper {



    @Insert("insert into question(title,description,GMT_CREATE,GMT_MODIFIED,creator,COMMENT_COUNT,VIEW_COUNT,LIKE_COUNT,tag,)" +
            " values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    public void create(Question question);

    @Select("select * from Question order by GMT_CREATE desc")
     public List<Question> list();

    @Select("select * from Question order by GMT_CREATE desc limit #{pageNum},#{pageSize}")
    public List<Question> pageList(@Param("pageNum") Integer pageNum, @Param("pageSize")  Integer pageSize);

    @Select("select  count(1) from Question")
    Integer count();

    @Select("select  count(1) from Question where creator=#{id}")
    Integer FindByIdListcount(Integer id);

    @Select("select  *  from Question where creator=#{id} order by GMT_CREATE desc  limit #{pageNum},#{pageSize}")
    List<Question> questionFindByIdpageList(@Param("id") Integer id,@Param("pageNum") Integer pageNum, @Param("pageSize")  Integer pageSize);

    @Select("select * from Question  where id=#{id}")
    Question findQuestionDetailById(Integer id);

    @Update("update Question set GMT_MODIFIED=#{gmtModified},tag=#{tag},title=#{title},description=#{description} where id=#{id}")
    void updateQuestion(Question question);
}
