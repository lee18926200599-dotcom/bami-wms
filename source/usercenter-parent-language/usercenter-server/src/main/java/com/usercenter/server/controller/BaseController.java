package com.usercenter.server.controller;

import com.usercenter.server.utils.UserReqHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired
    protected UserReqHandler userReqHandler;
}
