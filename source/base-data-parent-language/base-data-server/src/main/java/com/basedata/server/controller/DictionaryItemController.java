package com.basedata.server.controller;

import com.basedata.common.dto.DictionaryItemDto;
import com.basedata.common.query.BaseDictionaryQuery;
import com.basedata.common.vo.DictionaryItemVO;
import com.basedata.server.query.DictionaryItemQuery;
import com.basedata.server.service.DictionaryItemService;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 全局级字典
 */
@Api(tags = "全局级字典条目")
@RestController
@RequestMapping("/v1/dictionaryItem")
public class DictionaryItemController {

    @Resource
    private DictionaryItemService dictionaryItemService;

    /**
     * 字典子表表新增
     *
     * @param dictionaryItemDto
     * @return
     */

    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public RestMessage save(@RequestBody DictionaryItemDto dictionaryItemDto) {
        try {
            dictionaryItemDto.setGroupId(0L);
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

    @ApiOperation(value = "修改全局级字典条目")
    @PostMapping("/update")
    public RestMessage update(@RequestBody DictionaryItemDto dictionaryItemDto) {
        try {
            dictionaryItemDto.setGroupId(0L);
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
     * @param dictCode
     * @param itemCode
     * @return
     */

    @ApiOperation(value = "根据code 查询字典子表信息")
    @GetMapping("/queryDictionaryItemByCode")
    public RestMessage<DictionaryItemDto> queryDictionaryItemByCode(@RequestParam("dictCode") String dictCode, @RequestParam("itemCode") String itemCode) {
        try {
            BaseDictionaryQuery dictionaryInfoQuery = new BaseDictionaryQuery();
            dictionaryInfoQuery.setDictCode(dictCode);
            dictionaryInfoQuery.setItemCode(itemCode);
            dictionaryInfoQuery.setGroupId(0L);
            DictionaryItemDto dictionaryItemDto = dictionaryItemService.queryDictionaryItemByCode(dictionaryInfoQuery);
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

    @ApiOperation(value = "字典子表分页查询")
    @PostMapping("/queryPageList")
    public RestMessage<PageInfo<DictionaryItemVO>> queryPageList(@RequestBody DictionaryItemQuery dictionaryItemQuery) {
        try {
            return RestMessage.doSuccess(dictionaryItemService.queryPageList(dictionaryItemQuery));
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

    /**
     * 字典子表不分页查询--页面用
     *
     * @param dictionaryItemQuery
     * @return
     */

    @ApiOperation(value = "字典子表不分页查询")
    @PostMapping("/queryNoPage")
    public RestMessage<List<DictionaryItemVO>> queryNoPageList(@RequestBody DictionaryItemQuery dictionaryItemQuery) {
        try {
            return RestMessage.doSuccess(dictionaryItemService.queryNoPage(dictionaryItemQuery));
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }


}
