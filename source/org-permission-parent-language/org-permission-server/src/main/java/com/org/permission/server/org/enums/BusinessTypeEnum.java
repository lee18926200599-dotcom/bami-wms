package com.org.permission.server.org.enums;

import com.org.permission.common.enums.org.FunctionTypeEnum;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 业务类型状态枚举
 */
public enum BusinessTypeEnum {

    PLATFORM(1, "1", "平台", new Integer[]{
            FunctionTypeEnum.PLATFORM.getIndex(),
            FunctionTypeEnum.CORPORATION.getIndex(),
            FunctionTypeEnum.FINANCE.getIndex(),
            FunctionTypeEnum.STORAGE.getIndex(),
            FunctionTypeEnum.LOGISTICS.getIndex(),
            FunctionTypeEnum.PURCHASE.getIndex(),
            FunctionTypeEnum.SALE.getIndex(),
            FunctionTypeEnum.LABOUR_SERVICE.getIndex(),
            FunctionTypeEnum.BANKING.getIndex()
    }),
    STORAGE(2, "2", "仓储", new Integer[]{
            FunctionTypeEnum.STORAGE.getIndex(),
            FunctionTypeEnum.CORPORATION.getIndex(),
            FunctionTypeEnum.FINANCE.getIndex()
    }),
    LOGISTICS(3, "3", "运输", new Integer[]{
            FunctionTypeEnum.LOGISTICS.getIndex(),
            FunctionTypeEnum.CORPORATION.getIndex(),
            FunctionTypeEnum.FINANCE.getIndex()
    }),
    CHANNEL(4, "4", "渠道商", new Integer[]{
            FunctionTypeEnum.PURCHASE.getIndex(),
            FunctionTypeEnum.SALE.getIndex(),
            FunctionTypeEnum.CORPORATION.getIndex(),
            FunctionTypeEnum.FINANCE.getIndex()
    }),
    BRANDER(5, "5", "品牌商", new Integer[]{
            FunctionTypeEnum.PURCHASE.getIndex(),
            FunctionTypeEnum.SALE.getIndex(),
            FunctionTypeEnum.CORPORATION.getIndex(),
            FunctionTypeEnum.FINANCE.getIndex()
    }),
    LABORSERVICE(6, "6", "劳务", new Integer[]{}),
    FINANCE(7, "7", "金融", new Integer[]{
            FunctionTypeEnum.CORPORATION.getIndex(),
            FunctionTypeEnum.FINANCE.getIndex(),
            FunctionTypeEnum.BANKING.getIndex()
    }),
    LANDEDPROPERTY(8, "8", "地产", new Integer[]{}),
    MERCHANT(9, "9", "商户", new Integer[]{}),
    EXPRESS(10, "10", "快递", new Integer[]{
            FunctionTypeEnum.CORPORATION.getIndex(),
            FunctionTypeEnum.FINANCE.getIndex(),
            FunctionTypeEnum.LOGISTICS.getIndex()
    }),
    SITEOPERATIONS(11, "11", "站点运营", new Integer[]{}),
    DEALERAGENCYOPERATION(12, "12", "经销商代运营", new Integer[]{
            FunctionTypeEnum.CORPORATION.getIndex(),
            FunctionTypeEnum.SALE.getIndex(),
            FunctionTypeEnum.PURCHASE.getIndex(),
            FunctionTypeEnum.FINANCE.getIndex()
    }),
    AGENCY(13,"13","代运营", new Integer[] {
            FunctionTypeEnum.CORPORATION.getIndex(),
            FunctionTypeEnum.FINANCE.getIndex()
    }),
    ;

    private Integer index;

    private String code;

    private String name;

    /**
     * 组织职能
     */
    private Integer[] functionTypes;

    BusinessTypeEnum(Integer index, String code, String name, Integer[] functionTypes) {
        this.index = index;
        this.code = code;
        this.name = name;
        this.functionTypes = functionTypes;
    }

