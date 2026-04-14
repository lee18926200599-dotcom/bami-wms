package com.org.permission.server.org.dto.param;


import com.org.permission.server.org.bean.OrgBankAccountBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("资金账号请求参数")
@Data
public class BankAccountQueryReq implements Serializable {

    private static final long serialVersionUID = 4991529210809336758L;
    @ApiModelProperty("资金账户ID")
    private Long id;

    @ApiModelProperty("登录集团ID")
    private Long groupId;

    @ApiModelProperty("登录用户ID")
    private Long userId;

    @ApiModelProperty("使用组织")
    private Long useOrgId;

    @ApiModelProperty("开户组织")
    private Long openOrgId;

    @ApiModelProperty("账户分类,属性与对接方保持一致")
    private String accountCategory;

    @ApiModelProperty("账户类别,属性与对接方保持一致")
    private String accountType;

    @ApiModelProperty("账号")
    private String accountSn;

    @ApiModelProperty("开户名,账户名称")
    private String accountName;

    @ApiModelProperty("银行")
    private String bankName;

    @ApiModelProperty("银行编码")
    private String bankNameCode;

    @ApiModelProperty("开户行")
    private String openBankName;

    @ApiModelProperty("管理用户名")
    private String managerUserName;

    @ApiModelProperty("管理员用户ID")
    private Long managerUserId;

    @ApiModelProperty("状态")
    private Integer state;

    public OrgBankAccountBean builderBean(){
        OrgBankAccountBean bean = new OrgBankAccountBean();
        bean.setId(this.id);
        bean.setGroupId(this.groupId);
        bean.setBuId(this.openOrgId);
        bean.setUseOrgId(this.useOrgId);
        bean.setUserId(this.managerUserId);
        bean.setAccountSn(this.accountSn);
        bean.setAccountType(this.accountType);
        bean.setAccountCategory(this.accountCategory);
        bean.setState(this.state);
        return bean;
    }
}
