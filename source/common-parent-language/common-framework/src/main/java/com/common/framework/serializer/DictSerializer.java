package com.common.framework.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.common.framework.annotation.Lookup;
import com.common.framework.constants.DictConstants;
import com.common.framework.web.SpringBeanLoader;
import com.common.util.message.RestMessage;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class DictSerializer extends StdSerializer<Object> implements ContextualSerializer {


    private transient String dictCode;

    protected DictSerializer() {
        super(Object.class);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        Lookup annotation = beanProperty.getAnnotation(Lookup.class);
        return createContextual(annotation.dictCode());
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        String dictCode = getDictCode();
        if (StringUtils.isBlank(dictCode)) {
            return;
        }
        if (Objects.isNull(value)) {
            return;
        }
        try {
            // 因为序列化是每个对象都需要进行序列话操作，这里为了减少网络IO，使用了 guava 的本地缓存（代码在下面）,也可以使用redis缓存
            Map<String, String> dictMap = DictConstants.DICTIONARY_CACHE.get(dictCode);
            if (dictMap.size() == 0 || dictMap.containsKey("nullValue")) {
                // 当本地缓存中不存在该类型的字典时，就调用查询方法，并且放入到本地缓存中
                dictMap = queryDictionaryByDictCode(dictCode);
                DictConstants.DICTIONARY_CACHE.put(dictCode, dictMap);
            }
            // 通过数据字典类型和value获取name
            String label = dictMap.get(value.toString());
            gen.writeObject(value);
            // 在需要转换的字段上添加@Lookup注解，注明需要引用的groupCode，后端会在返回值中增加filedNameDesc的key，前端只需要取对应的 filedNameDesc 就可以直接使用
            gen.writeFieldName(gen.getOutputContext().getCurrentName() + DictConstants.DICT_TEXT_SUFFIX);
            gen.writeObject(label);
        } catch (Exception e) {
            log.error("错误信息:{}", e.getMessage(), e);
        }
    }

    private Map<String, String> queryDictionaryByDictCode(String dictCode) {
        Map<String, String> dictionaryInfo = Maps.newHashMap();
        DictQuery query = new DictQuery();
        query.setDictionaryCode(dictCode);
        try {
            String url = "http://zzz-base-data-server/dictionary-item-group/queryNoPage";
//        RestTemplate restTemplate = SpringBeanLoader.getApplicationContext().getBean(RestTemplate.class);
//        RestMessage message = restTemplate.postForObject(url, query, RestMessage.class);
            RestTemplate restTemplate = SpringBeanLoader.getSpringBean(RestTemplate.class);
            ;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<Object> entity = new HttpEntity<>(query, headers);
            ResponseEntity<RestMessage> response = restTemplate.exchange(url, HttpMethod.POST, entity, RestMessage.class);
            log.info("字典转换查询字典信息:{}", JSON.toJSONString(response));
            RestMessage message = response.getBody();
            if (message.isSuccess() && Objects.nonNull(message.getData())) {
                Object data = message.getData();
                if (data instanceof List) {
                    List<DictInfo> dictInfos = JSONArray.parseArray(JSON.toJSONString(data), DictInfo.class);
                    if (CollectionUtils.isNotEmpty(dictInfos)) {
                        return dictInfos.stream().collect(Collectors.toMap(DictInfo::getItemCode, DictInfo::getItemName));
                    }
                }
            }
            return dictionaryInfo;
        } catch (Exception e) {
            log.error("字典转换查询字典信息错误信息:{}", e.getMessage(), e);
            return dictionaryInfo;
        }
    }

    private JsonSerializer<?> createContextual(String dictCode) {
        DictSerializer serializer = new DictSerializer();
        serializer.setDictCode(dictCode);
        return serializer;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

}
