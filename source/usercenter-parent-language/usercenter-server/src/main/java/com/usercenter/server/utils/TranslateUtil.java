package com.usercenter.server.utils;


import com.org.permission.common.org.dto.OrgInfoDto;
import com.org.permission.common.org.dto.StaffInfoDto;
import com.org.permission.common.query.BatchQueryParam;
import com.basedata.common.vo.BaseBatchDictionaryVo;
import com.boss.crm.common.util.BeanUtil;
import com.boss.crm.common.util.StringUtil;
import com.usercenter.server.domain.service.DictionaryDomainService;
import com.usercenter.server.domain.service.OrgDomainService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 数据相关工具类
 */
@Configuration
public class TranslateUtil {

    // 组织机构
    private static OrgDomainService orgDomainService;

    @Autowired
    public void setOrgClient(OrgDomainService orgDomainService) {
        TranslateUtil.orgDomainService = orgDomainService;
    }

    private static final Map<String, String> MAPPING_RULE_ORG = new HashMap<>(1);
    private static final Map<String, String> MAPPING_RULE_STAFF = new HashMap<>(1);


    private static final Map<String, String> MAPPING_RULE_ADDRESS = new HashMap<>(1);


    private static final Map<String, String> MAPPING_RULE_USER = new HashMap<>(3);

    // 数据字典
    private static DictionaryDomainService dictionaryDomainService;

    @Autowired
    public void setDictionaryClient(DictionaryDomainService dictionaryDomainService) {
        TranslateUtil.dictionaryDomainService = dictionaryDomainService;
    }

    private static final Map<String, String> MAPPING_RULE_DICT = new HashMap<>(1);

    private static final Map<String, String> MAPPING_RULE_PAYMENT = new HashMap<>(1);

    static {
        // 组织机构映射
        MAPPING_RULE_ORG.put("name>", "orgName>");

        // 组织人员映射
        MAPPING_RULE_STAFF.put("name>", "realname>");
        MAPPING_RULE_STAFF.put("code>", "staffCode>");
        MAPPING_RULE_STAFF.put("phone>", "phone>");
        MAPPING_RULE_STAFF.put("departName>", "depName>");
        MAPPING_RULE_STAFF.put("unitOrg>", "buName>");

        // 地址库映射
        MAPPING_RULE_ADDRESS.put("name>", "name>");

        // 用户中心映射
        MAPPING_RULE_USER.put("name>", "userName>");
        MAPPING_RULE_USER.put("phone>", "phone>");
        MAPPING_RULE_USER.put("realName>", "realName>");
        MAPPING_RULE_USER.put("profile>", "profile>");

        // 数据字典映射
        MAPPING_RULE_DICT.put("name>", "itemName>");

        // 收付款协议映射
        MAPPING_RULE_PAYMENT.put("name>", "agreementName>");
    }

