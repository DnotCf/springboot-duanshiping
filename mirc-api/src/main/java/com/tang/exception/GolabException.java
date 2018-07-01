package com.tang.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GolabException {

    private static final Logger LOG = LoggerFactory.getLogger(GolabException.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String globaException(HttpServletResponse response, Exception e) {
        LOG.error(e.getMessage());
        return "error";
    }


}
