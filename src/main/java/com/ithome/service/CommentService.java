package com.ithome.service;

import com.ithome.domain.*;
import com.ithome.dto.CommentSelectDTO;
import com.ithome.enums.CommentTypeEnum;
import com.ithome.enums.NotifyStatusEnum;
import com.ithome.enums.NotifyTypeEnum;
import com.ithome.exception.CustomizeErrorCode;
import com.ithome.exception.CustomizeException;
import com.ithome.mapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 评论时通知
     */
    @Autowired
    private NotifyMapper notifyMapper;

    @Transactional
    public void Insert(Comment comment, User commentator) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExit(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_NOT_FOUND);
        }

        if (comment.getType() == CommentTypeEnum.QUESTION.getType()) {
            //根据一级评论ID查询问题是否存在
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //回复------问题
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.IncCommentCount(question);
            //通知
            CreateNotify(comment, commentator.getName(), question.getCreator(), question.getTitle(), NotifyTypeEnum.Reply_Questions, comment.getParentId());
        } else {
            //根据评论父ID查询一级评论是否存在
            Comment DbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (DbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            //回复------评论
            commentMapper.insert(comment);
            //增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.IncCount(parentComment);
            CreateNotify(comment, commentator.getName(), DbComment.getCommentator(),DbComment.getContent(), NotifyTypeEnum.Reply_Comment, DbComment.getParentId());
        }

    }

    /**
     * 创建通知
     *
     * @param comment
     * @param notifyName
     * @param receiver
     * @param outerTitle
     * @param notifyTypeEnum
     * @param outerId
     */
    private void CreateNotify(Comment comment, String notifyName, Integer receiver, String outerTitle, NotifyTypeEnum notifyTypeEnum, Integer outerId) {
        Notify notify = new Notify();
        notify.setGmtCreate(System.currentTimeMillis());
        notify.setType(notifyTypeEnum.getType());
        notify.setInformant(comment.getCommentator()); //登录用户（回复者）ID
        notify.setOuterid(outerId);//回复的类型
        notify.setReceiver(receiver);//接收者用户ID
        notify.setNotifireName(notifyName);//接收者用户名字
        notify.setOuterTitle(outerTitle);//接收者用户的标题
        notify.setStatus(NotifyStatusEnum.UNREAD.getStatus());
        notifyMapper.insert(notify);
    }

    public List<CommentSelectDTO> listByTargetId(Integer id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andTypeEqualTo(type.getType())
                .andParentIdEqualTo(Long.valueOf(id));
        commentExample.setOrderByClause("GMT_CREATE desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        //java8 Lambda Stream collect Collectors
        //优雅的将一个对象的集合转化成另一个对象的集合
        // collect_list 不去重而 collect_set 去重。

        //获取评论者（去重）
        Set<String> ommentatorcs = comments.stream().map(comment -> String.valueOf(comment.getCommentator())).collect(Collectors.toSet());
        List<String> userAccountIds = new ArrayList<>();
        userAccountIds.addAll(ommentatorcs);

        //获取评论者（去重）信息并转为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdIn(userAccountIds);
        List<User> users = userMapper.selectByExample(userExample);

        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getAccountId(), user -> user));

        //转换comment为CommentSelectDTO
        List<CommentSelectDTO> commentSelectDTOS = comments.stream().map(comment -> {
            CommentSelectDTO commentSelectDTO = new CommentSelectDTO();
            BeanUtils.copyProperties(comment, commentSelectDTO);
            commentSelectDTO.setUser(userMap.get(comment.getCommentator()));
            return commentSelectDTO;
        }).collect(Collectors.toList());
        return commentSelectDTOS;

    }
}
