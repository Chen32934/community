package com.ithome.service;

import com.ithome.domain.*;
import com.ithome.dto.NotifyDTO;
import com.ithome.dto.pagInationDTO;
import com.ithome.enums.NotifyStatusEnum;
import com.ithome.enums.NotifyTypeEnum;
import com.ithome.exception.CustomizeErrorCode;
import com.ithome.exception.CustomizeException;
import com.ithome.mapper.NotifyMapper;
import com.ithome.mapper.UserMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class NotifyService {

    @Autowired
    private NotifyMapper notifyMapper;

    @Autowired
    private UserMapper userMapper;


    /**
     * 通知列表
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public pagInationDTO notifyListPage(Integer userId, Integer pageNum, Integer pageSize) {

        pagInationDTO<NotifyDTO> pagInationDTO = new pagInationDTO<NotifyDTO>();

        NotifyExample notifyExample = new NotifyExample();
        notifyExample.createCriteria().andReceiverEqualTo(userId);
        notifyExample.setOrderByClause("GMT_CREATE desc");
        Integer totalCount = (int) notifyMapper.countByExample(notifyExample);
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
        pagInationDTO.setPagInation(totalPage, pageNum);
        Integer offSet = pageSize * (pageNum - 1);
//        notifyExample.setOrderByClause("GMT_MODIFIED desc");
        List<Notify> notifies = notifyMapper.selectByExampleWithRowbounds(notifyExample, new RowBounds(offSet, pageSize));
        if (notifies.size() == 0) {
            return pagInationDTO;
        }
        //通知集合
        List<NotifyDTO> notifyDTOS = new ArrayList<>();

        for (Notify notify : notifies) {
            NotifyDTO notifyDTO = new NotifyDTO();
            BeanUtils.copyProperties(notify, notifyDTO);
            notifyDTO.setTypeName(NotifyTypeEnum.nameOfType(notify.getType()));
            notifyDTOS.add(notifyDTO);
        }
        pagInationDTO.setData(notifyDTOS);
        return pagInationDTO;
    }

    public Integer unreadCount(Integer accountId) {
        NotifyExample notifyExample = new NotifyExample();
        notifyExample.createCriteria().andReceiverEqualTo(accountId).andStatusEqualTo(NotifyStatusEnum.UNREAD.getStatus());
        long count = notifyMapper.countByExample(notifyExample);
        return Integer.valueOf(String.valueOf(count));

    }

    public NotifyDTO read(Integer id, User user) {
        Notify notify = notifyMapper.selectByPrimaryKey(id);
        Integer accountId = user.getAccountId();
        Integer receiver = notify.getReceiver();

        if (!accountId.equals(receiver)){
            throw  new CustomizeException(CustomizeErrorCode.NOTREAD_Notification);
        }
        if (notify==null){
            throw  new CustomizeException(CustomizeErrorCode.Notification_NOTALL);
        }
        notify.setStatus(NotifyStatusEnum.READ.getStatus());
        notifyMapper.updateByPrimaryKey(notify);
        NotifyDTO notifyDTO = new NotifyDTO();
        BeanUtils.copyProperties(notify, notifyDTO);
        notifyDTO.setTypeName(NotifyTypeEnum.nameOfType(notify.getType()));
        return notifyDTO;
    }
}
