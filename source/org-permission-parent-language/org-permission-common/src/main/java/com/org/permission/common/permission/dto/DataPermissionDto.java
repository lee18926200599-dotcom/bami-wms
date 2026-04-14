package com.org.permission.common.permission.dto;

import com.google.common.collect.Lists;
import com.org.permission.common.util.BeanCopierUtil;
import com.org.permission.common.util.DtoConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 数据权限
 */
@Data
@ApiModel(description = "数据权限返回对象")
public class DataPermissionDto implements Serializable, DtoConverter<com.org.permission.common.permission.dto.UserDataPermissionDto, DataPermissionDto> {

    private static final long serialVersionUID = -6938333409963994910L;
    @ApiModelProperty(value = "用户id")
    private Long userId; 
    @ApiModelProperty(value = "权限类型：功能权限：func，组织权限：org，数据权限：data")
    private String permissionType; 
    @ApiModelProperty(value = "关联权限id")
    private Long dataId;
    @ApiModelProperty(value = "权限id")
    private Long permissionId;
    @ApiModelProperty(value = "国标码")
    private String gbCode; 
    @ApiModelProperty(value = "授权人id")
    private Long author; 
    @ApiModelProperty(value = "授权人")
    private String authorUser; 
    @ApiModelProperty(value = "授权时间")
    private Date authorTime; 
    @ApiModelProperty(value = "状态 1：启用，0：未启用")
    private Integer state; 
    @ApiModelProperty(value = "组织id")
    private Long orgId;
    @ApiModelProperty(value = "集团id")
    private Long groupId;
    @ApiModelProperty(value = "管理维度名称")
    private String name;
    @ApiModelProperty(value = "管理维度id")
    private Integer managementId;
    @ApiModelProperty(value = "分配方式")
    private String distributionType;
    @ApiModelProperty(value = "分类依据")
    private String based;
    @ApiModelProperty(value = "类别/个体")
    private String dataResource;
    @ApiModelProperty(value = "父级id")
    private Integer parentId;
    @ApiModelProperty(value = "操作权限(查询、维护) 1：查询、2：查询和维护")
    private Integer optionPermission;
    @ApiModelProperty(value = "是否有权限,判断勾选状态")
    private boolean check;
    @ApiModelProperty(value = "flag=0,show=false未启用不显示，flag=1 show=true")
    private Integer flag = 1;

    @ApiModelProperty(value = "维护")
    private boolean edit;
    @ApiModelProperty(value = "查询")
    private boolean query; 

    private List<DataPermissionDto> childDatas;


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DataPermissionDto userDataPermission = (DataPermissionDto) o;
        return dataId > 0 ? dataId.intValue() == userDataPermission.dataId.intValue() : dataId == 0;
    }


    @Override
    public DataPermissionDto convertFrom(com.org.permission.common.permission.dto.UserDataPermissionDto userDataPermissionDto) {
        if (ObjectUtils.isEmpty(userDataPermissionDto)) {
            return null;
        }
        DataPermissionDto dataPermissionDto = new DataPermissionDto();
        BeanCopierUtil.DATA_PERMISSION_COPIER.copy(userDataPermissionDto, dataPermissionDto, null);
        if (!ObjectUtils.isEmpty(userDataPermissionDto.getChildDatas())) {
            List<com.org.permission.common.permission.dto.UserDataPermissionDto> childPermissions = userDataPermissionDto.getChildDatas();
            List<DataPermissionDto> childDataPermissionList = Lists.newArrayList();
            for (com.org.permission.common.permission.dto.UserDataPermissionDto childUserDataPermissionDto : childPermissions) {
                DataPermissionDto childDataPermission = convertFrom(childUserDataPermissionDto);
                childDataPermissionList.add(childDataPermission);
            }
            dataPermissionDto.setChildDatas(childDataPermissionList);
        }
        return dataPermissionDto;
    }

    /**
     * dto转换
     * @param userDataPermissionDtos
     * @return
     */
    public List<DataPermissionDto> convertListFrom(List<com.org.permission.common.permission.dto.UserDataPermissionDto> userDataPermissionDtos){
        ArrayList<DataPermissionDto> dataPermissionDtos = Lists.newArrayList();
        if(ObjectUtils.isEmpty(userDataPermissionDtos)){
            return dataPermissionDtos;
        }
        for (UserDataPermissionDto userDataPermissionDto : userDataPermissionDtos) {
            DataPermissionDto dataPermissionDto = convertFrom(userDataPermissionDto);
            dataPermissionDtos.add(dataPermissionDto);
        }
        return dataPermissionDtos;
    }


}