    /**
     * 批量设置组织属性值<br />
     * 支持的属性值有：
     * <ul>
     * <li>name:组织名称</li>
     * </ul>
     *
     * @param objectList 对象列表
     * @param mappings   属性映射关系列表。如{@code new String[]{"groupOrg:name>groupOrgName", "unitOrg:name>unitOrgName"}}
     * @return 入参{@code objectList}
     */
    public static <T> List<T> translateOrgIds(List<T> objectList, String[] mappings) {
        if (CollectionUtils.isEmpty(objectList) || null == mappings || 0 == mappings.length) {
            return objectList;
        }

        Map<String, String> mappingMap = new HashMap<>(mappings.length);
        Arrays.stream(mappings).forEach(mapping -> mappingMap.put(mapping.split(":")[0], mapping.split(":")[1]));

        if (!mappingMap.isEmpty()) {
            Map<String, Field> sourceFieldCache = new HashMap<>(mappingMap.size());

            Set<Long> orgIds = new HashSet<>(objectList.size());
            String[] sourceArray = mappingMap.keySet().toArray(new String[]{});
            Arrays.stream(sourceArray).forEach(source -> {
                Field sourceField = BeanUtil.getField(objectList.get(0), source);
                if (null != sourceField) {
                    sourceFieldCache.put(source, sourceField);

                    objectList.stream().forEach(elem -> {
                        try {
                            Object val = sourceField.get(elem);
                            if (null != val) {
                                orgIds.add(((Number) val).longValue());
                            }
                        } catch (IllegalAccessException ignore) {
                        }
                    });
                }
            });

            if (0 < orgIds.size()) {
                List<OrgInfoDto> orgList = TranslateUtil.orgDomainService.getOrgByIdList(new ArrayList<>(orgIds));
                if (null != orgList && 0 < orgList.size()) {
                    Arrays.stream(sourceArray).forEach(source -> {
                        Field sourceField = sourceFieldCache.get(source);
                        if (null != sourceField) {
                            Arrays.stream(objectList.toArray()).forEach(elem -> {
                                try {
                                    Object val = sourceField.get(elem);
                                    if (null == val) {
                                        return;
                                    }

                                    Long v = ((Number) val).longValue();
                                    for (OrgInfoDto org : orgList) {
                                        if (org.getId().equals(v)) {
                                            String[] propMappings = mappingMap.get(source).split(",");
                                            for (int i = 0; i < propMappings.length; i++) {
                                                String targetSource = MAPPING_RULE_ORG.get(propMappings[i].split(">")[0] + ">");
                                                if (null != targetSource) {
                                                    propMappings[i] = propMappings[i].replace(propMappings[i].split(">")[0] + ">", targetSource);
                                                } else {
                                                    propMappings[i] = null;
                                                }
                                            }
                                            BeanUtil.copyPropertiesToSpec(org, elem, propMappings);
                                            break;
                                        }
                                    }
                                } catch (IllegalAccessException ignore) {
                                }
                            });
                        }
                    });
                }
            }
        }

        return objectList;
    }

    /**
     * 批量设置人员属性值<br />
     * 支持的属性值有：
     * <ul>
     * <li>name:人员名称</li>
     * </ul>
     *
     * @param objectList 对象列表
     * @param mappings   属性映射关系列表。如{@code new String[]{"worker:name>workerName"}}
     * @return 入参{@code objectList}
     */
    public static <T> List<T> translateStaffIds(List<T> objectList, String[] mappings) {
        if (CollectionUtils.isEmpty(objectList) || null == mappings || 0 == mappings.length) {
            return objectList;
        }

        Map<String, String> mappingMap = new HashMap<>(mappings.length);
        Arrays.stream(mappings).forEach(mapping -> mappingMap.put(mapping.split(":")[0], mapping.split(":")[1]));

        if (!mappingMap.isEmpty()) {
            Map<String, Field> sourceFieldCache = new HashMap<>(mappingMap.size());

            Set<Long> workerIds = new HashSet<>(objectList.size());
            String[] sourceArray = mappingMap.keySet().toArray(new String[]{});
            Arrays.stream(sourceArray).forEach(source -> {
                Field sourceField = BeanUtil.getField(objectList.get(0), source);
                if (null != sourceField) {
                    sourceFieldCache.put(source, sourceField);

                    Arrays.stream(objectList.toArray()).forEach(elem -> {
                        try {
                            Object val = sourceField.get(elem);
                            if (null != val) {
                                workerIds.add(((Number) val).longValue());
                            }
                        } catch (IllegalAccessException ignore) {
                        }
                    });
                }
            });

            if (0 < workerIds.size()) {
                BatchQueryParam orgParam = new BatchQueryParam();
                orgParam.setIds(new ArrayList<>(workerIds));
                List<StaffInfoDto> workerList = TranslateUtil.orgDomainService.getStaffByIdList(new ArrayList<>(workerIds));
                if (null != workerList && 0 < workerList.size()) {
                    Arrays.stream(sourceArray).forEach(source -> {
                        Field sourceField = sourceFieldCache.get(source);
                        if (null != sourceField) {
                            Arrays.stream(objectList.toArray()).forEach(elem -> {
                                try {
                                    Object val = sourceField.get(elem);
                                    if (null == val) {
                                        return;
                                    }

                                    Long v = ((Number) val).longValue();
                                    for (StaffInfoDto staff : workerList) {
                                        if (staff.getId().equals(v)) {
                                            String[] propMappings = mappingMap.get(source).split(",");
                                            for (int i = 0; i < propMappings.length; i++) {
                                                String targetSource = MAPPING_RULE_STAFF.get(propMappings[i].split(">")[0] + ">");
                                                if (null != targetSource) {
                                                    propMappings[i] = propMappings[i].replace(propMappings[i].split(">")[0] + ">", targetSource);
                                                } else {
                                                    propMappings[i] = null;
                                                }
                                            }
                                            BeanUtil.copyPropertiesToSpec(staff, elem, propMappings);
                                            break;
                                        }
                                    }
                                } catch (IllegalAccessException ignore) {
                                }
                            });
                        }
                    });
                }
            }
        }

        return objectList;
    }

