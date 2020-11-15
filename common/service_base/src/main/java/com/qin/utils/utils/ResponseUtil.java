package com.qin.utils.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

/**
 * @author sebastian
 * @date 11/15/20
 */
public class ResponseUtil {

    public static void out(HttpServletResponse response, CommonResult commonResult) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value(), commonResult.getMessage());
    }
}
