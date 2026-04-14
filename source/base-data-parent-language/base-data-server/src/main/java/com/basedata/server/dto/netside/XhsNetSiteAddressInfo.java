package com.basedata.server.dto.netside;

import com.basedata.server.dto.waybill.XhsSendAddress;
import lombok.Data;

@Data
public class XhsNetSiteAddressInfo {

    private XhsSendAddress address;

    private String mobile;

    private String name;

    private String phone;
}