    /**
     * 批量设置数据字典值<br />
     * 支持的属性值有：
     * <ul>
     * <li>name:名称</li>
     * </ul>
     *
     * @param objectList 对象列表
     * @param mappings   属性映射关系列表。如{@code new String[]{"","businessType+@BOSS006:name>businessTypeDesc","category@:name>categoryName"}}
     * @param selectors  数据字典项选择器数组
     * @return 入参{@code objectList}
     */
    public static <T> List<T> translateDict(List<T> objectList, String[] mappings, DictSelector... selectors) {
        if (CollectionUtils.isEmpty(objectList) || null == mappings || 0 == mappings.length) {
            return objectList;
        }

        // 多值属性记录MAP
        Map<String, Boolean> multiValuePorpertyMap = new HashMap<>(0);
        // 属性值TO数据字典MAP
        Map<String, Map<T, String>> codeMap = new HashMap<>(mappings.length);
        // 存放翻译结果的属性MAP
        Map<String, String> targetPropertyMap = new HashMap<>(mappings.length);

        for (String mapping : mappings) {
            if (StringUtils.isNotBlank(mapping)) {
                String[] parts = mapping.split(":");
                if (2 != parts.length) {
                    break;
                }

                if (StringUtils.isNotBlank(parts[0])) {
                    String code = "", propertyName;
                    boolean multiValMark;
                    String[] splitArray = parts[0].split("@");

                    // 解析属性名
                    propertyName = splitArray[0].split("\\+")[0];

                    // 解析多值标记
                    multiValMark = splitArray[0].endsWith("+");
                    multiValuePorpertyMap.put(propertyName, multiValMark);

                    if (StringUtils.isNotBlank(propertyName)) {
                        // 获取映射
                        String[] dataMapping = parts[1].split(">");
                        if (2 != dataMapping.length || null == MAPPING_RULE_DICT.get(dataMapping[0] + ">")) {
                            break;
                        }
                        targetPropertyMap.put(propertyName, MAPPING_RULE_DICT.get(dataMapping[0] + ">") + dataMapping[1]);

                        // 计算属性->(元素->字典项)Map
                        boolean dynamicMark = (1 >= splitArray.length || StringUtil.isBlank(splitArray[1])) && (null != selectors && 0 < selectors.length);
                        Map<T, String> elemCodeMap = new HashMap<>(objectList.size());

                        for (T obj : objectList) {
                            if (null == obj) {
                                continue;
                            }
                            if (dynamicMark) {
                                Map<T, String> objCodeMap = new HashMap<>(objectList.size());
                                for (DictSelector<T> s : selectors) {
                                    if (null != s && StringUtils.isNotBlank(s.getDictCode(obj))) {
                                        code = s.getDictCode(obj);
                                        break;
                                    }
                                }
                            } else {
                                code = splitArray[1];
                            }
                            elemCodeMap.put(obj, code);
                        }
                        if (!elemCodeMap.isEmpty()) {
                            codeMap.put(propertyName, elemCodeMap);
                        }
                    }
                }
            }
        }

        if (!codeMap.isEmpty()) {
            // 获取各个字典数据项的值集合
            Map<String, Map<T, List<String>>> codeValueMap = new HashMap<>(codeMap.size());
            for (String property : codeMap.keySet()) {
                Field field = BeanUtil.getField(objectList.get(0), property);
                if (null != field) {
                    Map<T, List<String>> elemValueMap = new HashMap<>(objectList.size());
                    for (T elem : objectList) {
                        try {
                            Object rawValue = field.get(elem);
                            if (null != rawValue) {
                                List<String> valueSet = new ArrayList<>(1);
                                if (multiValuePorpertyMap.get(property)) {
                                    String[] multiVal = rawValue.toString().split(",");
                                    Arrays.stream(multiVal).forEach(elemVal -> {
                                        if (StringUtils.isNotBlank(elemVal)) {
                                            valueSet.add(elemVal);
                                        }
                                    });
                                } else {
                                    valueSet.add(rawValue.toString());
                                }
                                elemValueMap.put(elem, valueSet);
                            }
                        } catch (IllegalAccessException ignore) {
                        }
                    }
                    if (!elemValueMap.isEmpty()) {
                        codeValueMap.put(property, elemValueMap);
                    }
                }
            }

            if (!codeValueMap.isEmpty()) {
                // 获取数据字典
                Map<String, Set<String>> codeParam = new HashMap<>(mappings.length);
                for (String propery : codeValueMap.keySet()) {
                    for (T elem : objectList) {
                        String code = codeMap.get(propery).get(elem);
                        if (null == codeParam.get(code)) {
                            codeParam.put(code, new HashSet<>(objectList.size()));
                        }
                        List<String> valueSet = codeValueMap.get(propery).get(elem);
                        if (null != valueSet) {
                            codeParam.get(code).addAll(valueSet);
                        }
                    }
                }

                // 翻译MAP
                Map<String, List<BaseBatchDictionaryVo.BaseBatchDictionaryDetailVo>> codeDataMap = TranslateUtil.dictionaryDomainService.getDicMap(codeParam);
                if (!codeDataMap.isEmpty()) {
                    for (String prop : codeMap.keySet()) {
                        String targetMapping = targetPropertyMap.get(prop);
                        if (StringUtils.isNotBlank(targetMapping)) {
                            String[] targetArrays = targetMapping.split(">");
                            Field targetField = BeanUtil.getField(objectList.get(0), targetArrays[1]);
                            if (null == targetField) {
                                continue;
                            }

                            for (T elem : objectList) {
                                if (null != elem) {
                                    String dictCode = codeMap.get(prop).get(elem);
                                    if (StringUtils.isBlank(dictCode)) {
                                        break;
                                    }
                                    List<BaseBatchDictionaryVo.BaseBatchDictionaryDetailVo> dictItems = codeDataMap.get(dictCode);
                                    if (null == dictItems || 0 == dictItems.size()) {
                                        break;
                                    }
                                    Field itemField = BeanUtil.getField(dictItems.get(0), targetArrays[0]);
                                    if (null == itemField) {
                                        break;
                                    }

                                    try {
                                        List<String> valueSet = codeValueMap.get(prop).get(elem);
                                        if (null != valueSet && !valueSet.isEmpty()) {
                                            // 翻译并保存
                                            List<String> resultList = new ArrayList<>(valueSet.size());
                                            for (String value : valueSet) {
                                                for (BaseBatchDictionaryVo.BaseBatchDictionaryDetailVo item : dictItems) {
                                                    if (value.equals(item.getItemCode())) {
                                                        resultList.add(itemField.get(item).toString());
                                                        break;
                                                    }
                                                }
                                            }
                                            if (!resultList.isEmpty()) {
                                                targetField.set(elem, StringUtil.getStringFromList(new ArrayList<>(resultList)));
                                            }
                                        }
                                    } catch (Exception ignore) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return objectList;
    }

    /**
     * 数据字典项目选择接口
     *
     * @param <T>
     */
    public interface DictSelector<T> {
        /**
         * 指定该选择器是否适用于{@code propertyName}
         *
         * @param propertyName 要匹配的属性名
         * @return true:适用，false:不适用
         */
        boolean isMatch(String propertyName);

        /**
         * 获取数据字典项编码
         *
         * @param contextElem 上下文对象
         * @return 数据字典项编码
         */
        String getDictCode(T contextElem);
    }

}
