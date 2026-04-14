package com.basedata.server.dto.netside;

import lombok.Data;

import java.io.Serializable;

@Data
public class XhsNetSiteAddressRequestParam implements Serializable {

    private String cpCode;

    private boolean needUsage;

    private String brandCode;
}
