package com.basedata.server.controller;

import com.basedata.common.dto.DictionaryInfoDto;
import com.basedata.common.query.BaseDictionaryBatchQuery;
import com.basedata.common.query.BaseDictionaryQuery;
import com.basedata.common.vo.BaseBatchDictionaryVo;
import com.basedata.common.vo.BaseDictionaryVo;
import com.basedata.server.query.DictionaryInfoQuery;
import com.basedata.server.service.DictionaryInfoService;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@Api(tags = "数据字典")
@RestController
@RequestMapping("/v1/dictionaryInfo")
public class DictionaryInfoV1Controller {

    @Resource
    private DictionaryInfoService dictionaryInfoService;

    /**
     * 字典主表新增
     * @param  dictionaryInfoDto
     * @return
     */
    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public RestMessage save(@RequestBody DictionaryInfoDto dictionaryInfoDto) {
        try {
            dictionaryInfoService.insert(dictionaryInfoDto);
            return RestMessage.doSuccess(I18nUtils.getMessage("base.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

    /**
     * 字典分页查询
     * @param  dictionaryInfoQuery
     * @return
     */
    @ApiOperation(value = "字典分页查询")
    @PostMapping("/queryPageList")
    public RestMessage<PageInfo<DictionaryInfoDto>> queryPageList(@RequestBody DictionaryInfoQuery dictionaryInfoQuery) {
        try {
            return RestMessage.doSuccess(dictionaryInfoService.queryPageList(dictionaryInfoQuery));
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }


    /**
     * 客户修改
     * @param  dictionaryInfoDto
     * @return
     */
    @ApiOperation(value = "字典修改")
    @PostMapping("/update")
    public RestMessage update(@RequestBody DictionaryInfoDto dictionaryInfoDto) {
        try {
            dictionaryInfoService.updateById(dictionaryInfoDto);
            return RestMessage.doSuccess(I18nUtils.getMessage("base.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

    @ApiOperation(value = "字典查询")
    @PostMapping("/common/query")
    public RestMessage<List<BaseDictionaryVo>> queryDictionary(@RequestBody BaseDictionaryQuery dictionaryInfoQuery) {
        List<BaseDictionaryVo> list = dictionaryInfoService.queryDictionary(dictionaryInfoQuery);
        return RestMessage.querySuccess(list);
    }

    @ApiOperation(value = "查询多个字典")
    @PostMapping("/common/queryList")
    public RestMessage<List<BaseBatchDictionaryVo>> queryDictionaryList(@RequestBody BaseDictionaryBatchQuery dictionaryInfoQuery) {
        dictionaryInfoQuery.setGroupId(FplUserUtil.getGroupId());
        List<BaseBatchDictionaryVo> dictionaryVo = dictionaryInfoService.queryDictionaryList(dictionaryInfoQuery);
        return RestMessage.querySuccess(dictionaryVo);
    }

}
