package com.org.permission.server.org.validator;

import com.common.language.util.I18nUtils;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import com.org.permission.server.org.bean.OrgTypeBean;
import com.org.permission.server.org.mapper.GroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 生成集团名字冲突校验类
 */
@Component(value = "addGroupValidator")
public class AddGroupValidator {
    @Resource
    private GroupMapper groupMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(AddGroupValidator.class);
    public void validate(String groupName){
        if (groupName == null) {
            LOGGER.warn("add group req param is null.");
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.group.name.null"));
        }
        final List<OrgTypeBean> orgTypeBeans = groupMapper.queryGroupByNameLock(groupName);
        if (!CollectionUtils.isEmpty(orgTypeBeans)) {
            LOGGER.warn("group name conflic,param:{}.", groupName);
            throw new OrgException(OrgErrorCode.REQ_PARAM_ERROR_CODE, I18nUtils.getMessage("org.validator.group.name.exist"));
        }
    }
}
