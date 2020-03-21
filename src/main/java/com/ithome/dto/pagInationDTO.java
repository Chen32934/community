package com.ithome.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class pagInationDTO {

    private List<QuestionDTO> question;
    private boolean showHomePage;
    private boolean showLastPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer pageNum;  //当前页
    private List<Integer> pages=new ArrayList<>();
    private Integer totalPage;
    public void setPagInation(Integer totalCount, Integer pageNum, Integer pageSize) {
        this.pageNum=pageNum;

        if (totalCount %pageSize == 0) {
            totalPage = totalCount / pageSize;
        } else{
            totalPage = totalCount / pageSize + 1;
        }

        pages.add(pageNum);
        for (int i = 1; i <= 3; i++) {
            if (pageNum-i>0){
                pages.add(0,pageNum-i);
            }
            if (pageNum+i<=totalPage){
                pages.add(pageNum+i);
            }
        }


//是否展示上一页
    if (pageNum==1){
        showLastPage=false;
    }else {
        showLastPage=true;
        showHomePage=true;
    }

//是否展示下一页
        if (pageNum==totalPage){
            showNext=false;
        }else {
            showNext=true;
            showEndPage=true;
        }


}







}
