package com.qin.security.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author sebastian
 * @date 11/15/20
 */
@Data
@ApiModel(description = "user entity")
public class User implements Serializable {

    @ApiModelProperty(value = "username")
    private String username;

    @ApiModelProperty(value = "password")
    private String password;

    @ApiModelProperty(value = "nick name")
    private String nickName;

    @ApiModelProperty(value = "salt for password")
    private String salt;

    @ApiModelProperty(value = "is expired")
    private Boolean isExpired;
}
