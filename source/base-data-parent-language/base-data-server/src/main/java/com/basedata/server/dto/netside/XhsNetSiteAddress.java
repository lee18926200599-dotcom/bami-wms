package com.basedata.server.dto.netside;

import lombok.Data;

import java.util.List;

@Data
public class XhsNetSiteAddress {

    private List<XhsSubscribeInfo> subscribeList;

    private Long accountId;
}
