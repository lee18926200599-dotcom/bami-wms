package com.basedata.server.dto.netside;

import lombok.Data;

import java.util.List;

@Data
public class WxxdAccountGetResponse {

    /**
     * 错误码
     */
    private Integer errcode;

    /**
     * 错误信息
     */
    private String errmsg;

    /**
     * 总条数
     */
    private Integer total_num;

    /**
     * 账号列表
     */
    private List<AccountInfo> account_list;

    @Data
    public static class AccountInfo {

        /**
         * 快递公司id
         */
        private String delivery_id;

        /**
         * 快递公司类型
         */
        private Integer company_type;

        /**
         * 店铺id
         */
        private String shop_id;

        /**
         * 电子面单账号id
         */
        private String acct_id;

        /**
         * 面单账号状态
         */
        private Integer status;

        /**
         * 面单余额
         */
        private Integer available;

        /**
         * 累积已取单
         */
        private Integer allocated;

        /**
         * 累积已回收
         */
        private Integer recycled;

        /**
         * 累计已取消
         */
        private Integer cancel;

        /**
         * 网点信息
         */
        private SiteInfo site_info;

        /**
         * 月结账号
         */
        private String monthly_card;

        private SenderAddress sender_address;
    }

    @Data
    public static class SiteInfo{

        /**
         * 快递公司编码
         */
        private  String delivery_id;

        /**
         * 网点运营状态, 1 正常, 其他值则不正常
         */
        private Integer site_status;

        /**
         * 网点编码
         */
        private String site_code;

        /**
         * 网点名字
         */
        private String site_name;

        /**
         * 地址全名
         */
        private String site_fullname;

        /**
         * 地址信息
         */
        private SiteAddress address;

        /**
         * 联系信息
         */
        private SiteContact contact;
    }

    @Data
    public static class SiteAddress{

        /**
         * 城市编码
         */
        private String city_code;

        /**
         * 城市名
         */
        private String city_name;

        /**
         * 国家编码
         */
        private String country_code;

        /**
         * 详细地址
         */
        private String detail_address;

        /**
         * 区县编码
         */
        private String district_code;

        /**
         * 区县名
         */
        private String district_name;

        /**
         * 省编码
         */
        private String province_code;

        /**
         * 省份名
         */
        private String province_name;

        /**
         * 街道编码
         */
        private String street_code;

        /**
         * 街道名
         */
        private String street_name;
    }

    @Data
    public static class SiteContact{

        /**
         * 移动电话
         */
        private String mobile;

        /**
         * 名称
         */
        private String name;

        /**
         * 电话
         */
        private String phone;
    }

    @Data
    public static class SenderAddress {

        private String province;

        private String city;

        private String county;

        private String street;

        private String address;

    }
}
