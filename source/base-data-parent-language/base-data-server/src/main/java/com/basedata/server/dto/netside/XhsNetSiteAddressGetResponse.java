package com.basedata.server.dto.netside;

import lombok.Data;

@Data
public class XhsNetSiteAddressGetResponse {

    private Integer error_code;

    private boolean success;

    private String error_msg;

    private XhsNetSiteAddress data;
}
