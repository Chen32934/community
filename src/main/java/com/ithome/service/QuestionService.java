package com.ithome.service;

import com.ithome.domain.Question;
import com.ithome.domain.User;
import com.ithome.dto.QuestionDTO;
import com.ithome.dto.pagInationDTO;
import com.ithome.mapper.IQuestionMapper;
import com.ithome.mapper.IUserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 组装的作用
 * mapper controller
 */
@Service
public class QuestionService {

    @Autowired
    private IUserMapper iUserMapper;

    @Autowired
    private IQuestionMapper iQuestionMapper;

    public pagInationDTO list(Integer pageNum, Integer pageSize) {
        Integer totalCount = iQuestionMapper.count();
        Integer totalPage;
        if (totalCount % pageSize == 0) {
            totalPage = totalCount / pageSize;
        } else {
            totalPage = totalCount / pageSize + 1;
        }
        if (pageNum > totalPage) {
            pageNum = totalPage;
        } else if (pageNum < 1) {
            pageNum = 1;

        }
        Integer offSet = pageSize * (pageNum - 1);
        List<Question> questions = iQuestionMapper.pageList(offSet, pageSize);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        pagInationDTO pagInationDTO = new pagInationDTO();
        for (Question question : questions) {
            User user = iUserMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pagInationDTO.setQuestion(questionDTOList);
        pagInationDTO.setPagInation(totalCount, pageNum, pageSize);
        return pagInationDTO;
    }

    public pagInationDTO findByIdPageList(Integer Id, Integer pageNum, Integer pageSize) {
        Integer totalCount = iQuestionMapper.FindByIdListcount(Id);
        Integer totalPage;
        if (totalCount % pageSize == 0) {
            totalPage = totalCount / pageSize;
        } else {
            totalPage = totalCount / pageSize + 1;
        }
        if (pageNum > totalPage) {
            pageNum = totalPage;
        } else if (pageNum < 1) {
            pageNum = 1;
        }
        Integer offSet = pageSize * (pageNum - 1);
        List<Question> questionList = iQuestionMapper.questionFindByIdpageList(Id, offSet, pageSize);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        pagInationDTO pagInationDTO = new pagInationDTO();
        for (Question question : questionList) {
            User user = iUserMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pagInationDTO.setQuestion(questionDTOList);
        pagInationDTO.setPagInation(totalCount, pageNum, pageSize);
        return pagInationDTO;

    }

    public QuestionDTO findQuestionDetailById(Integer id){
        Question question  = iQuestionMapper.findQuestionDetailById(id);
        User user = iUserMapper.findById(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setUser(user);
        BeanUtils.copyProperties(question, questionDTO);
        return  questionDTO ;
    };

}
