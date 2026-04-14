package com.org.permission.server.org.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.common.util.message.RestMessage;
import com.common.framework.web.SpringBeanLoader;
import com.usercenter.client.feign.UserStaffMapFeign;
import com.usercenter.common.dto.UserStaffMapDto;
import com.usercenter.common.dto.request.UserStaffMapFindListReq;
import com.usercenter.common.dto.request.UserStaffMapFindOneReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 人员工具类,静态方法
 */

public class StaffUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaffUtil.class);

    /**
     * 用户人员映射
     */
    private static UserStaffMapFeign userStaffMapFeign;

    /**
     * id字段:id
     */
    public static final String ID_FILED_ID = "id";

    /**
     * 用户id字段:userId
     */
    public static final String USER_ID_FILED_USER_ID = "userId";


    /**
     * 设置用户id
     **/
    public static void setUserId(Object object) {
        setUserId(object, ID_FILED_ID, USER_ID_FILED_USER_ID);
    }


    /**
     * 设置用户id，指定用户id的字段和人员id的字段
     **/
    public static void setUserId(Object object, String idFiledName, String userIdFiledName) {
        if (object == null) {
            return;
        }
        initApi();
        try {
            if (ObjectUtils.isEmpty(object)) {
                return;
            }
            if (object instanceof List) {
                List list = (List) object;
                List<Long> ids = Lists.newArrayList();
                for (Object o : list) {
                    Field idField = getStaffIdField(o, idFiledName);
                    Object staffId = idField.get(o);
                    ids.add((Long) staffId);
                }
                RestMessage<List<UserStaffMapDto>> findResult = userStaffMapFeign.findList(UserStaffMapFindListReq.createStaffQuery(ids));
                if (!findResult.isSuccess() || ObjectUtils.isEmpty(findResult.getData())) {
                    return;
                }
                Map<Long, UserStaffMapDto> userStaffMap = Maps.newHashMap();
                for (UserStaffMapDto userStaffMapDTO : findResult.getData()) {
                    userStaffMap.put(userStaffMapDTO.getStaffId(), userStaffMapDTO);
                }
                for (Object o : list) {
                    Field idField = getStaffIdField(o, idFiledName);
                    Object staffId = idField.get(o);
                    UserStaffMapDto userStaffMapDTO = userStaffMap.get(staffId);
                    set(o, userStaffMapDTO, userIdFiledName);
                }
            } else {
                Field idField = getStaffIdField(object, idFiledName);
                Object staffId = idField.get(object);
                Long staffIdInteger;
                if (staffId instanceof Long) {
                    staffIdInteger = (Long) staffId;
                } else {
                    staffIdInteger = (Long) staffId;
                }
                RestMessage<UserStaffMapDto> userStaffMapResult = userStaffMapFeign.findOne(UserStaffMapFindOneReq.createStaffQuery(staffIdInteger));
                if (userStaffMapResult != null) {
                    UserStaffMapDto userStaffMapDTO = userStaffMapResult.getData();
                    set(object, userStaffMapDTO, userIdFiledName);
                }
            }
        } catch (Exception e) {
            LOGGER.error("设置用户id出错：", e);
        }
    }

    /**
     * 获取人员id字段
     *
     * @param object      目标对象
     * @param idFiledName 人员id字段名
     * @throws NoSuchFieldException
     */
    private static Field getStaffIdField(Object object, String idFiledName) throws NoSuchFieldException {
        Field idField = null;
        try {
            idField = object.getClass().getDeclaredField(idFiledName);
        } catch (NoSuchFieldException e) {
            LOGGER.info("在类{}中找不到字段:{}", object.getClass().getName(), idFiledName);
        }
        if (idField == null) {
            try {
                idField = object.getClass().getSuperclass().getDeclaredField(idFiledName);
            } catch (Exception e) {
                LOGGER.info("在类{}中找不到字段:{}", object.getClass().getSuperclass().getName(), idFiledName);
            }
        }
        if (idField == null) {
            try {
                idField = object.getClass().getSuperclass().getSuperclass().getDeclaredField(idFiledName);
            } catch (Exception e) {
                LOGGER.info("在类{}中找不到字段:{}", object.getClass().getSuperclass().getSuperclass().getName(), idFiledName);
            }
        }
        if (idField == null) {
            throw new NoSuchFieldException(idFiledName);
        }

        idField.setAccessible(true);
        return idField;
    }

    /**
     * 设置userId到目标实体
     *
     * @param object          目标实体
     * @param userStaffMapDTO 映射信息
     * @param userIdFiledName 目标实体中的userId字段
     **/
    private static void set(Object object, UserStaffMapDto userStaffMapDTO, String userIdFiledName) throws NoSuchFieldException, IllegalAccessException {
        Field userIdField = null;
        try {
            userIdField = object.getClass().getDeclaredField(userIdFiledName);
        } catch (NoSuchFieldException e) {
            LOGGER.info("类{}中没有字段：{}", object.getClass().getName(), userIdFiledName);
        }
        if (userIdField == null) {
            userIdField = object.getClass().getSuperclass().getDeclaredField(userIdFiledName);
        }
        userIdField.setAccessible(true);
        Long userId = null;
        if (userStaffMapDTO != null) {
            userId = userStaffMapDTO.getUserId();
        }
        LOGGER.info("人员保存的用户id:{},映射中的用户id{}", userIdField.get(object), userId);
        if (userIdField.getType().equals(Integer.class)) {
            userIdField.set(object, userId);
        } else if (userIdField.getType().equals(Long.class)) {
            userIdField.set(object, userId == null ? null : new Long(userId));
        } else if (userIdField.getType().equals(String.class)) {
            userIdField.set(object, userId == null ? null : userId.toString());
        }

    }

    /**
     * 初始化api
     **/
    private static void initApi() {
        if (userStaffMapFeign == null) {
            UserStaffMapFeign bean = (UserStaffMapFeign) SpringBeanLoader.getSpringBean(UserStaffMapFeign.class);
            userStaffMapFeign = bean;
        }
    }

}
