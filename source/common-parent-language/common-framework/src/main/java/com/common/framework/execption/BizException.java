package com.common.framework.execption;

import java.util.HashMap;
import java.util.Map;

public class BizException extends SystemException{
    private Integer errorCode = -1;
    private Map<String, Object> map;

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public Map<String, Object> getMap() {
        return this.map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public static BizException newInstance(String message) {
        BizException be = new BizException(message);
        return be;
    }


    public BizException putParam(String key, Object value) {
        if (this.map == null) {
            this.map = new HashMap();
        }

        this.map.put(key, value);
        return this;
    }

    public BizException putParams(Map<String, Object> params) {
        if (this.map == null) {
            this.map = new HashMap();
        }

        this.map.putAll(params);
        return this;
    }
}
