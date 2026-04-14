package com.basedata.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.basedata.server.entity.DictionaryItem;
import com.basedata.server.mapper.DictionaryItemMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhouyk
 * @CreateTime: 2024-03-19  11:33
 * @Description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DictionaryTest {

    @Autowired
    private DictionaryItemMapper itemMapper;

    @Test
    public void init() {

        JSONArray jsonArray = JSON.parseArray(getTXT());
        Map<Long, JSONArray> map = new HashMap<>();
        Map<Long, JSONObject> objmap = new HashMap<>();
        for (Object object : jsonArray) {
            JSONObject obj = (JSONObject) object;
            objmap.put(obj.getLong("itemId"), obj);
            JSONArray array = map.get(obj.getLong("parentId"));
            if (array == null) {
                array = new JSONArray();
            }
            array.add(obj);
            map.put(obj.getLong("parentId"), array);
        }
        JSONArray resultArr = buildChild(0L, map);
//        System.out.println(resultArr.toJSONString());

        saveData(0L, 124L, "FPL_CRM_HYLX", resultArr);
    }

    private void saveData(Long parentId, Long dicId, String dicCode, JSONArray jsonArray) {
        if (jsonArray != null && jsonArray.size() > 0) {
            for (Object object : jsonArray) {
                JSONObject obj = (JSONObject) object;
                DictionaryItem dictionaryItem = new DictionaryItem();
                dictionaryItem.setDictionaryId(dicId);
                dictionaryItem.setDictionaryCode(dicCode);
                dictionaryItem.setSortNum(1L);
                dictionaryItem.setItemCode(obj.getString("itemCode"));
                dictionaryItem.setItemName(obj.getString("itemName"));
                dictionaryItem.setCreatedBy(0L);
                dictionaryItem.setCreatedName("System");
                dictionaryItem.setCreatedDate(new Date());
                dictionaryItem.setState(1);
                dictionaryItem.setParentId(parentId);
                dictionaryItem.setRemark("系统初始化数据");
                dictionaryItem.setDeletedFlag(0);
                itemMapper.insert(dictionaryItem);
                saveData(dictionaryItem.getId(), dicId, dicCode, obj.getJSONArray("child"));
            }
        }
    }

    private JSONArray buildChild(Long parentId, Map<Long, JSONArray> map) {
        JSONArray jsonArray = map.get(parentId);
        if (jsonArray != null) {
            for (Object object : jsonArray) {
                JSONObject obj = (JSONObject) object;
                obj.put("child", buildChild(obj.getLong("itemId"), map));
            }
        }
        return jsonArray;
    }

    public String getTXT() {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            /* 读入TXT文件 */
            String pathname = "/Users/zhouyk/普罗格/需求/行业字典_20240319.json";
            File file = new File(pathname);
            BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                stringBuffer.append(s);
            }
            br.close();
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
