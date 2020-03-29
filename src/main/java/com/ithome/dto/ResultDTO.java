package com.ithome.dto;

import com.ithome.exception.CustomizeErrorCode;
import com.ithome.exception.CustomizeException;
import lombok.Data;

/**
 * 泛型类
 * @param <T>
 */
@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    /**
     * 拓展ResultDTO
     */
    public static ResultDTO errorOf(Integer code,String  message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode){
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    /**
     * 带参数的okOf
     * @param t
     * @return
     */
    public static <T> ResultDTO okOf(T t){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }
}
