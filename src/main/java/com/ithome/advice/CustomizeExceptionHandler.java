package com.ithome.advice;

import com.alibaba.fastjson.JSON;
import com.ithome.dto.ResultDTO;
import com.ithome.exception.CustomizeErrorCode;
import com.ithome.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handler(HttpServletRequest request, Throwable e, Model model, HttpServletRequest req, HttpServletResponse rep) {

        String contentType = req.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            //返回JSON
            if (e instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SRVER_EXCEPTION);
            }
            try {
                rep.setContentType("application/json");
                rep.setStatus(200);
                rep.setCharacterEncoding("utf-8");
                PrintWriter writer = rep.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {

            }
            return null;
        } else {
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SRVER_EXCEPTION.getMessage());
            }
            return new ModelAndView("error");
        }

    }


}
