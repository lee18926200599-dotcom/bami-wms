package com.usercenter.server.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class SyncUserDTO {

    @JsonProperty("companyCode")
    private String companyCode;
    @JsonProperty("companyId")
    private String companyId;
    @JsonProperty("companyName")
    private String companyName;
    @JsonProperty("createDate")
    private String createDate;
    @JsonProperty("disable")
    private Boolean disable;
    @JsonProperty("emailAddr")
    private String emailAddr;
    @JsonProperty("employeeNum")
    private String employeeNum;
    @JsonProperty("enterpriseId")
    private String enterpriseId;
    @JsonProperty("equipments")
    private String equipments;
    @JsonProperty("expireDate")
    private String expireDate;
    @JsonProperty("id")
    private String id;
    @JsonProperty("lock")
    private Boolean lock;
    @JsonProperty("lockExpireDate")
    private String lockExpireDate;
    @JsonProperty("nickname")
    private String nickname;
    @JsonProperty("orgId")
    private String orgId;
    @JsonProperty("password")
    private String password;
    @JsonProperty("personType")
    private Integer personType;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("pwdExpireDate")
    private String pwdExpireDate;
    @JsonProperty("roleId")
    private String roleId;
    @JsonProperty("roles")
    private List<RolesDTO> roles;
    @JsonProperty("serviceId")
    private String serviceId;
    @JsonProperty("type")
    private Integer type;
    @JsonProperty("userCategory")
    private String userCategory;
    @JsonProperty("userState")
    private String userState;
    @JsonProperty("username")
    private String username;

    @NoArgsConstructor
    @Data
    public static class RolesDTO {
        @JsonProperty("allAuth")
        private Integer allAuth;
        @JsonProperty("enterpriseId")
        private String enterpriseId;
        @JsonProperty("groupId")
        private String groupId;
        @JsonProperty("groupName")
        private String groupName;
        @JsonProperty("id")
        private String id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("num")
        private String num;
        @JsonProperty("orgId")
        private String orgId;
        @JsonProperty("type")
        private Integer type;
    }
}
