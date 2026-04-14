package com.basedata.server.controller;

import com.basedata.common.dto.DictionaryItemDto;
import com.basedata.common.query.BaseDictionaryQuery;
import com.basedata.common.vo.DictionaryItemVO;
import com.basedata.server.query.DictionaryItemQuery;
import com.basedata.server.service.DictionaryItemService;
import com.common.framework.user.FplUserUtil;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 集团级字典条目接口文档
 */
@Api(tags = "集团级字典条目接口文档")
@RestController
@RequestMapping("/dictionary-item-group")
public class DictionaryItemGroupController {

    @Resource
    private DictionaryItemService dictionaryItemService;

    /**
     * 字典子表表新增
     *
     * @param dictionaryItemDto
     * @return
     */
    @ApiOperation("新增集团级字典条目")
    @PostMapping("/save")
    public RestMessage save(@RequestBody DictionaryItemDto dictionaryItemDto) {
        try {
            dictionaryItemDto.setGroupId(FplUserUtil.getGroupId());
            dictionaryItemService.insert(dictionaryItemDto);
            return RestMessage.doSuccess(I18nUtils.getMessage("base.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

    /**
     * 字典子表表新增
     *
     * @param dictionaryItemDto
     * @return
     */
    @ApiOperation(value = "修改集团级字典条目", httpMethod = "POST")
    @ApiImplicitParam(name = "dictionaryItemDto", value = "参数", paramType = "body", dataType = "DictionaryItemDto")
    @PostMapping("/update")
    public RestMessage<String> update(@RequestBody DictionaryItemDto dictionaryItemDto) {
        try {
            dictionaryItemDto.setGroupId(FplUserUtil.getGroupId());
            dictionaryItemService.updateById(dictionaryItemDto);
            return RestMessage.doSuccess(I18nUtils.getMessage("base.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

    /**
     * 字典子表 根据code 查询字典子表信息
     *
     * @param dictionaryQuery dictCode
     *                        itemCode
     * @return
     */
    @ApiOperation(value = "集团级-根据code 查询字典子表信息")
    @PostMapping("/queryDictionaryItemByCode")
    public RestMessage<DictionaryItemDto> queryDictionaryItemByCode(@RequestBody BaseDictionaryQuery dictionaryQuery) {
        try {
            dictionaryQuery.setGroupId(FplUserUtil.getGroupId());
            DictionaryItemDto dictionaryItemDto = dictionaryItemService.queryDictionaryItemByCode(dictionaryQuery);
            return RestMessage.doSuccess(dictionaryItemDto);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

    /**
     * 根据集团和code查询字典子表信息
     *
     * @param dictCode
     * @param itemCode
     * @param groupId
     * @return
     */
    @ApiOperation(value = "集团级-根据集团和code查询字典子表信息")
    @GetMapping("/queryGroupItemByCode")
    public RestMessage<DictionaryItemDto> queryGroupItemByCode(@RequestParam("dictCode") String dictCode, @RequestParam("itemCode") String itemCode, @RequestParam(value = "groupId", required = false, defaultValue = "0") Long groupId) {
        try {
            BaseDictionaryQuery dictionaryQuery = new BaseDictionaryQuery();
            dictionaryQuery.setItemCode(itemCode);
            dictionaryQuery.setDictCode(dictCode);
            dictionaryQuery.setGroupId(groupId);
            DictionaryItemDto dictionaryItemDto = dictionaryItemService.queryDictionaryItemByCode(dictionaryQuery);
            return RestMessage.doSuccess(dictionaryItemDto);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

    /**
     * 字典子表分页查询
     *
     * @param dictionaryItemQuery
     * @return
     */
    @ApiOperation(value = "集团级-字典子表分页查询")
    @PostMapping("/queryPageList")
    public RestMessage<PageInfo<DictionaryItemVO>> queryPageList(@RequestBody DictionaryItemQuery dictionaryItemQuery) {
        try {
            dictionaryItemQuery.setGroupId(FplUserUtil.getGroupId());
            return RestMessage.doSuccess(dictionaryItemService.queryPageList(dictionaryItemQuery));
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

    /**
     * 字典子表不分页查询
     *
     * @param dictionaryItemQuery
     * @return
     */
    @ApiOperation(value = "集团级-字典子表不分页查询")
    @PostMapping("/queryNoPage")
    public RestMessage<List<DictionaryItemVO>> queryNoPageList(@RequestBody DictionaryItemQuery dictionaryItemQuery) {
        try {
            dictionaryItemQuery.setGroupId(FplUserUtil.getGroupId());
            return RestMessage.doSuccess(dictionaryItemService.queryNoPage(dictionaryItemQuery));
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }


}
