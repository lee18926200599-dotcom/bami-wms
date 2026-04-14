package com.org.permission.server.org.controller;


import com.basedata.common.dto.DictionaryItemDto;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.github.pagehelper.PageInfo;
import com.org.permission.server.domain.base.DicDomainService;
import com.org.permission.server.domain.crm.CustDomainService;
import com.org.permission.server.domain.crm.CustInfoDto;
import com.org.permission.server.domain.crm.QueryCustReqParam;
import com.org.permission.server.exception.OrgErrorCode;
import com.org.permission.server.exception.OrgException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 通用服务控制器
 */
@RestController
@Api(tags = "0通用接口文档")
@RequestMapping("common")
public class CommonController  {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

	private static final String BUSINESS_TYPE_DIC_CODE = "BUSSINESS_TYPE";
	private static final String BUSINESS_TYPE_DIC_NAME = "业务类型";

	@Resource
	private CustDomainService custDomainService;

	@Resource
	private DicDomainService dicDomainService;

	@ApiOperation(value = "查询客户列表", httpMethod = "POST")
	@PostMapping(value = "/queryCustList")
	public RestMessage<PageInfo<CustInfoDto>> queryCustList(@RequestBody final QueryCustReqParam reqParam) {
		LOGGER.info("query enable crm list request param:{}.", reqParam);
		try {
			final PageInfo<CustInfoDto> custList = custDomainService.queryCustList(reqParam);
			// 解析业务类型
			parseBizTypeId(custList.getList());
			return RestMessage.doSuccess(custList);
		} catch (OrgException oex) {
			LOGGER.info("query enable crm list failed,reqParam:" + reqParam, oex);
			return RestMessage.error(oex.getErrorCode()+"", oex.getMessage());
		} catch (Exception ex) {
			LOGGER.error("query enable crm list error,reqParam:" + reqParam, ex);
			return RestMessage.error(OrgErrorCode.ORG_SYSTEM_ERROR_CODE+"", I18nUtils.getMessage("org.common.system.exception"));
		}
	}

	/**
	 * 解析业务类型ID
	 *
	 * @param custList 客商列表
	 */
	private void parseBizTypeId(List<CustInfoDto> custList) {
		if (CollectionUtils.isEmpty(custList)) {
			return;
		}
		final List<DictionaryItemDto> dicItems = dicDomainService.dicItems(BUSINESS_TYPE_DIC_CODE, BUSINESS_TYPE_DIC_NAME);
		final Map<String, String> itemCodeMap = dicDomainService.convertToMap(dicItems);
		for (CustInfoDto custInfoDto : custList) {
			final String bizTypeId = custInfoDto.getBizTypeId();
			if (StringUtils.isEmpty(bizTypeId)) {
				continue;
			}
			StringBuilder sb = new StringBuilder();
			final String[] split = bizTypeId.split(",");
			String bizTypeName;
			for (String itemCode : split) {
				sb.append(",").append(itemCodeMap.get(itemCode));
			}
			bizTypeName = sb.toString().replaceFirst(",", "");
			custInfoDto.setBizTypeName(bizTypeName);
		}
	}
}
