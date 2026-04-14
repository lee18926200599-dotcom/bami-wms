package com.org.permission.common.util;

import com.org.permission.common.permission.dto.DataPermissionDto;
import com.org.permission.common.permission.dto.UserDataPermissionDto;
import org.springframework.cglib.beans.BeanCopier;

/**
 * 快速拷贝类
 */
public class BeanCopierUtil {
    /**
     * 数据权限快速拷贝类
     */
    public static BeanCopier DATA_PERMISSION_COPIER = BeanCopier.create(UserDataPermissionDto.class, DataPermissionDto.class, false);
}