    public static BusinessTypeEnum getEnum(String code) {
        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public static List<LinkedHashMap> getEnumList() {
        List<LinkedHashMap> list = new ArrayList<>();
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (BusinessTypeEnum type : values()) {
            map.put(type.code, type.name);
        }
        list.add(map);
        return list;
    }

    public Integer getIndex() {
        return index;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Integer[] getFunctionTypes() {
        return functionTypes;
    }


    public boolean equals(Integer index) {
        return null != index && index.equals(this.index);
    }


    @Override
    public String toString() {
        return code;
    }
    public static boolean hasCorporation(String custBusinessType) {
        String[] butype = custBusinessType.split(",");
        Boolean flag=false;
        for (int i = 0; i <butype.length; i++) {
            if(butype[i].equals(MERCHANT.getCode())|| butype[i].equals(STORAGE.getCode())){
                flag= true;
            }
            if(butype[i].equals(LOGISTICS.getCode())|| butype[i].equals(CHANNEL.getCode())){
                flag= true;
            }
            if(butype[i].equals(BRANDER.getCode())|| butype[i].equals(PLATFORM.getCode())){
                flag= true;
            }

            if(butype[i].equals(EXPRESS.getCode())|| butype[i].equals(FINANCE.getCode())){
                flag= true;
            }
            if(butype[i].equals(AGENCY.getCode())||butype[i].equals(DEALERAGENCYOPERATION.getCode())){
                flag= true;
            }

        }
        return flag;
    }

    //财务职能
    public static boolean hasFinance(String custBusinessType) {
        String[] butype = custBusinessType.split(",");
        Boolean flag=false;
        for (int i = 0; i <butype.length; i++) {
            if(butype[i].equals(MERCHANT.getCode())|| butype[i].equals(STORAGE.getCode())){
                flag= true;
            }
            if(butype[i].equals(LOGISTICS.getCode())|| butype[i].equals(CHANNEL.getCode())){
                flag= true;
            }
            if(butype[i].equals(BRANDER.getCode())|| butype[i].equals(PLATFORM.getCode())){
                flag= true;
            }
            if(butype[i].equals(FINANCE.getCode())|| butype[i].equals(EXPRESS.getCode())){
                flag= true;
            }
            if(butype[i].equals(AGENCY.getCode())|| butype[i].equals(DEALERAGENCYOPERATION.getCode())){
                flag= true;
            }


        }
        return flag;
    }

    public static boolean hasPurchase(String custBusinessType) {
        String[] butype = custBusinessType.split(",");
        Boolean flag=false;
        for (int i = 0; i <butype.length; i++) {
            if(butype[i].equals(MERCHANT.getCode())|| butype[i].equals(CHANNEL.getCode())){
                flag= true;
            }
            if(butype[i].equals(PLATFORM.getCode())){
                flag= true;
            }
            if(butype[i].equals(DEALERAGENCYOPERATION.getCode())|| butype[i].equals(BRANDER.getCode())){
                flag= true;
            }

        }
        return flag;
    }

    public static boolean hasSale(String custBusinessType) {
        String[] butype = custBusinessType.split(",");
        Boolean flag=false;
        for (int i = 0; i <butype.length; i++) {
            if(butype[i].equals(MERCHANT.getCode())|| butype[i].equals(CHANNEL.getCode())){
                flag= true;
            }
            if(butype[i].equals(BRANDER.getCode())|| butype[i].equals(DEALERAGENCYOPERATION.getCode())){
                flag= true;
            }
            if(butype[i].equals(PLATFORM.getCode())){
                flag= true;
            }
        }
        return flag;
    }

    public static boolean hasStorage(String custBusinessType) {

        String[] butype = custBusinessType.split(",");
        Boolean flag=false;
        for (int i = 0; i <butype.length; i++) {
            if(butype[i].equals(STORAGE.getCode())|| butype[i].equals(PLATFORM.getCode())){
                flag= true;
            }
        }
        return flag;
    }

    public static boolean hasLogistics(String custBusinessType) {
        String[] butype = custBusinessType.split(",");
        Boolean flag=false;
        for (int i = 0; i <butype.length; i++) {
            if(butype[i].equals(LOGISTICS.getCode())|| butype[i].equals(EXPRESS.getCode())){
                flag= true;
            }
            if(butype[i].equals(PLATFORM.getCode())){
                flag= true;
            }
        }
        return flag;
    }

    public static boolean hasPlatform(String custBusinessType) {
        return StringUtils.equals(custBusinessType, PLATFORM.getCode());
    }

    public static boolean hasLabour(String custBusinessType) {
        return StringUtils.contains(custBusinessType, LABORSERVICE.getCode());
    }

    //含有金融职能
    public static boolean hasBanking(String custBusinessType) {
        String[] butype = custBusinessType.split(",");
        Boolean flag=false;
        for (int i = 0; i <butype.length; i++) {
            if(butype[i].equals(PLATFORM.getCode())|| butype[i].equals(FINANCE.getCode())){
                flag= true;
            }
        }
        return flag;
    }
}
