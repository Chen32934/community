package com.ithome.mapper;

import com.ithome.domain.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IQuestionMapper {



    @Insert("insert into question(title,description,GMT_CREATE,GMT_MODIFIED,creator,COMMENT_COUNT,VIEW_COUNT,LIKE_COUNT,tag,)" +
            " values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    public void create(Question question);

}
