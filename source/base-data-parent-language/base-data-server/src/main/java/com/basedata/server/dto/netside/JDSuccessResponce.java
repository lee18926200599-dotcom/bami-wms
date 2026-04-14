package com.basedata.server.dto.netside;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 本版本是按照第一种方式对接
 * 1，京东商家开放平台  https://open.jd.com/home/home/#/doc/api?apiCateId=356&apiId=2121&apiName=jingdong.ldop.alpha.provider.sign.success.info.get
 * 2，京东开放平台新增了接口，用于查询京东商家的签约信息。新地址：https://open.jd.com/v2/#/doc/api?apiCateId=356&apiId=10236&apiName=jingdong.logistics.ewaybill.contract.query&gwType=0
 *
 */
@NoArgsConstructor
@Data
public class JDSuccessResponce implements Serializable {

    private JingdongLdopAlphaProviderSignSuccessInfoGetResponce jingdong_ldop_alpha_provider_sign_success_info_get_responce;

    @NoArgsConstructor
    @Data
    public static class JingdongLdopAlphaProviderSignSuccessInfoGetResponce implements Serializable {
        private String code;
        private ResultInfo resultInfo;

        @NoArgsConstructor
        @Data
        public static class ResultInfo implements Serializable {
            private String statusMessage;
            private Integer statusCode;
            private List<SuccessData> data;

            @NoArgsConstructor
            @lombok.Data
            public static class SuccessData implements Serializable {
                /**
                 * 承运商编码
                 */
                private String providerCode;
                /**
                 * 承运商id
                 */
                private Integer providerId;
                /**
                 * 承运商名称
                 */
                private String providerName;
                /**
                 * 承运商类型,1-快递公司 2-物流公司 3-安装公司 4-生鲜冷链公司
                 */
                private Integer providerType;
                /**
                 * 是否支持货到付款
                 */
                private Boolean supportCod;
                /**
                 * 承运商经营类型， 1-直营 2-加盟
                 */
                private Integer operationType;
                /**
                 * 网点编码
                 */
                private String branchCode;
                /**
                 * 网点名称
                 */
                private String branchName;
                /**
                 * 财务结算编码（直营型快递公司必返回）
                 */
                private String settlementCode;
                /**
                 * 剩余单号量
                 */
                private String amount;
                /**
                 * 签约发货地址
                 */
                private WaybillAddress address;
                /**
                 * 承运商支持的增值服务(京东所定义)
                 */
                private List<ValueAddedServiceDTO> valueAddedServices;

                @NoArgsConstructor
                @lombok.Data
                public static class WaybillAddress implements Serializable {
                    private Integer provinceId;
                    private String provinceName;
                    private Integer cityId;
                    private String cityName;
                    private Integer countryId;
                    private String countryName;
                    private Integer countrysideId;
                    private String countrysideName;
                    private String address;
                }

                @NoArgsConstructor
                @lombok.Data
                public static class ValueAddedServiceDTO implements Serializable {
                    /**
                     * 服务编码，DELIVERY_TO_DOOR（送货上门）
                     */
                    private String serviceCode;
                    /**
                     * 服务名称：送货上门
                     */
                    private String serviceName;
                }
            }
        }
    }
}
