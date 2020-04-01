package com.ithome.service;

import com.ithome.domain.Question;
import com.ithome.domain.QuestionExample;
import com.ithome.domain.User;
import com.ithome.domain.UserExample;
import com.ithome.dto.QuestionDTO;
import com.ithome.dto.QuestionQueryDTO;
import com.ithome.dto.pagInationDTO;
import com.ithome.exception.CustomizeErrorCode;
import com.ithome.exception.CustomizeException;
import com.ithome.mapper.QuestionExtMapper;
import com.ithome.mapper.QuestionMapper;
import com.ithome.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 组装的作用
 * mapper controller
 */
@Service
public class QuestionService {



    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;



    public pagInationDTO list(Integer pageSize,Integer pageNum,String questiontype, String search) {
        if (StringUtils.isNotBlank(search)){
            String [] tags=StringUtils.split(search," ");
            search= Arrays.stream(tags).collect(Collectors.joining("|"));
        }
        QuestionQueryDTO questionQueryDTO=new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        questionQueryDTO.setQuestiontype(questiontype);
        Integer totalCount =questionExtMapper.searchCount(questionQueryDTO);
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
        questionQueryDTO.setPageNum(offSet);
        questionQueryDTO.setPageSize(pageSize);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        pagInationDTO pagInationDTO = new pagInationDTO();
        for (Question question : questions) {
            UserExample userExample=new UserExample();
                userExample.createCriteria().andAccountIdEqualTo(String.valueOf(question.getCreator()));
            List<User> users = userMapper.selectByExample(userExample);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(users.get(0));
            questionDTOList.add(questionDTO);
        }
        pagInationDTO.setData(questionDTOList);
        pagInationDTO.setPagInation(totalPage, pageNum);
        return pagInationDTO;
    }

    public List<Question> listTop(){
        long ldate = System.currentTimeMillis();
        List<Question> listTop=questionExtMapper.selectTop(ldate);
        return listTop;
    }
    public pagInationDTO findByIdPageList(Integer Id, Integer pageNum, Integer pageSize) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(Id);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
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
        questionExample.setOrderByClause("GMT_MODIFIED desc");
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offSet,pageSize));
//        List<Question> questionList = iQuestionMapper.questionFindByIdpageList(Id, offSet, pageSize);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        pagInationDTO pagInationDTO = new pagInationDTO();
        for (Question question : questionList) {
            UserExample userExample=new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(String.valueOf(question.getCreator()));
            List<User> users = userMapper.selectByExample(userExample);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(users.get(0));
            questionDTOList.add(questionDTO);
        }
        pagInationDTO.setData(questionDTOList);
        pagInationDTO.setPagInation(totalPage, pageNum);
        return pagInationDTO;

    }

    public QuestionDTO findQuestionDetailById(Integer id){
        Question question  = questionMapper.selectByPrimaryKey(id);
        if (question==null){
            throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        UserExample userExample=new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(String.valueOf(question.getCreator()));
        List<User> users = userMapper.selectByExample(userExample);
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setUser(users.get(0));
        BeanUtils.copyProperties(question, questionDTO);
        return  questionDTO ;
    };

    public void IncView(Integer id){
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.IncView(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if (questionDTO.getTag()==null){
            return new ArrayList<>();
        }
        String tag = StringUtils.replace(questionDTO.getTag(), "，", ",").replace(",", "|");

        Question question=new Question();
        question.setId(questionDTO.getId());
        question.setTag(tag);
        List<Question> questions=questionExtMapper.selectRelated(question);
        //转换DTO
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questiond = new QuestionDTO();
            BeanUtils.copyProperties(q, questiond);
            return questiond;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
