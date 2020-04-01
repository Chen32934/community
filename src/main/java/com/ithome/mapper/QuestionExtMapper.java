package com.ithome.mapper;

import com.ithome.domain.Question;
import com.ithome.dto.QuestionQueryDTO;

import java.util.List;

public interface QuestionExtMapper {
  /**
   * 浏览数
   * @param record
   * @return
   */
   int IncView(Question record);

   int IncCommentCount(Question record);

   List<Question> selectRelated(Question question);

   Integer searchCount(QuestionQueryDTO questionQueryDTO);


    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
    List<Question> selectTop(Long systemdate);


}