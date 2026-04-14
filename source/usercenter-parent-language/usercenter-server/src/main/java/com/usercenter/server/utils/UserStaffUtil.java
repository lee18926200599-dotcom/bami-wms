package com.usercenter.server.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.org.client.feign.StaffFeign;
import com.org.permission.common.org.dto.StaffInfoDto;
import com.org.permission.common.query.BatchQueryParam;
import com.common.util.message.RestMessage;
import com.common.base.enums.BooleanEnum;
import com.common.framework.web.SpringBeanLoader;
import com.usercenter.server.entity.BaseUserStaffMap;
import com.usercenter.server.service.IBaseUserStaffMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 人员工具类,静态方法
 */

public class UserStaffUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserStaffUtil.class);

    /**
     * 用户人员映射
     */
    private static IBaseUserStaffMapService userStaffMapApi;

    /**
     * 人员服务
     */
    private static StaffFeign staffFeign;

    /**
     * id字段:id
     */
    public static final String ID_FILED_ID = "id";

    /**
     * 用户id字段:userId
     */
    public static final String STAFF_ID_FILED_ARCHIVES_ID = "archivesId";

    /**
     * 用户id字段:userId
     */
    public static final String STAFF_NAME_FILED_REAL_NAME = "realName";




    /**
     * 设置用户id
     **/
    public static void setStaffInfo(Object object) {
        setStaffInfo(object, ID_FILED_ID, STAFF_ID_FILED_ARCHIVES_ID,STAFF_NAME_FILED_REAL_NAME);
    }


    /**
     * 设置用户id，指定用户id的字段和人员id的字段
     **/
    public static void setStaffInfo(Object object, String idFiledName, String staffIdFiledName, String staffNameFiledName) {
        initApi();
        if (object == null) {
            return;
        }
        try {
            if (ObjectUtils.isEmpty(object)) {
                return;
            }
            if (object instanceof List) {
                List list = (List) object;
                List<Long> ids = Lists.newArrayList();
                for (Object o : list) {
                    Field idField = getStaffIdField(o, idFiledName);
                    Object userId = idField.get(o);
                    ids.add((Long)userId);
                }
                //获取映射信息
                List<BaseUserStaffMap> findResult = userStaffMapApi.findListByCondition(ids, null, null, BooleanEnum.FALSE.getCode());
                if (ObjectUtils.isEmpty(findResult)) {
                    return;
                }
                List<Long> staffIdList = Lists.newArrayList();
                Map<Long, BaseUserStaffMap> userStaffMap = Maps.newHashMap();
                for (BaseUserStaffMap userStaffMapDTO : findResult) {
                    userStaffMap.put(userStaffMapDTO.getUserId(), userStaffMapDTO);
                    staffIdList.add(userStaffMapDTO.getStaffId());
                }
                //获取人员信息
                BatchQueryParam batchQueryParam = new BatchQueryParam();
                batchQueryParam.setIds(staffIdList);
                RestMessage<List<StaffInfoDto>> restMessage = staffFeign.batchQueryStaffsInfo(batchQueryParam);
                if (!restMessage.isSuccess() || ObjectUtils.isEmpty(restMessage.getData())) {
                    return;
                }
                Map<Long, StaffInfoDto> staffMap = Maps.newHashMap();
                for (StaffInfoDto staffInfoDto : restMessage.getData()) {
                    staffMap.put(staffInfoDto.getId(), staffInfoDto);
                }
                for (Object o : list) {
                    Field idField = getStaffIdField(o, idFiledName);
                    Object userId = idField.get(o);
                    BaseUserStaffMap userStaffMapDTO = userStaffMap.get(userId);
                    if(userStaffMapDTO==null){
                        continue;
                    }
                    StaffInfoDto staffInfoDto = staffMap.get(userStaffMapDTO.getStaffId());
                    set(o, userStaffMapDTO, staffInfoDto, staffIdFiledName, staffNameFiledName);
                }
            } else {
                Field idField = getStaffIdField(object, idFiledName);
                Object userId = idField.get(object);
                Long userIdLong;
                if (userId instanceof Long) {
                    userIdLong = (Long) userId;
                } else {
                    userIdLong = (Long) userId;
                }
                BaseUserStaffMap query = new BaseUserStaffMap();
                query.setUserId(userIdLong);
                BaseUserStaffMap finResult = userStaffMapApi.findOneByCondition(query);
                if (finResult != null) {
                    BatchQueryParam batchQueryParam = new BatchQueryParam();
                    batchQueryParam.setIds(Lists.newArrayList(finResult.getStaffId()));
                    RestMessage<List<StaffInfoDto>> staffRestMessage = staffFeign.batchQueryStaffsInfo(batchQueryParam);
                    StaffInfoDto staffInfoDto = null;
                    if (staffRestMessage.isSuccess() && !ObjectUtils.isEmpty(staffRestMessage.getData())) {
                        staffInfoDto = staffRestMessage.getData().get(0);
                    }
                    set(object, finResult, staffInfoDto, staffIdFiledName, staffNameFiledName);
                }
            }
        } catch (Exception e) {
            LOGGER.error("设置用户id出错：", e);
        }
    }

    /**
     * 获取人员id字段
     * @param object 目标对象
     * @param idFiledName 人员id字段名
     * @throws NoSuchFieldException
     */
    private static Field getStaffIdField(Object object, String idFiledName) throws NoSuchFieldException {
        Field idField = null;
        try {
            idField = object.getClass().getDeclaredField(idFiledName);
        } catch (NoSuchFieldException e) {
            LOGGER.info("在类{}中找不到字段:{}",object.getClass().getName(), idFiledName);
        }
        if (idField == null) {
            try {
                idField = object.getClass().getSuperclass().getDeclaredField(idFiledName);
            } catch (Exception e) {
                LOGGER.info("在类{}中找不到字段:{}",object.getClass().getSuperclass().getName(), idFiledName);
            }
        }
        if (idField == null) {
            try {
                idField = object.getClass().getSuperclass().getSuperclass().getDeclaredField(idFiledName);
            } catch (Exception e) {
                LOGGER.info("在类{}中找不到字段:{}",object.getClass().getSuperclass().getSuperclass().getName(), idFiledName);
            }
        }
        if (idField == null){
            throw new NoSuchFieldException(idFiledName);
        }

        idField.setAccessible(true);
        return idField;
    }

    /**
     * 设置userId到目标实体
     * @param object 目标实体
     * @param userStaffMapDTO 映射信息
     * @param staffInfoDto 人员信息
     * @param staffIdFiledName 目标实体中的staffId字段
     * @param staffNameFiledName 目标实体中的staffName字段
     **/
    private static void set(Object object, BaseUserStaffMap userStaffMapDTO, StaffInfoDto staffInfoDto, String staffIdFiledName, String staffNameFiledName) throws NoSuchFieldException, IllegalAccessException {
        try {
            //人员id
            if (userStaffMapDTO != null) {
                Field staffIdField = object.getClass().getDeclaredField(staffIdFiledName);
                staffIdField.setAccessible(true);
                LOGGER.info("用户中保存的人员id:{},映射中的人员id{}",staffIdField.get(object),userStaffMapDTO.getStaffId());
                if (staffIdField.getType().equals(Integer.class)) {
                    staffIdField.set(object, userStaffMapDTO.getStaffId());
                } else {
                    staffIdField.setLong(object, new Long(userStaffMapDTO.getStaffId().longValue()));
                }
            }
            //人员名称
            if (staffInfoDto != null) {
                Field staffNameField = object.getClass().getDeclaredField(staffNameFiledName);
                staffNameField.setAccessible(true);
                staffNameField.set(object, staffInfoDto.getRealname());
            }
        } catch (NoSuchFieldException e) {
            LOGGER.info("类{}中没有字段：{}", object.getClass().getName(), staffIdFiledName);
        }
    }

    /**
     * 初始化api
     **/
    private static void initApi() {
        if (userStaffMapApi == null) {
            IBaseUserStaffMapService bean = SpringBeanLoader.getSpringBean(IBaseUserStaffMapService.class);
            userStaffMapApi = bean;
        }
        if (staffFeign == null) {
            StaffFeign bean = SpringBeanLoader.getSpringBean(StaffFeign.class);
            staffFeign = bean;
        }
    }

}
