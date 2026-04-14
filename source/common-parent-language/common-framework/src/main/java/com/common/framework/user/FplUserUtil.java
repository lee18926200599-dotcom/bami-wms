package com.common.framework.user;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.common.base.entity.CurrentUser;
import com.common.framework.filter.ParameterRequestWrapper;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@UtilityClass
public class FplUserUtil {

    /**
     * header中的当前用户id
     */
    public static final String HEADER_USER_ID = "Userid";
    /**
     * header中的当前用户id
     */
    public static final String HEADER_USER_NAME = "Username";
    /**
     * header中的集团id
     */
    public static final String HEADER_GROUP_ID = "Groupid";
    /**
     * header中的组织id
     */
    public static final String HEADER_ORG_ID = "Orgid";
    /**
     * header中的组织名称
     */
    public static final String HEADER_ORG_NAME = "Orgname";

    /**
     * header中的仓库ID
     */
    public static final String HEADER_WAREHOUSE_ID = "Warehouseid";
    /**
     * header中的仓库CODE
     */
    public static final String HEADER_WAREHOUSE_CODE = "Warehousecode";
    /**
     * header中的仓库名称
     */
    public static final String HEADER_WAREHOUSE_NAME = "Warehousename";

    private final ThreadLocal<CurrentUser> threadLocal = new TransmittableThreadLocal<>();

    public void setValue(CurrentUser currentUser) {
        threadLocal.set(currentUser);
    }

    public CurrentUser getValue() {
        CurrentUser currentUser = threadLocal.get();
        return currentUser;
    }

    public void remove() {
        threadLocal.remove();
    }

    /**
     * 获取当前登陆用户
     */
    public static CurrentUser getCurrentUser() {
        CurrentUser currentUser = null;
        try {
            ParameterRequestWrapper request = (ParameterRequestWrapper) ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
            currentUser = new CurrentUser();
            if (StringUtils.isNotBlank(request.getHeader(HEADER_USER_ID))) {
                currentUser.setUserId(Long.valueOf(request.getHeader(HEADER_USER_ID)));
            }
            if (StringUtils.isNotBlank(request.getHeader(HEADER_USER_NAME))) {
                currentUser.setUserName(strDecode(request.getHeader(HEADER_USER_NAME)));
            }
            if (StringUtils.isNotBlank(request.getHeader(HEADER_ORG_ID))) {
                currentUser.setServiceProviderId(Long.valueOf(request.getHeader(HEADER_ORG_ID)));
            }
            if (StringUtils.isNotBlank(request.getHeader(HEADER_ORG_NAME))) {
                currentUser.setServiceProviderName(strDecode(request.getHeader(HEADER_ORG_NAME)));
            }
            if (StringUtils.isNotBlank(request.getHeader(HEADER_GROUP_ID))) {
                currentUser.setGroupId(Long.valueOf(request.getHeader(HEADER_GROUP_ID)));
            }
            if (StringUtils.isNotBlank(request.getHeader(HEADER_WAREHOUSE_ID))) {
                currentUser.setWarehouseId(Long.valueOf(request.getHeader(HEADER_WAREHOUSE_ID)));
            }
            if (StringUtils.isNotBlank(request.getHeader(HEADER_WAREHOUSE_CODE))) {
                currentUser.setWarehouseCode(strDecode(request.getHeader(HEADER_WAREHOUSE_CODE)));
            }
            if (StringUtils.isNotBlank(request.getHeader(HEADER_WAREHOUSE_NAME))) {
                currentUser.setWarehouseName(strDecode(request.getHeader(HEADER_WAREHOUSE_NAME)));
            }
        } catch (Exception e) {
            //不中断
        }
        return currentUser;
    }

    /**
     * 获取当前登陆用户的id
     */
    public static Long getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String userId = request.getHeader(HEADER_USER_ID);
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        return Long.valueOf(userId);
    }

    /**
     * 获取当前登陆用户名
     */
    public static String getUserName() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String userName = request.getHeader(HEADER_USER_NAME);
        return strDecode(userName);
    }


    /**
     * 获取当前登陆用户集团id
     */
    public static Long getGroupId() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String groupId = request.getHeader(HEADER_GROUP_ID);
        if (StringUtils.isBlank(groupId)) {
            return null;
        }
        return Long.valueOf(groupId);
    }

    /**
     * 获取当前登陆用户组织id
     */
    public static Long getOrgId() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String orgId = request.getHeader(HEADER_ORG_ID);
        if (StringUtils.isBlank(orgId)) {
            return null;
        }
        return Long.valueOf(orgId);
    }

    /**
     * 获取当前登陆仓库id
     */
    public static Long getWarehouseId() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String warehouseId = request.getHeader(HEADER_WAREHOUSE_ID);
        if (StringUtils.isBlank(warehouseId)) {
            return null;
        }
        return Long.valueOf(warehouseId);
    }

    /**
     * 获取当前登陆用户的id
     */
    public static String getToken() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token.replace("Bearer ", "");
    }

    private String strDecode(String str) {
        try {
            URLDecoder decoder = new URLDecoder();
            return decoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

}
