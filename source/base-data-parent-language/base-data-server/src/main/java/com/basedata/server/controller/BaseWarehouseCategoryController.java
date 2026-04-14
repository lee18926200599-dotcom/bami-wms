package com.basedata.server.controller;

import com.basedata.common.dto.BaseWarehouseCategoryDto;
import com.basedata.common.query.WarehouseCategoryQuery;
import com.basedata.common.vo.BaseWarehouseCategoryVO;
import com.basedata.server.entity.BaseWarehouseCategory;
import com.basedata.server.service.BaseWarehouseCategoryService;
import com.common.language.util.I18nUtils;
import com.common.util.message.RestMessage;
import com.common.util.util.HuToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "仓库分类")
@RestController
@RequestMapping("/v1/warehouseCategory")
@Slf4j
public class BaseWarehouseCategoryController {

    @Resource
    private BaseWarehouseCategoryService baseWarehouseCategoryService;



    /**
     * 仓库分类查询
     * @param
     * @return
     */
    
    @ApiOperation(value = "仓库分类查询")
    @GetMapping("/queryNoPage")
    public RestMessage<List<BaseWarehouseCategoryVO>> queryNoPage(@RequestParam("source") String source) {
        try {
            List<BaseWarehouseCategoryVO> list = baseWarehouseCategoryService.queryNoPage(source);
            return RestMessage.doSuccess(list);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }


    /**
     * 仓库分类新增
     * @param  baseWarehouseCategoryDto
     * @return
     */
    @ApiOperation(value = "仓库分类新增")
    @PostMapping("/save")
    public RestMessage save(@RequestBody @Valid BaseWarehouseCategoryDto baseWarehouseCategoryDto) {
        try {
            baseWarehouseCategoryService.insert(baseWarehouseCategoryDto);
            return RestMessage.doSuccess(I18nUtils.getMessage("base.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

    /**
     * 仓库分类修改
     * @param  baseWarehouseCategoryDto
     * @return
     */
    
    @ApiOperation(value = "仓库分类修改")
    @PostMapping("/update")
    public RestMessage update(@RequestBody @Valid BaseWarehouseCategoryDto baseWarehouseCategoryDto) {
        try {
            baseWarehouseCategoryService.updateById(baseWarehouseCategoryDto);
            return RestMessage.doSuccess(I18nUtils.getMessage("base.success"));
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

    /**
     * 启用/停用
     * @return
     */
    
    @ApiOperation(value = "仓库分类 启用/停用")
    @PostMapping("/enableOrDisable")
    public RestMessage enableOrDisable(@RequestBody BaseWarehouseCategoryDto baseWarehouseCategoryDto) {
        try {
            if(null == baseWarehouseCategoryDto || null == baseWarehouseCategoryDto.getState()){
                return  RestMessage.error("参数有误！");
            }
            return baseWarehouseCategoryService.enableOrDisable(baseWarehouseCategoryDto);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }
    /**
     * 移动
     * @param  id
     * @return
     */
    
    @ApiOperation(value = "移动")
    @GetMapping("/move")
    public RestMessage move(@RequestParam ("id")Long id ,@RequestParam ("id2")Long id2 ) {

        try {
            return baseWarehouseCategoryService.move(id,id2);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }


    /**
     * 删除
     * @param  id
     * @return
     */
    
    @ApiOperation(value = "删除仓库分类")
    @GetMapping("/delete")
    public RestMessage delete(@RequestParam ("id")Long id) {

        try {
            BaseWarehouseCategoryDto baseWarehouseCategoryDto = new BaseWarehouseCategoryDto();
            if(null != id){
                baseWarehouseCategoryDto.setId(id);
            }else{
                return  RestMessage.error("参数有误！");
            }
            return baseWarehouseCategoryService.delete(baseWarehouseCategoryDto);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }


    /**
     * 根据仓库分类编号查询子编号集合
     * @param
     * @return
     */
    
    @ApiOperation(value = "根据仓库分类编号查询子编号集合")
    @GetMapping("/queryCategoryListByParentId")
    public RestMessage<List<Long>> queryCategoryListByParentId(@RequestParam("id") Long id) {
        try {
            List<Long> list = baseWarehouseCategoryService.queryCategoryListByParentId(id);
            return RestMessage.doSuccess(list);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

    /**
     * 根据仓库分类编号查询子编号集合
     * @param
     * @return
     */
    
    @ApiOperation(value = "根据仓库分类id查询仓储分类")
    @GetMapping("/queryCategoryById")
    public RestMessage<BaseWarehouseCategoryDto> queryCategoryById(@RequestParam("id") Long id) {
        try {
            log.info("打印入参id:{}",id);
            BaseWarehouseCategoryDto baseWarehouseCategoryDto = baseWarehouseCategoryService.queryCategoryById(id);
            return RestMessage.doSuccess(baseWarehouseCategoryDto);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }
    /**
     * 根据仓库分类编号查询子编号集合
     * @param
     * @return
     */
  
    @ApiOperation(value = "根据参数查询仓储分类集合")
    @PostMapping("/queryCategoryList")
    public RestMessage<List<BaseWarehouseCategoryDto>> queryCategoryList(@RequestBody WarehouseCategoryQuery query) {
        try {
            List<BaseWarehouseCategory> baseWarehouseCategoryList = baseWarehouseCategoryService.queryCategoryList(query);
            List<BaseWarehouseCategoryDto> list= HuToolUtil.exchange(baseWarehouseCategoryList,BaseWarehouseCategoryDto.class);
            return RestMessage.doSuccess(list);
        } catch (Exception e) {
            e.printStackTrace();
            return RestMessage.error(e.getMessage());
        }
    }

}
