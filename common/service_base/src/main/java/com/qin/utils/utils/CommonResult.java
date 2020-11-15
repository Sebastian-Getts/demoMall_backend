package com.qin.utils.utils;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sebastian
 * @date 11/15/20
 */
@Data
@Accessors(chain = true)
public class CommonResult {
    private Boolean success;
    private Integer code;
    private String message;

    private Map<String, Object> data = new HashMap<>();

    private CommonResult() {
    }

    public static CommonResult ok() {
        return new CommonResult().setCode(200).setSuccess(true).setMessage("success");
    }

    public static CommonResult error() {
        return new CommonResult().setSuccess(false).setCode(201).setMessage("failed");
    }

    public CommonResult data(String key, String value) {
        data.put(key, value);
        return new CommonResult().setData(data);
    }
}
