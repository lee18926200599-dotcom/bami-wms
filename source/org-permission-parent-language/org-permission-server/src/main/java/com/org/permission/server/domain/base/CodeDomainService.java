package com.org.permission.server.domain.base;

import com.common.framework.constants.RedisConstants;
import com.common.framework.number.BaseNoGenerateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 分布式ID领域封装
 */
@Component(value = "codeDomainService")
public class CodeDomainService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeDomainService.class);
    private static final long PLATFORM_ORG_ID = 1L;
    private static final String FPL_ROLE_CODE = "FPL_ROLE_CODE_";

    @Value("${base_org_group_code_biz_code}")
    private String groupCodeBizCode;

    @Value("${base_org_bizunit_code_biz_code}")
    private String bizUnitCodeBizCode;
    @Value(value = "${base_org_dep_code_biz_code}")
    private String depCodeBizCode;

    /**
     * 获取平台级编码
     *
     * @param bizeCode 业务类型
     * @return 编码
     */
    public String getPlatformCode(String bizeCode) {
        return BaseNoGenerateUtil.generateCodeBase(bizeCode, PLATFORM_ORG_ID + "", null, 2);
    }

    public String getStaffCode() {
        return "RY" + BaseNoGenerateUtil.generateCodeBase(RedisConstants.FPL_BOSS_STAFF_CODE, null, "STAFF", 8);
    }

    /**
     * 获取集团级编码
     *
     * @return 编码
     */
    public String getGroupCode() {
        return "JT" + BaseNoGenerateUtil.generateCodeBase(groupCodeBizCode, null, null, 2);
    }

    public String getOrgCode() {
        return "YWDY" + BaseNoGenerateUtil.generateCodeBase(bizUnitCodeBizCode, null, null, 2);
    }

    public String getDepCode() {
        return "BM" + BaseNoGenerateUtil.generateCodeBase(depCodeBizCode, null, null, 2);
    }

    public String getRoleCode(Long groupId) {
        return "R" + BaseNoGenerateUtil.generateCodeBase(FPL_ROLE_CODE, groupId + "", "ROLE", 2);
    }

}
