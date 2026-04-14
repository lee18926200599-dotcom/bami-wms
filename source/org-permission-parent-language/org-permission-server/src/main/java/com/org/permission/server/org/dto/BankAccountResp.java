package com.org.permission.server.org.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.org.permission.server.org.bean.OrgBankAccountBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@ApiModel("银行账户返回值")
@Data
public class BankAccountResp {

    @ApiModelProperty("资金账户ID")
    private Long id;

    @ApiModelProperty("使用组织名称")
    private String useOrgName;

    @ApiModelProperty("使用组织ID")
    private Long useOrgId;

    @ApiModelProperty("开户组织名称")
    private String openOrgName;

    @ApiModelProperty("开户组织ID")
    private Long openOrgId;

    @ApiModelProperty("账号")
    private String accountSn;


    @ApiModelProperty("开户名,账户名称")
    private String accountName;

    @ApiModelProperty("状态")
    private Integer state;

    @ApiModelProperty("账户分类,属性与对接方保持一致")
    private String accountCategory;

    @ApiModelProperty("账户类别,属性与对接方保持一致")
    private String accountType;


    @ApiModelProperty("银行")
    @JsonIgnore
    private String bankName;

    @ApiModelProperty("银行编码")
    private String bankNameCode;

    @ApiModelProperty("开户行")
    private String openBankName;

    @ApiModelProperty("管理用户")
    private String managerUserName;


    @ApiModelProperty("支付账户ID")
    private Long accountId;


    @JsonIgnore
    private Long managerUserId;

    public static List<BankAccountResp> generatorFromBeanList(List<OrgBankAccountBean> beans){
        List<BankAccountResp> resps = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(beans)){
            beans.forEach(bean->{
                BankAccountResp resp = new BankAccountResp();
                resp.setId(bean.getId());
                resp.setUseOrgId(bean.getUseOrgId());
                resp.setOpenOrgId(bean.getBuId());
                resp.setAccountSn(bean.getAccountSn());
                resp.setAccountCategory(bean.getAccountCategory());
                resp.setState(bean.getState());
                resp.setAccountType(bean.getAccountType());
                resp.setAccountId(bean.getAccountId());
                resp.setManagerUserId(bean.getUserId());
                resps.add(resp);
            });
        }
        return resps;
    }
}
