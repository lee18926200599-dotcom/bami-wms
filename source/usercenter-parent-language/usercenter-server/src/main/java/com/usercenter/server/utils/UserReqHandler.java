package com.usercenter.server.utils;

import com.usercenter.common.dto.request.UserApiReq;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class UserReqHandler {

    public <T> T getRequestBody(Class<T> entityClass, UserApiReq userApiReq, String methodName) {
        return entityClass.cast(userApiReq.getRequestBody());
    }

    /**
     * 请求信息
     */
    class ReqInfo implements Serializable {

        private static final long serialVersionUID = -227935951477837414L;

        private Long timeStamp;

        public ReqInfo(Long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public Long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(Long timeStamp) {
            this.timeStamp = timeStamp;
        }
    }
}
