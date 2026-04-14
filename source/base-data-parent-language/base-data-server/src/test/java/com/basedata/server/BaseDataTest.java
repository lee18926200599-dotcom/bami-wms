package com.basedata.server;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.basedata.server.entity.BaseWarehouse;
import com.basedata.server.mapper.BaseWarehouseMapper;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: zhouyk
 * @CreateTime: 2023-12-26  12:33
 * @Description: TODO
 */
@SpringBootTest
public class BaseDataTest {
    @Resource
    private BaseWarehouseMapper baseWarehouseMapper;
    @Test
    public void codetest(){
       System.out.println(getWarehouseCode(18155L));
    }
    private String getWarehouseCode(Long serviceProviderId) {
        LambdaQueryWrapper<BaseWarehouse> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseWarehouse::getServiceProviderId, serviceProviderId);
        lambdaQueryWrapper.orderByDesc(BaseWarehouse::getId);
        PageHelper.startPage(1, 1);
        List<BaseWarehouse> list = baseWarehouseMapper.selectList(lambdaQueryWrapper);
        String code = "C001";
        Integer codeNum = 0;
        if (CollectionUtils.isEmpty(list)) {
            return code;
        }
        BaseWarehouse baseWarehouse = list.get(0);
        if (StringUtils.isBlank(baseWarehouse.getWarehouseCode())) {
            return code;
        }
        try {
            String existCode = baseWarehouse.getWarehouseCode().substring(1);
            codeNum = Integer.valueOf(existCode) + 1;

        } catch (Exception e) {
            Pattern pattern = Pattern.compile("[0-9]+");
            Matcher matcher = pattern.matcher(baseWarehouse.getWarehouseCode());
            if (matcher.find()) {
                String num = matcher.group();
                codeNum = Integer.valueOf(num) + 1;
            } else {
                return code;
            }
        }
        if (codeNum > 999) {
            code = "C" + codeNum;
        } else {
            code = "C" + String.format("%03d", codeNum);
        }
        return code;
    }
}
