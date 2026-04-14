package com.usercenter.server.entity;

import lombok.Data;

import java.util.List;


@Data
public class BaseUserAndDetail extends BaseUser{
    List<BaseUserDetail> detailList;
}
